package com.nek12.beautylab.ui.items

import com.nek12.beautylab.core.model.net.brand.BrandResponse
import java.util.*

data class BrandChipItem(
    val name: String,
    val id: UUID,
) {

    constructor(response: BrandResponse) : this(
        response.name, response.id
    )
}
