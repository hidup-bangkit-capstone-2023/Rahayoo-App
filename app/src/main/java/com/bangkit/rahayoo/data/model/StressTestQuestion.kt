package com.bangkit.rahayoo.data.model

data class StressTestQuestion(
    val id: Int,
    val question: String,
    val questionType: StressTestQuestionType = StressTestQuestionType.POSITIVE,
)

enum class StressTestQuestionType {
    POSITIVE,
    NEGATIVE
}

object StressTestQuestions {
    fun getAllQuestion() = listOf(
        StressTestQuestion(
            0,
            "how often have you been upset because of something that happened unexpectedly",
            StressTestQuestionType.NEGATIVE
        ),
        StressTestQuestion(
            1,
            "how often have you felt that you were unable to control the important things in your life",
            StressTestQuestionType.NEGATIVE
        ),
        StressTestQuestion(
            2,
            "how often have you felt nervous and stressed",
            StressTestQuestionType.NEGATIVE
        ),
        StressTestQuestion(
            3,
            "how often have you felt confident about your ability to handle your personal problems",
            StressTestQuestionType.POSITIVE
        ),
        StressTestQuestion(
            4,
            "how often have you felt that things were going your way",
            StressTestQuestionType.POSITIVE
        ),
        StressTestQuestion(
            5,
            "how often have you found that you could not cope with all the things that you had to do",
            StressTestQuestionType.NEGATIVE
        ),
        StressTestQuestion(
            6,
            "how often have you been able to control irritations in your life",
            StressTestQuestionType.POSITIVE
        ),
        StressTestQuestion(
            7,
            "how often have you felt that you were on top of things",
            StressTestQuestionType.POSITIVE
        ),
        StressTestQuestion(
            8,
            "how often have you been angered because of things that were outside of your control",
            StressTestQuestionType.NEGATIVE
        ),
        StressTestQuestion(
            9,
            "how often have you felt difficulties were piling up so high that you could not overcome them",
            StressTestQuestionType.NEGATIVE
        )
    )
}