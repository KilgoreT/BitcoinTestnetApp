package me.apomazkin.bitcointestnetapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Network {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()

    val apiService: EsploraApi = retrofit.create(EsploraApi::class.java)

    companion object {
        private const val BASE_URL = "https://blockstream.info/"
        private lateinit var instance: Network
        fun getInstance(): Network {
            if (::instance.isInitialized.not()) {
                instance = Network()
            }
            return instance
        }
    }
}