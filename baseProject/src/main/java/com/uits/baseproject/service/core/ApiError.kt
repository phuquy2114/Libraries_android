package com.primarynet.metro.service.core

import com.google.gson.annotations.SerializedName

/**
 * Api error
 *
 * @author QuyDP
 */
data class ApiError(
    var code: Int,
    var message: String?
)
