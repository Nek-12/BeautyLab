package com.nek12.beautylab.core.model.net

@kotlinx.serialization.Serializable
enum class Color(val value: Int) {

    RED(0xFF00),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    WHITE(0xFFFFFF),
    BLACK(0x000000),
    YELLOW(0xFFFF00),
    ORANGE(0xFFA500),
    PURPLE(0x800080),
    PINK(0xFFC0CB),
    BROWN(0xA52A2A),
    GREY(0x808080),
    SILVER(0xC0C0C0),
    GOLD(0xFFD700),
    LIME(0x00FF00),
    CYAN(0x00FFFF),
    MAGENTA(0xFF00FF),
    TURQUOISE(0x40E0D0),
    OLIVE(0x808000),
    MAROON(0x800000),
    NAVY(0x000080),

    OTHER(0x000000);

    companion object {

        private val map: Map<Int, Color> = values().associateBy(Color::value)

        fun from(value: Int): Color? {
            return map[value]
        }
    }
}
