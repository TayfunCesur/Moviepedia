package com.tayfuncesur.moviepedia.ui.noConnection

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tayfuncesur.moviepedia.R

class NoConnectionFragment : Fragment() {

    companion object {
        fun newInstance() = NoConnectionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.no_connection, container, false)
    }

}