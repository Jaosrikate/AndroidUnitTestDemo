package com.srikate.unittestexample.data

enum class TransferType(val numType: String) {
    ONUS_SAME_ACCOUNT("1"),
    ONUS_DIFF_ACCOUNT("3"),
    PROMPTPAY_ID("NATID"),
    PROMPTPAY_PHONE("MSISDN"),
    EWALLET_ID("EWALLETID")
}