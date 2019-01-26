package com.tayfuncesur.moviepedia.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tayfuncesur.moviepedia.base.ViewModelFactory
import com.tayfuncesur.moviepedia.base.ViewModelKey
import com.tayfuncesur.moviepedia.ui.movieDetail.MovieDetailViewModel
import com.tayfuncesur.moviepedia.ui.nowPlaying.NowPlayingViewModel
import com.tayfuncesur.moviepedia.ui.splash.SplashScreenViewModel
import com.tayfuncesur.moviepedia.ui.topRated.TopRatedViewModel
import com.tayfuncesur.moviepedia.ui.upcoming.UpcomingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TopRatedViewModel::class)
    internal abstract fun bindTopRatedViewModel(topRatedViewModel: TopRatedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingViewModel::class)
    internal abstract fun bindUpcomingViewModel(upcomingViewModel: UpcomingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NowPlayingViewModel::class)
    internal abstract fun bindNowPlayingViewModel(nowPlayingViewModel: NowPlayingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    internal abstract fun bindSplashScreenViewModel(splashScreenViewModel: SplashScreenViewModel): ViewModel
}