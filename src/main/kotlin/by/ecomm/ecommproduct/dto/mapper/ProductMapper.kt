package by.ecomm.ecommproduct.dto.mapper

import by.ecomm.ecommproduct.dto.ProductRequestDto
import by.ecomm.ecommproduct.dto.ProductResponseDto
import by.ecomm.ecommproduct.entity.Product
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper(componentModel = "spring")
interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    fun toEntity(dto: ProductRequestDto): Product

    fun updateEntityFromDto(dto: ProductRequestDto, @MappingTarget entity: Product)

    @Mapping(source = "category.id", target = "categoryId")
    fun toDto(entity: Product): ProductResponseDto

}

