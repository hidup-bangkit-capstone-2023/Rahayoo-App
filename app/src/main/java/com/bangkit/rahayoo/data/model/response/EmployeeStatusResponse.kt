package com.bangkit.rahayoo.data.model.response

import com.squareup.moshi.Json

data class StressLevelResponse(
    @Json(name = "total_avg")
    val totalAvg: Float
)

data class MoodPerDay(
    @Json(name = "mood_value")
    val mood: String,
    val date: String,
    val count: Int
)

data class WeeklyStatusResponse(
    @Json(name = "weekly_stress_avg")
    val weeklyStressAvg: Float? = null,
    @Json(name = "weekly_mood")
    val weeklyMood: String? = null,
    @Json(name = "weekly_calendar")
    val weeklyMoodPerDay: List<MoodPerDay> = emptyList()
)