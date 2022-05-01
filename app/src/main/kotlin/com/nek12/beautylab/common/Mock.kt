package com.nek12.beautylab.common

import com.nek12.beautylab.core.model.net.Color
import com.nek12.beautylab.core.model.net.Country
import com.nek12.beautylab.core.model.net.TransactionStatus.PENDING
import com.nek12.beautylab.core.model.net.brand.BrandResponse
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.model.net.product.category.ProductCategoryResponse
import com.nek12.beautylab.core.model.net.transaction.GetTransactionResponse
import java.time.Instant
import java.util.*


object Mock {

    val transaction
        get() = GetTransactionResponse(
            product = product,
            buyerId = UUID.randomUUID(),
            pricePerUnit = 27.0,
            totalPrice = 277.0,
            amount = 12,
            date = Instant.now(),
            status = PENDING,
            comment = "Please be careful with my parcel",
            id = UUID.randomUUID(),
        )
    val category = ProductCategoryResponse(
        "Category", "Beauty", UUID.randomUUID(),
    )

    val brand = BrandResponse(
        UUID.randomUUID(), "Brand", Country.values().random()
    )

    val product = GetProductResponse(
        "Product Name Long But Less than 64 symbols limit",
        """
            Scented candles Jiovanne with Orange flavor is one of our best products. It makes you lose all hope of knowing what is right for you and a whole new look and feel. It's also a great way to spend your waking hour, and it really goes with the colors for dinner or in a coffee shop. Our brandy or whiskey is a no-brainer.

            José-Clément

            It's not about your skin, but rather if you like them. Jochen offers two styles, each of which are the same colour. When you order a single bottle of their Jochen and their Champagne, the brand name is pronounced with the same flavour. They also give customers their 'Jochen-Clément'. (Jochen's are very common Champagne brands and it is a French-made name for their brandy, it's often sold to shopkeepers to mark their deals. It's not a bad name, but when you're buying a Jochen, you don't want to have someone to watch you when your orders come in, it's a waste.)

            Odessa is the French version of their Ardennes. This is the company's only real traditional style, in fact that's how I often spend my waking hours, but they also offer a slightly different look for the occasional coffee. You see, unlike many of our brands, you don't wear the brand name.
        """.trimIndent(),
        null,
        12384,
        "Property 1, prop 2, prop 3",
        833.004,
        234.76,
        category,
        brand,
        Color.values().random(),
        Instant.now(),
        UUID.randomUUID(),
    )

}
