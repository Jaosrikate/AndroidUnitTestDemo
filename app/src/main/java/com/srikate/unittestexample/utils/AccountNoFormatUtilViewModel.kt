package com.srikate.unittestexample.utils

class AccountNoFormatUtilViewModel {

    companion object {

        fun getFormatBankAccountNoReplacement(format: String): String {
            var patternString = ""
            var inGroupCount = 0
            format.forEach { c ->
                //format need to be character
                if (c == '-') {
                    patternString = patternString.plus("(\\d{$inGroupCount})")
                    inGroupCount = 0
                    return@forEach
                }
                inGroupCount++
            }
            if (inGroupCount != 0) {
                patternString = patternString.plus("(\\d{$inGroupCount})")
            }
            return patternString
        }

        fun getFormatBankAccountNo(format: String): String {
            var patternString = ""
            var inGroupCount = 0
            format.forEach { c ->
                //format need to be character
                if (c == '-') {
                    patternString = patternString.plus("(\\d{$inGroupCount})\\-")
                    inGroupCount = 0
                    return@forEach
                }
                inGroupCount++
            }
            if (inGroupCount != 0) {
                patternString = patternString.plus("(\\d{$inGroupCount})")
            }
            return patternString
        }

        fun getFormatMaskingBankAccountNoReplacement(format: String): String {
            var patternString = ""
            var inGroupCount = 0
            format.forEach { c ->
                //format need to be character
                if (c == '-') {
                    patternString = patternString.plus("(\\w{$inGroupCount})")
                    inGroupCount = 0
                    return@forEach
                }
                inGroupCount++
            }
            if (inGroupCount != 0) {
                patternString = patternString.plus("(\\w{$inGroupCount})")
            }
            return patternString
        }

        fun getFormatAccountNoWithDash(
            bankCode: String?,
            format: String,
            noDashAccountNo: String
        ): String? {
            bankCode?.let {
                val replacement = getFormatBankAccountNoReplacement(format)
                return AppFormat.numberFormat(
                    replacement,
                    AppFormat.BANK_ACCOUNT_NO_REPLACEMENT,
                    noDashAccountNo
                )
            } ?: run {
                return noDashAccountNo
            }
        }

        fun getMaskAccountNoWithDash(
            bankCode: String?,
            format: String,
            maskedAccountNo: String
        ): String? {
            bankCode?.let {
                val replacement = getFormatMaskingBankAccountNoReplacement(format)
                return AppFormat.numberFormat(
                    replacement,
                    AppFormat.BANK_ACCOUNT_NO_REPLACEMENT,
                    maskedAccountNo
                )
            } ?: run {
                return maskedAccountNo
            }
        }

        fun validateAccountAfterFormat(dashFormat: String, text: String): Boolean {
            return AppFormat.validTextPattern(
                dashFormat,
                text
            )
        }

        fun formatAccountNoInput(digitFormat: String, text: String): String {
            return AppFormat.numberFormat(
                digitFormat,
                AppFormat.BANK_ACCOUNT_NO_REPLACEMENT,
                text
            )
        }

    }
}