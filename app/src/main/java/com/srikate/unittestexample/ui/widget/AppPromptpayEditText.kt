package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.srikate.unittestexample.R
import com.srikate.unittestexample.utils.AppConstant
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel.Companion.CITIZEN_ID_FORMATTED
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel.Companion.CITIZEN_ID_LENGTH
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel.Companion.PHONE_NO_FORMATTED
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel.Companion.PHONE_NO_LENGTH

class AppPromptpayEditText : BaseAppEditText {
    lateinit var mListener: AppPromptpayEditTextListener

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    interface AppPromptpayEditTextListener {
        fun onPPNoError()
        fun onPPValid()
        fun onPPEmpty()
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
        typeface = Typeface.createFromAsset(context.assets, AppConstant.Font.FONT_REGULAR)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (::mListener.isInitialized) {
                    mListener.onPPValid()
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = null
            }

            override fun afterTextChanged(s: Editable) {
                val input = text.toString().replace("-", "")
                if (input.isEmpty() || input.length == 13) {
                    changeLength(CITIZEN_ID_LENGTH)
                }
            }
        })

        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (text.toString().isNotEmpty()) {
                    checkLength()
                    val ppNo = PromptpayNoUtilViewModel.getPPFormat(text.toString())
                    setText(ppNo)
                    changeLength(ppNo.length)
                    displayValidatePPNo()
                } else {
                    error = AppConstant.ERROR
                    if (::mListener.isInitialized) {
                        mListener.onPPEmpty()
                    }
                }
            } else {
                if (text.toString().isNotEmpty()) {
                    changeLength(CITIZEN_ID_LENGTH)
                    val currentText = text.toString().replace("-", "")
                    text = Editable.Factory.getInstance().newEditable(currentText)
                }
            }
        }
    }

    private fun checkLength() {
        when (text.toString().length) {
            CITIZEN_ID_LENGTH -> changeLength(CITIZEN_ID_FORMATTED)
            PHONE_NO_LENGTH -> changeLength(PHONE_NO_FORMATTED)
        }
    }

    override fun setError(error: CharSequence?) {
        if (error == AppConstant.ERROR) {
            setCompoundDrawables(null, null, null, null)
            background = context.getDrawable(R.drawable.bg_error_edittext)
            setHintTextColor(context.getColor(R.color.pastel_red))
            setTextColor(context.getColor(R.color.dark_coral))
        } else {
            background = context.getDrawable(R.drawable.bg_edittext_blue_border)
            setHintTextColor(Color.GRAY)
            setTextColor(context.getColor(R.color.dark))
        }
        super.setError(null)
    }

    fun setListener(listener: AppPromptpayEditTextListener) {
        mListener = listener
    }

    fun displayValidatePPNo() {
        val input = text.toString().replace("-", "")
        if (::mListener.isInitialized) {
            return if (PromptpayNoUtilViewModel.validatePPNo(input)) {
                error = null
                mListener.onPPValid()
            } else {
                error = AppConstant.ERROR
                mListener.onPPNoError()
            }
        }
    }

    fun validatePPNo(): Boolean {
        val input = text.toString().replace("-", "")
        if (::mListener.isInitialized) {
            return PromptpayNoUtilViewModel.validatePPNo(input)
        }
        return false
    }
}