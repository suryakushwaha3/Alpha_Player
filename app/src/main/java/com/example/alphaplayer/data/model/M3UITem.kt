package com.example.alphaplayer.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class M3UItem(
    val title: String,
    val url: String,
    val logo: String?,
    val group: String?,
    val headers: Map<String, String> = emptyMap()
)
