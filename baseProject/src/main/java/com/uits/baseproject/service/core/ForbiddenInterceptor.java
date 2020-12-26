package com.uits.baseproject.service.core;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Copyright © 2017 BAP CO., LTD
 * Created by PHUQUY on 11/13/17.
 */

public class ForbiddenInterceptor implements Interceptor {

    private static final String TAG = ForbiddenInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 403) {
            Log.e(TAG, "intercept: ");
        }
        return response;
    }
}
