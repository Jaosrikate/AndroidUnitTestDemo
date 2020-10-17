package com.srikate.unittestexample

import com.srikate.unittestexample.data.TransferType
import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class PromptpayTest {

    @Test
    @Parameters(
        "0878889900|087-888-9900",
        "1102005467988|1-1020-05467-98-8"
    )
    fun `validate promptpay format from input string`(phoneNo : String , expected: String){
        val actual = PromptpayNoUtilViewModel.getPPFormat(phoneNo)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `validate get string promtpay type from input length`(){
        val actualTypeID = PromptpayNoUtilViewModel.getTypePP("1102220345699")
        Assert.assertEquals(TransferType.PROMPTPAY_ID.numType, actualTypeID)
        val actualTypePhoneNo = PromptpayNoUtilViewModel.getTypePP("0837888971")
        Assert.assertEquals(TransferType.PROMPTPAY_PHONE.numType, actualTypePhoneNo)
    }

    @Test
    @Parameters(
        "0878889900|true",
        "1102005467988|true",
        "1224|false",
        "1878889900|false",
        "028757587|false",
        "66987888971|false"
    )
    fun `is promptpay number valid?`(input : String , expected: Boolean){
        val actual = PromptpayNoUtilViewModel.validatePPNo(input)
        Assert.assertEquals(expected, actual)
    }

    @Test
    @Parameters(
        "\\*1234|1234",
        "\\+6626337945|026-337-945",
        "\\+66837888971|083-788-8971",
        "66837888971|083-788-8971",
        "\\*137|137",
        "083 788 8971|083-788-8971",
        "\\*902\\*01\\#|90201",
        "(+66)837888971|083-788-8971",
        "aa66837888971|083-788-8971",
        "+660837888971|008-378-88971",
        "020302222|020-302-222"
    )
    fun `modify promtpay number that include invalid character to valid phone number pattern`(input : String , expected: String){
        val actual = PromptpayNoUtilViewModel.phoneNoModifyPattern(input)
        Assert.assertEquals(expected, actual)
    }
}