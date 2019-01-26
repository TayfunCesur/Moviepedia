package com.tayfuncesur.moviepedia.ui.main

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.tayfuncesur.moviepedia.R
import com.tayfuncesur.moviepedia.ui.noConnection.NoConnectionFragment
import com.tayfuncesur.moviepedia.util.FONT_PATH
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.miguelcatalan.materialsearchview.MaterialSearchView
import android.support.v4.view.ViewPager
import android.widget.ImageButton
import com.tayfuncesur.moviepedia.model.DataTypes
import com.tayfuncesur.moviepedia.model.EventData
import com.tayfuncesur.moviepedia.model.Movie
import com.tayfuncesur.moviepedia.ui.movieDetail.MovieDetailFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        setUpViewPager()

        setUpNetworkBroadcast()

        setUpSearchView()

        setSupportActionBar(toolbar)
    }

    private fun setUpViewPager() {

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this)
        viewpager.adapter = viewPagerAdapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                search_view.closeSearch()
            }
        })
        tabs.setTabTextColors(
            ContextCompat.getColor(this, R.color.white),
            ContextCompat.getColor(this, R.color.colorAccent)
        )
        tabs.setupWithViewPager(viewpager)

        setUpTabviewFont()
    }

    private fun setUpTabviewFont() {
        val typeface = Typeface.createFromAsset(this.assets, FONT_PATH)
        for (tabIndex in 0 until tabs.tabCount) {
            val tabTextView =
                ((tabs.getChildAt(0) as LinearLayout).getChildAt(tabIndex) as LinearLayout).getChildAt(1) as TextView
            tabTextView.isAllCaps = false
            tabTextView.typeface = typeface
        }
    }

    private fun setUpNetworkBroadcast() {
        //Connnectivity Change action has been deprecated on android O. To support before, We must use broadcast receiver. To support 0 and above,
        //we must use JobScheduler api. Here is thirdparty to implement this which is very simple and useful.
        compositeDisposable.add(
            ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isConnectedToInternet ->
                    val noConnectionFragment = NoConnectionFragment.newInstance()
                    if (!isConnectedToInternet) {
                        supportFragmentManager.beginTransaction().setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                            .add(android.R.id.content, noConnectionFragment, getString(R.string.noConnectionStr))
                            .addToBackStack(getString(R.string.noConnectionStr)).commit()
                    } else {
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.executePendingTransactions()
                    }
                }
        )
    }

    private fun setUpSearchView() {
        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                EventBus.getDefault().post(EventData(DataTypes.OnQueryTextSubmit, query))
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                EventBus.getDefault().post(EventData(DataTypes.OnQueryTextChange, newText))
                return false
            }
        })

        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                EventBus.getDefault().post(EventData(DataTypes.OnSearchViewShown, null))
            }

            override fun onSearchViewClosed() {
                EventBus.getDefault().post(EventData(DataTypes.OnSearchViewClosed, null))
            }
        })

        obtainRightIcon()?.setOnClickListener {
            search_view.setQuery("", false)
            EventBus.getDefault().post(EventData(DataTypes.OnSearchViewRightIconClicked, null))
        }
    }

    private fun obtainRightIcon(): ImageButton? {
        return search_view.findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.action_search)
        search_view.setMenuItem(item)

        return true
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun onEvent(data: EventData) {
        if (data.dataTypes == DataTypes.OnMovieClicked) {
            supportFragmentManager.beginTransaction().setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            ).replace(
                R.id.mainContainer, MovieDetailFragment.newInstance(data.data!! as Movie), getString(R.string.movieDetail)
            ).addToBackStack(getString(R.string.movieDetail)).commit()
            search_view.closeSearch()
        }
    }

    override fun onBackPressed() {
        toolbar_title.text = getString(R.string.app_name)
        if (supportFragmentManager.backStackEntryCount > 0) {
            val founded = supportFragmentManager.findFragmentByTag(getString(R.string.noConnectionStr)) != null
            //If there is no internet connection, NoConnectionFragment is on screen now. If back pressed, finish the app
            if (founded) finishAffinity()
            else supportFragmentManager.popBackStackImmediate()
        } else {
            if (search_view.isSearchOpen) {
                search_view.closeSearch()
            } else {
                if (viewpager.currentItem != 0) {
                    viewpager.currentItem = 0
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
