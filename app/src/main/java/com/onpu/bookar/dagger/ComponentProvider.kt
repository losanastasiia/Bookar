package com.onpu.bookar.dagger

import android.content.Context

// синглтон класс, который занимается создание и предоставлением даггер компонента
object ComponentProvider {
    lateinit var appComponent: AppComponent

    fun getComponent(context: Context): AppComponent {
        if(this::appComponent.isInitialized.not())
            appComponent = DaggerAppComponent.factory().create(context)
        return appComponent
    }
}