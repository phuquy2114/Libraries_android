package com.uits.baseproject.service.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("message")
    var message : String ?= null
}