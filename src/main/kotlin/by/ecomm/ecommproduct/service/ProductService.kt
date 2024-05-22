package by.ecomm.ecommproduct.service

import by.ecomm.ecommproduct.dto.ProductRequestDto
import by.ecomm.ecommproduct.dto.ProductResponseDto
import by.ecomm.ecommproduct.dto.mapper.ProductMapper
import by.ecomm.ecommproduct.exception.AlreadyExistsException
import by.ecomm.ecommproduct.exception.ElementNotFoundException
import by.ecomm.ecommproduct.repository.CategoryRepository
import by.ecomm.ecommproduct.repository.ProductRepository
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val repository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val mapper: ProductMapper,
) {

    fun getAll(pageable: Pageable): Page<ProductResponseDto> {
        return repository.findAll(pageable).map { mapper.toDto(it) }
    }

    fun getById(id: UUID): ProductResponseDto {
        val product = repository.findById(id)
            .orElseThrow { ElementNotFoundException("Product with ID $id not found") }
        return mapper.toDto(product)
    }

    fun save(request: ProductRequestDto): ProductResponseDto {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ElementNotFoundException("Category with ID ${request.categoryId} not found") }

        repository.findByTitleIsIgnoreCase(request.title)?.let {
            throw AlreadyExistsException("Product with name '${request.title}' already exists")
        }

        val entity = mapper.toEntity(request).apply {
            this.category = category
        }

        val savedEntity = repository.save(entity)
        return mapper.toDto(savedEntity).apply {
            this.categoryId = category.id
        }
    }

    fun updateById(id: UUID, request: ProductRequestDto): ProductResponseDto {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ElementNotFoundException("Category with ID ${request.categoryId} not found") }

        val existingProduct = repository.findById(id).orElseThrow {
            ElementNotFoundException("Product with ID $id not found.")
        }

        mapper.updateEntityFromDto(request, existingProduct)
        existingProduct.apply {
            this.category = category
        }

        val savedEntity = repository.save(existingProduct)
        return mapper.toDto(savedEntity).apply {
            this.categoryId = category.id
        }
    }

    fun delete(id: UUID) {
        if (repository.existsById(id)) {
            repository.deleteById(id)
        } else {
            throw ElementNotFoundException("Product with ID $id not found")
        }
    }

}
