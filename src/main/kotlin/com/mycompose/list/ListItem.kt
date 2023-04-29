package com.mycompose.list

import kotlinx.serialization.Serializable

@Serializable
data class ListItem(
    val name: String,
    val imageUrl: String
)