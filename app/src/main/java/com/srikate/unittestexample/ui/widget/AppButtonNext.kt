package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.srikate.unittestexample.R
import com.srikate.unittestexample.utils.AppConstant

class AppButtonNext : AppCompatButton {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        typeface = Typeface.createFromAsset(context.assets, AppConstant.Font.FONT_BOLD)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) {
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.nice_blue)
            setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        } else {
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.button_gray)
            setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.warm_grey_two
                )
            )
        }
    }
}