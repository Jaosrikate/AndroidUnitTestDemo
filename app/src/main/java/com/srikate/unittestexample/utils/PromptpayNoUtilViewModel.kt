package com.srikate.unittestexample.utils

import com.srikate.unittestexample.data.TransferType

class PromptpayNoUtilViewModel {

    companion object {
        const val CITIZEN_ID_LENGTH = 13
        const val CITIZEN_ID_FORMATTED = 17
        const val PHONE_NO_LENGTH = 10
        const val PHONE_NO_FORMATTED = 12
        const val PP_DASH_FORMAT = "(\\w{3})(\\w{3})(\\w+)"
        const val CITIZEN_ID_DASH_FORMAT = "(\\w{1})(\\w{4})(\\w{5})(\\w{2})(\\w+)"

        fun getPPFormat(input: String): String {
            return if (input.length == CITIZEN_ID_LENGTH) {
                AppFormat.numberFormat(
                    AppFormat.CITIZEN_ID_PATTERN,
                    AppFormat.CITIZEN_ID_REPLACEMENT,
                    input
                )
            } else {
                AppFormat.numberFormat(
                    AppFormat.PHONE_NO_PATTERN,
                    AppFormat.PHONE_NO_REPLACEMENT,
                    input
                )
            }
        }

        fun getMaskPPFormat(value: String): String {
            return if (value.length == CITIZEN_ID_LENGTH) {
                AppFormat.numberFormat(
                    CITIZEN_ID_DASH_FORMAT,
                    AppFormat.CITIZEN_ID_REPLACEMENT,
                    value
                )
            } else {
                AppFormat.numberFormat(
                    PP_DASH_FORMAT,
                    AppFormat.PHONE_NO_REPLACEMENT,
                    value
                )
            }
        }

        fun getTypePP(input: String): String? {
            return if (input.replace(
                    "-",
                    ""
                ).length > PHONE_NO_LENGTH
            ) TransferType.PROMPTPAY_ID.numType else TransferType.PROMPTPAY_PHONE.numType
        }

        fun validatePPNo(ppNo: String): Boolean {
            val ppNoReplace = ppNo.replace("-", "")
            return ppNoReplace.length == CITIZEN_ID_LENGTH
                    || ((ppNoReplace.startsWith("0") && ppNoReplace.length == PHONE_NO_LENGTH))
        }

        fun phoneNoModifyPattern(_phoneNo: String): String? {
            var phoneNo = _phoneNo.filter { it.isDigit() }
            if (AppFormat.validTextPattern(
                    AppFormat.PHONE_NO_CLEAR_THAI_COUNTRY_CODE,
                    phoneNo
                )
            ) {
                phoneNo = phoneNo.replaceRange(0, 2, "0")
            }
            return AppFormat.numberFormat(
                AppFormat.PHONE_NO_PATTERN,
                AppFormat.PHONE_NO_REPLACEMENT,
                phoneNo
            )
        }

        fun getNAIDFormat(input: String): String {
            return AppFormat.numberFormat(
                AppFormat.CITIZEN_ID_PATTERN,
                AppFormat.CITIZEN_ID_REPLACEMENT,
                input
            )
        }

        fun validateNAIDNo(input : String) : Boolean{
            return input.length == CITIZEN_ID_LENGTH
        }

        fun getMobileNoFormat(input: String): String {
            return AppFormat.numberFormat(
                AppFormat.PHONE_NO_PATTERN,
                AppFormat.PHONE_NO_REPLACEMENT,
                input
            )
        }

        fun validateMobileNo(input : String) : Boolean{
            return input.length == PHONE_NO_LENGTH
        }

    }
}