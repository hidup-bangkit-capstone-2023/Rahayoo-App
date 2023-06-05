package com.bangkit.rahayoo.data.model.response

import java.time.LocalDate

data class StressLevelResponse(
    val stressId: Int,
    val employeeId: Int,
    val date: LocalDate,
    val stressValue: Int,
)