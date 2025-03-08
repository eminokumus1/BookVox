package com.eminokumus.bookvox.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int? = null,
    val name: String? = null,
    val author: String? = null,
    val imageUrl: String? = null,
    val summary: String? = null,
    val audioUrl: String? = null,
    val content: String? = null // Kitabın tam içeriği (şimdilik test verisi olacak)
): Parcelable