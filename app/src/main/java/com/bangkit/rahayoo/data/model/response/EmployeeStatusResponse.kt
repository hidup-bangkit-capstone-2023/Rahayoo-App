package com.bangkit.rahayoo.data.model.response

import com.squareup.moshi.Json

data class StressLevelResponse(
    @Json(name = "total_avg")
    val totalAvg: Float
)

data class MoodPerDay(
    val mood: Int,
    val date: String
)

data class WeeklyStatusResponse(
    @Json(name = "weekly_stress_avg")
    val weeklyStressAvg: Int,
    @Json(name = "weekly_mood_avg")
    val weeklyMoodAvg: Int?,
    @Json(name = "weekly_mood_per_day")
    val weeklyMoodPerDay: List<MoodPerDay>?
)