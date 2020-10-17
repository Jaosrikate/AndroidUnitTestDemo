package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import com.srikate.unittestexample.R
import com.srikate.unittestexample.utils.AppConstant
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel


class AppMobileNoEditText : BaseAppEditText {

    lateinit var mListener: AppMobileNoEditTextListener
    var isDelete = false

    interface AppMobileNoEditTextListener {
        fun onMobileNoError()
        fun onMobileNoValid()
        fun onMobileNoEmpty()
    }

    fun setListener(listener: AppMobileNoEditTextListener) {
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
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                error = null
                isDelete = before > count
                if (isDelete) {
                    validateMobileNo()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (text.toString().isNotEmpty()) {
                    changeLength(PromptpayNoUtilViewModel.PHONE_NO_FORMATTED)
                    val mobileNo = PromptpayNoUtilViewModel.getMobileNoFormat(text.toString())
                    setText(mobileNo)
                    validateMobileNo()
                } else {
                    error = AppConstant.ERROR
                    if (::mListener.isInitialized) {
                        mListener.onMobileNoEmpty()
                    }
                }
            } else {
                if (text.toString().isNotEmpty()) {
                    changeLength(PromptpayNoUtilViewModel.PHONE_NO_LENGTH)
                    val currentText = text.toString().replace("-", "")
                    text = Editable.Factory.getInstance().newEditable(currentText)
                    setSelection(text.toString().length)
                }
            }
        }

        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                clearFocus()
            }
            false
        }
    }

    fun validateMobileNo(): Boolean {
        val input = text.toString().replace("-", "")
        if (::mListener.isInitialized) {
            return if (PromptpayNoUtilViewModel.validateMobileNo(input)) {
                error = null
                mListener.onMobileNoValid()
                true
            } else {
                error = AppConstant.ERROR
                mListener.onMobileNoError()
                false
            }
        }
        return false
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