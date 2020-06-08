package com.goyal.sayedmetals.model.repository

import com.goyal.sayedmetals.Configuration
import com.goyal.sayedmetals.model.apis.MarkerApi
import com.goyal.sayedmetals.model.schema.LocationData
import com.goyal.sayedmetals.model.schema.MarkerSchema
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarkerRepository(private val markerApi: MarkerApi) {

  private lateinit var markerCall: Call<MarkerSchema>
  private lateinit var markerApiListener: MarkerApiListener

  fun setMarkerApiListener(markerApiListener: MarkerApiListener) {
    this.markerApiListener = markerApiListener
  }

  fun fetchMarkerData(searchString: String = "") {
    markerCall = markerApi.getMarkersDetails(searchString, Configuration.API_KEY)
    markerCall.enqueue(object : Callback<MarkerSchema> {
      override fun onResponse(call: Call<MarkerSchema>, response: Response<MarkerSchema>) {
        if (response.code() == 200) {
          val markerSchema = response.body()!!
          markerApiListener.onMarkerResponseSuccess(markerSchema.locationData)
        } else {
          markerApiListener.onMarkerResponseFailure("Something does not seem to be right!")
        }
      }

      override fun onFailure(call: Call<MarkerSchema>, t: Throwable) {
        markerApiListener.onMarkerResponseFailure(t.localizedMessage!!)
      }
    })
  }

  fun cancelApiCalls() {
    markerCall.cancel()
  }

  interface MarkerApiListener {
    fun onMarkerResponseSuccess(markerList: List<LocationData>)

    fun onMarkerResponseFailure(message: String)
  }

}