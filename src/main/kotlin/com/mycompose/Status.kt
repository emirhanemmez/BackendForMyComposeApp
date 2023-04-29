package com.mycompose

import kotlinx.serialization.SerialName

enum class Status {
    @SerialName("success")
    SUCCESS,

    @SerialName("fail")
    FAIL
}