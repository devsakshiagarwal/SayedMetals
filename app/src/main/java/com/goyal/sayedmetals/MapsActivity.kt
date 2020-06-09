package com.goyal.sayedmetals

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.goyal.sayedmetals.R.string
import com.goyal.sayedmetals.arch.BaseActivity
import com.goyal.sayedmetals.model.schema.LocationData
import com.goyal.sayedmetals.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.activity_maps.search_view

class MapsActivity : BaseActivity(), OnMapReadyCallback {

  private lateinit var mMap: GoogleMap
  private lateinit var viewModel: MapsViewModel
  private var queryParam = "iron"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_maps)
    initComponents()
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.cancelApiCall()
  }

  private fun initComponents() {
    val mapFragment = supportFragmentManager
        .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
    viewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
    viewModel.setRepository(compRoot()!!.getMarkerRepository())
    doSearch()
    startObserving()
  }

  private fun doSearch() {
    search_view.setOnQueryTextListener(object : OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        queryParam = query!!
        viewModel.fetchMarketData(query)
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }
    })
    search_view.setQuery(queryParam, true)
  }

  private fun startObserving() {
    viewModel.liveDataMarkerList.observe(this, Observer {
      if (it != null) {
        addMarkers(it)
      } else {
        Toast.makeText(this, getString(string.err_marker), Toast.LENGTH_SHORT)
            .show()
      }
    })
    viewModel.liveDataErrorMessage.observe(this, Observer {
      if (it != null) {
        Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.RED)
            .show()
      }
    })
  }

  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap
    mMap.uiSettings.isZoomControlsEnabled = true
    mMap.uiSettings.isScrollGesturesEnabled = true
    mMap.uiSettings.isTiltGesturesEnabled = true
  }

  private fun addMarkers(markersList: List<LocationData>) {
    mMap.clear()
    Snackbar.make(
        findViewById(android.R.id.content),
        markersList.size.toString() + " location/s available for search: " + queryParam,
        Snackbar.LENGTH_LONG
    )
        .setActionTextColor(Color.RED)
        .show()
    for (marker in markersList) {
      val latLng = LatLng(marker.latitude.toDouble(), marker.longitude.toDouble())
      mMap.addMarker(
          MarkerOptions().position(latLng)
              .title(marker.name)
      )
      mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }
  }

}
