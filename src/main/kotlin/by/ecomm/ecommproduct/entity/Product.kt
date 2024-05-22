package by.ecomm.ecommproduct.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapKeyColumn
import java.time.ZonedDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    var title: String,
    var description: String,
    var price: Double,
    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category?,
    @ElementCollection
    @CollectionTable(name = "product_attributes", joinColumns = [JoinColumn(name = "product_id")])
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value")
    var attributes: Map<String, String> = mapOf(),
    @Enumerated(EnumType.ORDINAL)
    var status: ProductStatus,
    @CreationTimestamp
    val createdAt: ZonedDateTime? = ZonedDateTime.now(),
    @UpdateTimestamp
    val updatedAt: ZonedDateTime? = null
)