package com.mycompose.list

import kotlinx.serialization.Serializable

@Serializable
data class GetListResponse(
    val currentPage: Int,
    val nextPageAvailable: Boolean,
    val list: List<ListItem>
)
