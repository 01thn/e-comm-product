package by.ecomm.ecommproduct.repository

import by.ecomm.ecommproduct.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByNameIsIgnoreCase(name: String): Category?
}