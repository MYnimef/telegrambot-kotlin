package com.mynimef.bot


enum class Emoji(val value: String) {

    THINKING("\uD83E\uDD28"),
    COOL("\uD83D\uDE0E"),
    SHIT("\uD83D\uDCA9"),
    LIKE("\uD83D\uDC4D"),
    DISLIKE("\uD83D\uDC4E"),
    POINTER("\u27A1"),
    QUESTION("\u2753");

    override fun toString(): String {
        return value
    }

}