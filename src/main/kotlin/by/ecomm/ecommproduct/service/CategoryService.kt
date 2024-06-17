package by.ecomm.ecommproduct.service

import by.ecomm.ecommproduct.dto.CategoryRequestDto
import by.ecomm.ecommproduct.dto.CategoryResponseDto
import by.ecomm.ecommproduct.dto.mapper.CategoryMapper
import by.ecomm.ecommproduct.exception.ProductServiceException
import by.ecomm.ecommproduct.repository.CategoryRepository
import java.time.ZonedDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryService(
    val repository: CategoryRepository,
    val mapper: CategoryMapper
) {

    fun getAll(pageable: Pageable): Page<CategoryResponseDto> {
        return repository.findAll(pageable).map { mapper.toDto(it) }
    }

    fun getById(id: Long): CategoryResponseDto {
        val category = repository.findById(id)
            .orElseThrow { ProductServiceException("Product with ID $id not found") }
        return mapper.toDto(category)
    }

    fun getByName(name: String): CategoryResponseDto {
        val categoryByName = repository.findByNameIsIgnoreCase(name)
            ?: throw ProductServiceException("Category with name '${name}' not found")
        return mapper.toDto(categoryByName)
    }

    fun save(request: CategoryRequestDto): CategoryResponseDto {
        return mapper.toDto(
            repository.save(
                mapper.toEntity(request).apply {
                    createdAt = ZonedDateTime.now()
                }
            )
        )
    }

    fun delete(id: Long) {
        val category = repository.findById(id)
            .orElseThrow { ProductServiceException("Category not found") }
        repository.delete(category)
    }

    fun update(id: Long, request: CategoryRequestDto): CategoryResponseDto {
        val existingCategory = repository.findById(id).orElseThrow {
            ProductServiceException("Category with ID $id not found.")
        }
        mapper.updateEntityFromDto(request, existingCategory)
        return mapper.toDto(repository.save(existingCategory))
    }

}
