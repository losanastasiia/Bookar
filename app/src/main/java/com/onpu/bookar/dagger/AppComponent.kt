package com.onpu.bookar.dagger

import android.content.Context
import com.onpu.bookar.view.find.FindBookViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class]
)
@Singleton
interface AppComponent {
    fun inject(viewModel: FindBookViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}