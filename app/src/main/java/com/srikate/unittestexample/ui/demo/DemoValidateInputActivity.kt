package com.srikate.unittestexample.ui.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.srikate.unittestexample.R
import com.srikate.unittestexample.ui.widget.AppButtonNext
import com.srikate.unittestexample.ui.widget.AppMobileNoEditText
import com.srikate.unittestexample.ui.widget.AppNAIDEditText

class DemoValidateInputActivity : AppCompatActivity(), AppNAIDEditText.AppNAIDEditTextListener,
    AppMobileNoEditText.AppMobileNoEditTextListener {
    private lateinit var edtCitizenId: AppNAIDEditText
    private lateinit var edtMobileNo: AppMobileNoEditText
    private lateinit var tvIDNoError: TextView
    private lateinit var tvMobileNoError: TextView
    private lateinit var btLogin: AppButtonNext

    private var isNAIDValid = false
    private var isMobileNoValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_demo_validate_input)

        edtCitizenId = findViewById(R.id.edtCitizenID)
        edtMobileNo = findViewById(R.id.edtMobileNo)
        tvIDNoError = findViewById(R.id.tvIDNoError)
        tvMobileNoError = findViewById(R.id.tvMobileNoError)
        btLogin = findViewById(R.id.btLogin)
        btLogin.isEnabled = false

        edtCitizenId.setListener(this)
        edtMobileNo.setListener(this)

        btLogin.setOnClickListener {
            if (validateData()) {
                Toast.makeText(
                    this,
                    "Hello , " + edtCitizenId.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onNAIDError() {
        isNAIDValid = false
        tvIDNoError.visibility = View.VISIBLE
        tvIDNoError.text = getString(R.string.naid_error)
        btLogin.isEnabled = false
    }

    override fun onNAIDEmpty() {
        isNAIDValid = false
        tvIDNoError.visibility = View.VISIBLE
        tvIDNoError.text = getString(R.string.please_fill_citizen_id)
        btLogin.isEnabled = false
    }

    override fun onNAIDValid() {
        isNAIDValid = true
        tvIDNoError.visibility = View.INVISIBLE
        validateData()
    }

    override fun onMobileNoError() {
        isMobileNoValid = false
        tvMobileNoError.visibility = View.VISIBLE
        tvMobileNoError.text = getString(R.string.mobile_no_error)
        btLogin.isEnabled = false
    }

    override fun onMobileNoValid() {
        isMobileNoValid = true
        tvMobileNoError.visibility = View.INVISIBLE
        validateData()
    }

    override fun onMobileNoEmpty() {
        isMobileNoValid = false
        tvMobileNoError.visibility = View.VISIBLE
        tvMobileNoError.text = getString(R.string.please_fill_mobile)
        btLogin.isEnabled = false
    }

    private fun validateData(): Boolean {
        val isDataValid = isNAIDValid && isMobileNoValid
        btLogin.isEnabled = isDataValid
        return isDataValid
    }

}