package com.tayfuncesur.moviepedia.di.module

import com.tayfuncesur.moviepedia.ui.main.MainActivity
import com.tayfuncesur.moviepedia.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun bindSplashActivity() : SplashActivity
}