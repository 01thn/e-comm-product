package by.ecomm.ecommproduct.controller.helpers

import javax.swing.SortOrder
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

object PageUtils {
    fun pageBuilder(page: Int, size: Int, sortingOrder: SortOrder): PageRequest {
        val sort = when (sortingOrder) {
            SortOrder.ASCENDING -> Sort.by("updatedAt").ascending()
            SortOrder.DESCENDING -> Sort.by("updatedAt").descending()
            SortOrder.UNSORTED -> Sort.by("updatedAt")
        }
        return PageRequest.of(page, size, sort)
    }

}
