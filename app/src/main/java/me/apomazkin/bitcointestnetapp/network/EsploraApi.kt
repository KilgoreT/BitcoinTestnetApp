package me.apomazkin.bitcointestnetapp.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EsploraApi {
    @POST("testnet/api/tx")
    fun postTransaction(@Body transactionData: String): Call<String>
}