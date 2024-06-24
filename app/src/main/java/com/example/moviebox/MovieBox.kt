package com.example.moviebox

import android.app.Application
import com.example.moviebox.di.AppComponent
import com.example.moviebox.di.DaggerAppComponent

class MovieBox : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()
}