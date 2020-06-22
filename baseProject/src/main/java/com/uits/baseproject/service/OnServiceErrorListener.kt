package com.uits.baseproject.service

import com.primarynet.metro.service.core.ApiError

/**
 * Copyright © 2017 BAP CO., LTD
 * Created by PHUQUY on 7/25/17.
 */

interface OnServiceErrorListener {
    fun onNetworkError()

    fun onAuthenticationError()

    fun onAppError(apiError: ApiError)

    fun onRequestConnectTimeout()
}
