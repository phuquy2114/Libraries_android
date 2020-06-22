package com.uits.baseproject.service

import android.util.Log
import com.google.gson.GsonBuilder
import com.uits.baseproject.service.core.BooleanAdapter
import com.uits.baseproject.service.core.DoubleAdapter
import com.uits.baseproject.service.core.ForbiddenInterceptor
import com.uits.baseproject.service.core.IntegerAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * http://maxwellvandervelde.com/blog/android-simulate-slow-network
 * Api client
 *
 * @author QuyDP
 */
abstract class ApiClient {

    /**
     * constructor
     */
    constructor()

    /**
     * init confirm url
     *
     * @param url
     */
    fun init(url: String?): Retrofit {
        // Author
        val auth = ""
        Log.d(TAG, "init: $auth")
        // init
        val booleanAdapter = BooleanAdapter()
        val integerAdapter = IntegerAdapter()
        val doubleAdapter = DoubleAdapter()
        // init Gson
        val gson = GsonBuilder()
                .registerTypeAdapter(Boolean::class.java, booleanAdapter)
                .registerTypeAdapter(Boolean::class.javaPrimitiveType, booleanAdapter)
                .registerTypeAdapter(Int::class.java, integerAdapter)
                .registerTypeAdapter(Int::class.javaPrimitiveType, integerAdapter)
                .registerTypeAdapter(Double::class.java, doubleAdapter)
                .registerTypeAdapter(Double::class.javaPrimitiveType, doubleAdapter)
                .disableHtmlEscaping()
                .create()
        return Retrofit.Builder()
                .baseUrl(url) // .client(okHttpBuilder.build())
                .client(unsafeOkHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        private val TAG = ApiClient::class.java.simpleName
        private const val TIME_OUT: Long = 300000
        private const val AUTHORIZATION = "x-access-token"
        private const val AUTHORIZATION_TYPE = "Bearer "

        // Create a trust manager that does not validate certificate chains
        val unsafeOkHttpClient:

        // Install the all-trusting trust manager

        // Create an ssl socket factory with our all-trusting manager

        // Log
        // AUTHORIZATION
                OkHttpClient.Builder
            get() = try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                builder.retryOnConnectionFailure(true)
                builder.interceptors().add(ForbiddenInterceptor())

                // Log
                if (true) {
                    val logInterceptor = HttpLoggingInterceptor()
                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    builder.interceptors().add(logInterceptor)
                }
                // AUTHORIZATION
                Log.d(TAG, "intercept: " + Locale.getDefault().isO3Language.substring(0, 2))
                builder.addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                            .addHeader("Accept-Language", Locale.getDefault().language)
                            .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
    }
}