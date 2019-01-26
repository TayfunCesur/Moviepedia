package com.tayfuncesur.moviepedia.di.module

import com.tayfuncesur.moviepedia.ui.movieDetail.MovieDetailFragment
import com.tayfuncesur.moviepedia.ui.nowPlaying.NowPlayingFragment
import com.tayfuncesur.moviepedia.ui.topRated.TopRatedFragment
import com.tayfuncesur.moviepedia.ui.upcoming.UpcomingFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTopRatedFragment(): TopRatedFragment

    @ContributesAndroidInjector
    abstract fun contributeUpcomingFragment(): UpcomingFragment

    @ContributesAndroidInjector
    abstract fun contributeNowplayingFragment(): NowPlayingFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment
}
