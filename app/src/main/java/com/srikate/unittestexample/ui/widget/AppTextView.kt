package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.srikate.unittestexample.utils.AppConstant

class AppTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {
        createFont()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        createFont()
    }

    constructor(context: Context?, attr: AttributeSet?, defStyle: Int) : super(
        context!!, attr, defStyle
    ) {
        createFont()
    }

    private fun createFont() {
        val font = Typeface.createFromAsset(context.assets, AppConstant.Font.FONT_REGULAR)
        setTypeface(font)
    }
}