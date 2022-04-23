package com.nek12.beautylab.core.model.net.mobile

import com.nek12.beautylab.core.model.net.brand.BrandResponse
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.model.net.product.category.ProductCategoryResponse
import com.nek12.beautylab.core.model.net.user.GetUserResponse

@kotlinx.serialization.Serializable
data class MobileViewResponse(
    val popularProducts: List<GetProductResponse>,
    val newProducts: List<GetProductResponse>,
    val mostDiscountedProducts: List<GetProductResponse>,
    val user: GetUserResponse,
    val categories: List<ProductCategoryResponse>,
    val topBrands: List<BrandResponse>,
)
