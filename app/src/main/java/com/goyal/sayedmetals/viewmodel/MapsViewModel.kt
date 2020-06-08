package com.goyal.sayedmetals.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goyal.sayedmetals.model.repository.MarkerRepository
import com.goyal.sayedmetals.model.schema.LocationData

class MapsViewModel : ViewModel(), MarkerRepository.MarkerApiListener {

  private lateinit var markerRepository: MarkerRepository
  var liveDataMarkerList = MutableLiveData<List<LocationData>>()
  var liveDataErrorMessage = MutableLiveData<String>()

  fun setRepository(markerRepository: MarkerRepository) {
    this.markerRepository = markerRepository
    markerRepository.setMarkerApiListener(this)
    markerRepository.fetchMarkerData("steel")
  }

  fun cancelApiCall() {
    markerRepository.cancelApiCalls()
  }

  override fun onMarkerResponseSuccess(markerList: List<LocationData>) {
    liveDataMarkerList.value = markerList
  }

  override fun onMarkerResponseFailure(message: String) {
    liveDataErrorMessage.value = message
  }
}