package com.tayfuncesur.moviepedia.di

import android.app.Application
import com.tayfuncesur.moviepedia.App
import com.tayfuncesur.moviepedia.di.module.AppModule
import com.tayfuncesur.moviepedia.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}