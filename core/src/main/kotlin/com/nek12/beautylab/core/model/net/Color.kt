package com.nek12.beautylab.core.model.net

@kotlinx.serialization.Serializable
enum class Color(val value: Int) {

    RED(0xFF00),
    GREEN(0x00FF00),
    BLUE(0x0000FF);

    companion object {

        private val map: Map<Int, Color> = values().associateBy(Color::value)

        fun from(value: Int): Color? {
            return map[value]
        }
    }
}
