package com.eminokumus.bookvox.model

import java.time.LocalDate

data class User(
    val userId: String,
    val name: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String,
    val dateOfBirth: LocalDate
)
