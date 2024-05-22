package by.ecomm.ecommproduct.repository

import by.ecomm.ecommproduct.entity.Product
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, UUID> {
    fun findByTitleIsIgnoreCase(name: String): Product?
}
