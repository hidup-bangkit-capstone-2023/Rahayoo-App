package com.bangkit.rahayoo.util

import com.bangkit.rahayoo.data.model.StressTestQuestionType

fun Float.toProgressValue() = (this * 100).toInt()

fun Float.toScaleValue(): Int {
    return when (this.toProgressValue()) {
        in 1..25 -> 2
        in 26..50 -> 3
        in 51..75 -> 4
        in 76..100 -> 5
        else -> 1
    }
}

fun Float.toEmoteValue(questionType: StressTestQuestionType): String {
    return when (questionType) {
        StressTestQuestionType.NEGATIVE -> {
            when (this.toProgressValue()) {
                in 1..25 -> "\uD83D\uDE42"
                in 26..50 -> "\uD83D\uDE14"
                in 51..75 -> "\uD83D\uDE42"
                in 76..100 -> "\uD83D\uDE2B"
                else -> "\uD83D\uDE00"
            }
        }
        StressTestQuestionType.POSITIVE -> {
            when (this.toProgressValue()) {
                in 1..25 -> "\uD83D\uDE29"
                in 26..50 -> "\uD83D\uDE14"
                in 51..75 -> "\uD83D\uDE42"
                in 76..100 -> "\uD83D\uDE00"
                else -> "\uD83D\uDE2B"
            }
        }
    }
}