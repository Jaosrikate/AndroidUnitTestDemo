package com.srikate.unittestexample.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText
import com.srikate.unittestexample.R

abstract class BaseAppEditText : AppCompatEditText {

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
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
//        typeface = Typeface.createFromAsset(context.assets, AppConstant.Font.FONT_REGULAR)

        val actionMode = object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.let { menuItem ->
                    menuItem.clear()
                    menuItem.add(
                        0,
                        android.R.id.selectTextMode,
                        0,
                        context.getString(R.string.select)
                    )
                    menuItem.add(
                        0,
                        android.R.id.selectAll,
                        1,
                        context.getString(R.string.select_all)
                    )
                    menuItem.add(0, android.R.id.copy, 1, context.getString(R.string.copy))
                    menuItem.add(0, android.R.id.paste, 1, context.getString(R.string.paste))
                }
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }

        }

        this.customSelectionActionModeCallback = actionMode
        this.customInsertionActionModeCallback = actionMode
    }

    fun onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun changeLength(length: Int) {
        if (length != 0) {
            this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
        } else {
            this.filters = arrayOf<InputFilter>()
        }
    }

}