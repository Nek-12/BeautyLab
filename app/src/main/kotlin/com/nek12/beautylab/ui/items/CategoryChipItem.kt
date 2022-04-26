package com.nek12.beautylab.ui.items

import com.nek12.beautylab.core.model.net.product.category.ProductCategoryResponse
import java.util.*

data class CategoryChipItem(val name: String, val id: UUID) {
    constructor(response: ProductCategoryResponse) : this(response.name, response.id)
}
