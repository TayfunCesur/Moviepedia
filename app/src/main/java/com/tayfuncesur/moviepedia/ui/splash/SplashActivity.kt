package com.tayfuncesur.moviepedia.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.tayfuncesur.moviepedia.R
import com.tayfuncesur.moviepedia.ui.main.MainActivity
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlinx.android.synthetic.main.splash_screen.*


class SplashActivity : AppCompatActivity() {

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)
        AndroidInjection.inject(this)


        //Splash Screen'de tam olarak ne yapmam gerektiğini anlayamadım. Bende görsel olarak düşünerek belirli bir süre bir timer ile bekletiyorum.
        //Bu esnada device rotation case ine karşılık timer sıfırlanmasını ve leak olmasını önlemek amacıyla view model kullandım.
        splashScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        splashScreenViewModel.isFinished.observe(this, Observer {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        })
    }
}
