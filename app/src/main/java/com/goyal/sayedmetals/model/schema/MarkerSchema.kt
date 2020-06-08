package com.goyal.sayedmetals.model.schema

import com.squareup.moshi.Json

data class MarkerSchema(
  @field:Json(name = "error") val error: Boolean = false,
  @field:Json(name = "status") val status: String = "",
  @field:Json(name = "locationData") val locationData: List<LocationData> = ArrayList()
)

data class LocationData(
  @field:Json(name = "id") val id: String = "",
  @field:Json(name = "name") val name: String = "",
  @field:Json(name = "latitude") val latitude: String = "",
  @field:Json(name = "longitude") val longitude: String = ""
)
