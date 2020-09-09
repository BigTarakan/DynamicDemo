package com.bigtarakan.dynamicdemo.net

import com.google.gson.JsonElement
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException


interface ApiService {

    @GET("main")
    fun getMain(): Observable<JsonElement>

    @GET("about")
    fun getAbout(): Observable<JsonElement>

    @GET("help")
    fun getHelp(): Observable<JsonElement>

    @GET("education")
    fun getEducation(): Observable<JsonElement>

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .baseUrl("https://62.113.112.43/test/api/")
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}