package com.tayfuncesur.moviepedia.util

import android.os.CountDownTimer

open class Timer protected constructor(timeout: Long, interval: Long, private val tickCallback: IntervalCallback?, private val finishCallback: TimeoutCallback?) : CountDownTimer(timeout, interval) {

    override fun onFinish() {
        finishCallback?.call()
    }

    override fun onTick(millisUntilFinished: Long) {
        tickCallback?.call(millisUntilFinished)
    }

    interface TimeoutCallback {
        fun call()
    }

    interface IntervalCallback {
        fun call(millisUntilFinished: Long)
    }

    companion object {

        fun setTimeout(callback: TimeoutCallback, delay: Int): Timer {
            return setTimeout(callback, java.lang.Long.valueOf(delay.toLong()))
        }

        fun setTimeout(callback: TimeoutCallback, delay: Long): Timer {
            val timer = Timer(delay, delay, null, callback)
            timer.start()
            return timer
        }

        fun setInterval(callback: IntervalCallback, delay: Int): Timer? {
            return setInterval(callback, java.lang.Long.valueOf(delay.toLong()))
        }

        fun setInterval(callback: IntervalCallback?, delay: Long): Timer? {
            if (callback == null) return null
            val timer = Timer(java.lang.Long.MAX_VALUE, delay, callback, null)
            timer.start()
            return timer
        }
    }


}
