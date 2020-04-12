package com.onpu.bookar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.onpu.bookar.dagger.ComponentProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ComponentProvider.getComponent(application)
    }
}
