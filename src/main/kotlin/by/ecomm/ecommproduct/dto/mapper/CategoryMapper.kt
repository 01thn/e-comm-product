package by.ecomm.ecommproduct.dto.mapper

import by.ecomm.ecommproduct.dto.CategoryRequestDto
import by.ecomm.ecommproduct.dto.CategoryResponseDto
import by.ecomm.ecommproduct.entity.Category
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget

@Mapper(componentModel = "spring", uses = [ProductMapper::class])
interface CategoryMapper {

    fun toEntity(dto: CategoryRequestDto): Category

    fun updateEntityFromDto(dto: CategoryRequestDto, @MappingTarget entity: Category)

    fun toDto(entity: Category): CategoryResponseDto

}
