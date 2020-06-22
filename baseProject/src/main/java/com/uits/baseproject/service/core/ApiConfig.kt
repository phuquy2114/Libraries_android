package com.primarynet.metro.service.core

import android.content.Context
import lombok.Builder
import lombok.Value

/**
 * A configure is used to create [com.primarynet.metro.service.ApiClient].
 * See this at
 *
 * @author QuyDP
 */
@Value
@Builder
class ApiConfig {
    val context: Context? = null
    val baseUrl: String? = null
    val auth: String? = null
}
