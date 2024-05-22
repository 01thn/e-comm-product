package by.ecomm.ecommproduct.controller;

import by.ecomm.ecommproduct.controller.helpers.PageUtils
import by.ecomm.ecommproduct.dto.ProductRequestDto
import by.ecomm.ecommproduct.dto.ProductResponseDto
import by.ecomm.ecommproduct.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID
import javax.swing.SortOrder
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(private val service: ProductService) {

    @Operation(summary = "Get all products with pagination and sorting")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved list of products"
            )
        ]
    )
    @GetMapping("/all")
    fun getAll(
        @Parameter(description = "Page number to retrieve") @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "5") size: Int,
        @Parameter(description = "Sorting order") @RequestParam sortingOrder: SortOrder
    ): ResponseEntity<Page<ProductResponseDto>> {
        return ResponseEntity.ok(service.getAll(PageUtils.pageBuilder(page, size, sortingOrder)))
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully retrieved product"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product not found"
            )
        ]
    )
    @GetMapping("/{id}")
    fun getById(@Parameter(description = "ID of the product to retrieve") @PathVariable id: UUID): ResponseEntity<ProductResponseDto> {
        val productDto = service.getById(id)
        return ResponseEntity(productDto, HttpStatus.OK)
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Successfully created product"

            )
        ]
    )
    @PostMapping
    fun save(@Parameter(description = "Product to create") @RequestBody product: ProductRequestDto): ResponseEntity<ProductResponseDto> {
        val productDto = service.save(product)
        return ResponseEntity(productDto, HttpStatus.CREATED)
    }

    @Operation(summary = "Delete a product by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Successfully deleted product"),
            ApiResponse(
                responseCode = "404",
                description = "Product not found"
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun delete(@Parameter(description = "ID of the product to delete") @PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Update a product by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully updated product"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product not found"
            )
        ]
    )
    @PutMapping("/{id}")
    fun update(
        @Parameter(description = "ID of the product to update") @PathVariable id: UUID,
        @Parameter(description = "Updated product data") @RequestBody productRequest: ProductRequestDto
    ): ResponseEntity<ProductResponseDto> {
        val productDto = service.updateById(id, productRequest)
        return ResponseEntity(productDto, HttpStatus.OK)
    }

}
