package com.uits.baseproject.service.response

import com.google.gson.annotations.SerializedName
import lombok.Data
import lombok.EqualsAndHashCode

@Data
@EqualsAndHashCode(callSuper = false)
open class BaseResponse {

    @SerializedName("message")
    var message : String ?= null
}