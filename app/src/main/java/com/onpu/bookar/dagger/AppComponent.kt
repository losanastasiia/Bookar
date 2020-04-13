package com.onpu.bookar.dagger

import android.content.Context
import com.onpu.bookar.view.find.FindBookViewModel
import com.onpu.bookar.view.info.BookInfoViewModel
import com.onpu.bookar.view.saved.SavedBookViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// компонент даггера, который отвечает за предоставление зависимостей классам
@Component(
    modules = [AppModule::class]
)
@Singleton
interface AppComponent {
    fun inject(viewModel: FindBookViewModel)
    fun inject(viewModel: SavedBookViewModel)
    fun inject(viewModel: BookInfoViewModel)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}