package by.ecomm.ecommproduct.controller;

import by.ecomm.ecommproduct.controller.helpers.PageUtils
import by.ecomm.ecommproduct.dto.CategoryRequestDto
import by.ecomm.ecommproduct.dto.CategoryResponseDto
import by.ecomm.ecommproduct.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
@RequestMapping("/api/v1/category")
class CategoryController(private val service: CategoryService) {

    @Operation(summary = "Get all categories with pagination and sorting")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully retrieved list of categories",
            )
        ]
    )
    @GetMapping("/all")
    fun getAll(
        @Parameter(description = "Page number to retrieve") @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "5") size: Int,
        @Parameter(description = "Sorting order") @RequestParam sortingOrder: SortOrder
    ): ResponseEntity<Page<CategoryResponseDto>> {
        return ResponseEntity.ok(service.getAll(PageUtils.pageBuilder(page, size, sortingOrder)))
    }

    @Operation(summary = "Get category by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully retrieved category",
            ),
            ApiResponse(responseCode = "404", description = "Category not found")
        ]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<CategoryResponseDto> {
        val categoryDto = service.getById(id)
        return ResponseEntity(categoryDto, HttpStatus.OK)
    }

    @Operation(summary = "Get category by name")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully retrieved category",
            ),
            ApiResponse(responseCode = "404", description = "Category not found")
        ]
    )
    @GetMapping
    fun getByName(@RequestParam name: String): ResponseEntity<CategoryResponseDto> {
        val categoryDto = service.getByName(name)
        return ResponseEntity(categoryDto, HttpStatus.OK)
    }

    @Operation(summary = "Create a new category")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Successfully created category",
            )
        ]
    )
    @PostMapping
    fun save(@RequestBody category: CategoryRequestDto): ResponseEntity<CategoryResponseDto> {
        val categoryDto = service.save(category)
        return ResponseEntity(categoryDto, HttpStatus.CREATED)
    }

    @Operation(summary = "Delete a category by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Successfully deleted category"),
            ApiResponse(responseCode = "404", description = "Category not found")
        ]
    )
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Update a category by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successfully updated category",
            ),
            ApiResponse(
                responseCode = "404",
                description = "Category not found",
            )
        ]
    )
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody categoryRequest: CategoryRequestDto
    ): ResponseEntity<CategoryResponseDto> {
        val productDto = service.update(id, categoryRequest)
        return ResponseEntity(productDto, HttpStatus.OK)
    }

}
