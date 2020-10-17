package com.srikate.unittestexample.utils

import android.content.Context
import android.graphics.Typeface
import com.srikate.unittestexample.utils.AppConstant.Font.Companion.FONT_REGULAR

class AppConstant private constructor() {
    class Font private constructor() {
        companion object {
            const val FONT_BOLD = "font/fc_friday_medium.ttf"
            const val FONT_REGULAR = "font/fc_friday_medium.ttf"
        }

        init {
            throw IllegalStateException(MESSAGE)
        }
    }

    companion object {

        var isIsDebug = true
            private set
        private const val MESSAGE = "Utility Class"
        const val ERROR = "error"
        fun initConstant() {
            //enable Logcat on some mode
            isIsDebug = true

        }

        fun getAppTypeFace(context: Context): Typeface {
            return Typeface.createFromAsset(
                context.assets,
                FONT_REGULAR
            )
        }

        fun isIsDebugs(): Boolean {
            return isIsDebug
        }


    }

    init {
        throw IllegalStateException(MESSAGE)
    }
}