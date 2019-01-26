package com.tayfuncesur.moviepedia.util

import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class PreferencesUtil @Inject constructor() {

    companion object {
        fun newInstance() = PreferencesUtil()
    }

    private lateinit var sharedPreferences: SharedPreferences

    internal fun sharedPreferences(sharedPreferences: SharedPreferences): PreferencesUtil {
        this@PreferencesUtil.sharedPreferences = sharedPreferences
        return this@PreferencesUtil
    }

    private fun getFavList(): JSONArray {
        val result = this.sharedPreferences.getString(FAV_LIST, null)
        return if (result == null)
            JSONArray()
        else {
            JSONArray(result)
        }
    }

    fun setFav(movieId: Int) {
        val obj = JSONObject()
        obj.put(1.toString(), movieId.toString())
        val newArray = getFavList().put(obj.toString())
        this.sharedPreferences.edit().putString(FAV_LIST, newArray.toString()).apply()
    }

    fun isFav(movieId: Int): Boolean {
        val arr = getFavList()
        for (i in 0..(arr.length() - 1)) {
            if ((JSONObject(arr.getString(i))).getInt(1.toString()) == movieId)
                return true
        }
        return false
    }

    fun removeFav(movieId: Int) {
        val arr = getFavList()
        val newArr = JSONArray()
        for (i in 0..(arr.length() - 1)) {
            if ((JSONObject(arr.getString(i))).getInt(1.toString()) != movieId)
                newArr.put(arr.get(i))
        }
        this.sharedPreferences.edit().putString(FAV_LIST, newArr.toString()).apply()
    }


}