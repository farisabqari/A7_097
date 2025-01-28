package com.example.myapplicationujian

import android.app.Application
import com.example.myapplicationujian.DI.AppContainer
import com.example.myapplicationujian.DI.KlinikContainer

class TerapiApplications : Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KlinikContainer()
    }
}