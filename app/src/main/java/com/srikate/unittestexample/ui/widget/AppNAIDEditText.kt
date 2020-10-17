package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View.OnFocusChangeListener
import androidx.core.content.ContextCompat
import com.srikate.unittestexample.R
import com.srikate.unittestexample.utils.AppConstant
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel

class AppNAIDEditText : BaseAppEditText {

    lateinit var mListener: AppNAIDEditTextListener

    interface AppNAIDEditTextListener {
        fun onNAIDError()
        fun onNAIDEmpty()
        fun onNAIDValid()
    }

    fun setListener(listener: AppNAIDEditTextListener) {
        mListener = listener
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        val tf =
            Typeface.createFromAsset(context.assets, AppConstant.Font.FONT_REGULAR)
        typeface = tf
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                if (::mListener.isInitialized) {
                    mListener.onNAIDValid()
                }
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                error = null
            }

            override fun afterTextChanged(s: Editable) {}
        })

        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (text.toString().isNotEmpty()) {
                    changeLength(PromptpayNoUtilViewModel.CITIZEN_ID_LENGTH)
                    val currentText = text.toString().replace("-", "")
                    text = Editable.Factory.getInstance().newEditable(currentText)
                    setSelection(text.toString().length)
                }
            } else {
                displayValidateNAID()
            }
        }
    }

    fun displayValidateNAID() {
        if (::mListener.isInitialized) {
            return when {
                validateNAID() -> {
                    error = null
                    changeLength(PromptpayNoUtilViewModel.CITIZEN_ID_FORMATTED)
                    val naid = PromptpayNoUtilViewModel.getNAIDFormat(text.toString())
                    setText(naid)
                    mListener.onNAIDValid()
                }
                text.isNullOrEmpty() -> {
                    error = AppConstant.ERROR
                    mListener.onNAIDEmpty()
                }
                else -> {
                    error = AppConstant.ERROR
                    mListener.onNAIDError()
                }
            }
        }
    }

    fun validateNAID(): Boolean {
        val input = text.toString().replace("-", "")
        return when {
            PromptpayNoUtilViewModel.validateNAIDNo(input) -> true
            input.isNullOrEmpty() -> false
            else -> false
        }
    }


    override fun setError(error: CharSequence?) {
        if (error == AppConstant.ERROR) {
            setCompoundDrawables(null, null, null, null)
            background = ContextCompat.getDrawable(context, R.drawable.bg_error_edittext_register)
            setHintTextColor(context.getColor(R.color.pastel_red))
            setTextColor(context.getColor(R.color.dark_coral))
        } else {
            background = ContextCompat.getDrawable(context, R.drawable.bg_edittext_blue_border)
            setHintTextColor(Color.GRAY)
            setTextColor(context.getColor(R.color.dark))
        }
        super.setError(null)
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event)
            return false
        }
        return super.onKeyPreIme(keyCode, event)
    }
}