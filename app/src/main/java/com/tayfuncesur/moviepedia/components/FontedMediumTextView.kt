package com.tayfuncesur.moviepedia.components
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet


class FontedMediumTextView : android.support.v7.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {
        val tf = Typeface.createFromAsset(context.assets, "fonts/manrope-medium.ttf")
        typeface = tf
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val tf = Typeface.createFromAsset(context.assets, "fonts/manrope-medium.ttf")
        typeface = tf
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val tf = Typeface.createFromAsset(context.assets, "fonts/manrope-medium.ttf")
        typeface = tf
    }
}
