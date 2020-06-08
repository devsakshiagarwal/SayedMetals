package com.goyal.sayedmetals

import android.app.Application
import com.goyal.sayedmetals.arch.CompRoot

class SayedMetals : Application() {

  private lateinit var compRoot: CompRoot

  override fun onCreate() {
    super.onCreate()
    compRoot = CompRoot()
  }

  fun compRoot() = compRoot
}