package com.uits.baseproject.service

import com.uits.baseproject.service.response.BaseResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 3/21/19.
 */
interface ApiService {
    /**
     * function GET
     */
    @GET("/get")
    fun getData(@Query("key") value : String) : Single<BaseResponse>

    /**
     * func POST
     */
    @Multipart
    @POST("api.php?func=ReqLogin")
    fun postReqLogin(
            @Query("ReqUserID", encoded = true) id: String,
            @Query("ReqPassword", encoded = true) pass: String,
            @Query("ReqPhone", encoded = false) phone: String,
            @Query("ReqToken", encoded = false) token: String,
            @Part("APIKey") key: RequestBody
    ): Observable<BaseResponse>
}
