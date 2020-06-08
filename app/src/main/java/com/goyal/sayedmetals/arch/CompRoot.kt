package com.goyal.sayedmetals.arch

import com.goyal.sayedmetals.Configuration
import com.goyal.sayedmetals.model.apis.MarkerApi
import com.goyal.sayedmetals.model.repository.MarkerRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CompRoot {

  private lateinit var retrofit: Retrofit
  private lateinit var client: OkHttpClient

  init {
    initHttpClient()
    initRetrofit()
  }

  private fun initHttpClient() {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    client =
      OkHttpClient().newBuilder()
          .addInterceptor(loggingInterceptor)
          .build()
  }

  private fun initRetrofit() {
    retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Configuration.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
  }

  private fun getRetrofit() = retrofit

  private fun getMarkerApi(): MarkerApi = getRetrofit().create(MarkerApi::class.java)

  fun getMarkerRepository() = MarkerRepository(getMarkerApi())

}