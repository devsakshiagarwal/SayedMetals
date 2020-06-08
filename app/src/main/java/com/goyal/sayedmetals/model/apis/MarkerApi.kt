package com.goyal.sayedmetals.model.apis

import com.goyal.sayedmetals.arch.Urls
import com.goyal.sayedmetals.model.schema.MarkerSchema
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MarkerApi {

  @FormUrlEncoded
  @POST(Urls.MARKERS_URL)
  fun getMarkersDetails(
    @Field("searchText") searchText: String,
    @Field("apiKey") apiKet: String
  ): Call<MarkerSchema>
}