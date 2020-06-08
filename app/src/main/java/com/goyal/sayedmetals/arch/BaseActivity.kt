package com.goyal.sayedmetals.arch

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import com.goyal.sayedmetals.SayedMetals

@SuppressLint("Registered")
open class BaseActivity : FragmentActivity() {

  private var compRoot: CompRoot? = null

  protected fun compRoot(): CompRoot? {
    if (compRoot == null) {
      compRoot = (application as SayedMetals).compRoot()
    }
    return compRoot
  }
}