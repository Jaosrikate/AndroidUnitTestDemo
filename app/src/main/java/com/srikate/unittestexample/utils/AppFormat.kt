package com.srikate.unittestexample.utils

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by pc1 on 1/24/2017 AD.
 */
object AppFormat {
    private val months = arrayOf(
        "ม.ค.",
        "ก.พ.",
        "มี.ค.",
        "เม.ย.",
        "พ.ค.",
        "มิ.ย.",
        "ก.ค.",
        "ส.ค.",
        "ก.ย.",
        "ต.ค.",
        "พ.ย.",
        "ธ.ค."
    )
    private const val DAY_REPLACE = "{day}"
    private const val MONTH_REPLACE = "{month}"
    private const val YEAR_REPLACE = "{year}"
    private const val HOUR_REPLACE = "{hour}"
    private const val MINUTE_REPLACE = "{minute}"
    private const val EXCEPTION = "Exception"
    private const val TIME_SIMPLE_PATTERN = "{day} {month} {year}"
    const val TEXT_NO_SPECIAL_CHAR_PATTERN = "^[A-Za-zก-ูเ-๙0-9 ]+$"
    const val TISCO_BANK_ACCOUNT_NO_PATTERN = "(\\d{4})(\\d{3})(\\d{6})(\\d+)"
    const val TISCO_BANK_ACCOUNT_NO_WITH_DASH_PATTERN = "(\\d{4})\\-(\\d{3})\\-(\\d{6})\\-(\\d{1})"
    const val BANK_ACCOUNT_NO_REPLACEMENT = "$1-$2-$3-$4"
    const val PHONE_NO_PATTERN = "(\\d{3})(\\d{3})(\\d+)"
    const val PHONE_NO_REPLACEMENT = "$1-$2-$3"
    const val PHONE_NO_CLEAR_THAI_COUNTRY_CODE = "^[\u0036]+"
    const val CITIZEN_ID_PATTERN = "(\\d{1})(\\d{4})(\\d{5})(\\d{2})(\\d+)"
    const val CITIZEN_ID_REPLACEMENT = "$1-$2-$3-$4-$5"
    const val MOBILE_NO_PATTERN = "(\\d{3})(\\d{3})(\\d+)"
    const val MOBILE_NO_REPLACEMENT = "(\\d{3})(\\d{3})(\\d+)"
    const val WALLET_ID_PATTERN = "(\\d{2})(\\d{11})(\\d+)"
    const val WALLET_ID_REPLACEMENT = "$1-$2-$3"
    const val ACCOUNT_NAME_MAX_LENGTH = 50

    fun numberFormat(pattern: String, replacement: String?, value: String): String {
        return value.replaceFirst(pattern.toRegex(), replacement!!)
    }

    fun validTextPattern(pattern: String?, actualValue: String): Boolean {
        val p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
        val matcher = p.matcher(actualValue)
        return matcher.find() || actualValue.isEmpty()
    }

}