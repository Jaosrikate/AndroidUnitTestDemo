package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.srikate.unittestexample.R
import com.srikate.unittestexample.utils.AccountNoFormatUtilViewModel
import com.srikate.unittestexample.utils.AppConstant
import org.apache.commons.lang3.StringUtils

class AppBankAccountEditText : BaseAppEditText {
    var length: Int = 0
    var maxLength: Int = 14
    var format: String = "###-#-#####-#"
    var dashCount: Int = 0
    var fullLength: Int = 0
    private lateinit var formatBankForReplaceable: String
    lateinit var formatBankNoWithDash: String
    private var isRequireFormatFromSelection = false
    lateinit var mListener: AppBankAccountEditTextListener
    var isFocusFirstTime = true

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    interface AppBankAccountEditTextListener {
        fun onPasteError()
        fun onAccountError()
        fun onAccountValid()
        fun onAccountEmpty()
        fun onRequireFormat(isFocus: Boolean)
        fun setEnableNextButton(isEnable: Boolean)
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
                mListener.onAccountValid()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = null
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        setOnFocusChangeListener { _, hasFocus ->
            if (isRequireFormatFromSelection) {
                mListener.onRequireFormat(hasFocus)
            }
            if (hasFocus) {
                postDelayed({
                    if (!this.hasFocus()) {
                        this.requestFocus()
                        isFocusFirstTime = false
                        text?.let { setSelection(it.length) }
                    }
                }, 200)
            }
            if (!hasFocus) {
                if (text.isNullOrEmpty()) {
                    error = AppConstant.ERROR
                    mListener.onAccountEmpty()
                } else {
                    if (::formatBankForReplaceable.isInitialized) {
                        if (text.toString().length == length) {
                            changeLength(fullLength)
                            text = Editable.Factory.getInstance().newEditable(
                                AccountNoFormatUtilViewModel.formatAccountNoInput(
                                    formatBankForReplaceable,
                                    text.toString()
                                )
                            )
                        }
                    }
                    validateAccountNo()
                }
            } else {
                if (text.toString().isNotEmpty()) {
                    val currentText = text.toString().replace("-", "")
                    text = Editable.Factory.getInstance().newEditable(currentText)
                }
            }
        }
    }

    override fun setError(error: CharSequence?) {
        if (error == AppConstant.ERROR) {
            setCompoundDrawables(null, null, null, null)
            background = ContextCompat.getDrawable(context, R.drawable.bg_error_edittext)
            setHintTextColor(context.getColor(R.color.pastel_red))
            setTextColor(context.getColor(R.color.dark_coral))
        } else {
            background = ContextCompat.getDrawable(context, R.drawable.bg_edittext_blue_border)
            setHintTextColor(Color.GRAY)
            setTextColor(context.getColor(R.color.dark))
        }
        super.setError(null)
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            android.R.id.paste -> {
                onTextPaste()
            }
        }
        return consumed
    }

    /**
     * Text was pasted into the EditText.
     */
    private fun onTextPaste() {
        val textReplaceDash = text.toString().replace("-", "")
        if (this.length != 0) {
            if (textReplaceDash.length <= this.length) {
                if (::mListener.isInitialized) {
                    text = Editable.Factory.getInstance().newEditable(textReplaceDash)
                }
            } else {
                text =
                    Editable.Factory.getInstance().newEditable(textReplaceDash.substring(0, length))
            }
            setSelection(text.toString().length)
        }
    }

    fun setAccountFormat(length: Int, format: String) {
        this.length = length
        this.format = format
        this.formatBankForReplaceable =
            AccountNoFormatUtilViewModel.getFormatBankAccountNoReplacement(format)
        this.formatBankNoWithDash =
            AccountNoFormatUtilViewModel.getFormatBankAccountNo(format)
        this.dashCount = StringUtils.countMatches(format, "-")
        this.fullLength = length + dashCount
        changeLength(maxLength)
        validateAccountNo()
        enableAccountInput()
    }

    private fun enableAccountInput() {
        isEnabled = true
    }

    fun setIsRequireFormat(isRequire: Boolean) {
        this.isRequireFormatFromSelection = isRequire
    }

    fun setListener(listener: AppBankAccountEditTextListener) {
        mListener = listener
    }

    fun validateAccountNo() {
        if (::mListener.isInitialized && ::formatBankNoWithDash.isInitialized) {
            return if (!AccountNoFormatUtilViewModel.validateAccountAfterFormat(
                    dashFormat = formatBankNoWithDash,
                    text = text.toString()
                )
            ) {
                error = AppConstant.ERROR
                mListener.onAccountError()
                mListener.setEnableNextButton(false)
            } else {
                error = null
                mListener.onAccountValid()
                mListener.setEnableNextButton(true)
            }
        }
    }
}