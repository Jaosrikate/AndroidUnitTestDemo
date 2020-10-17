package com.srikate.unittestexample

import com.srikate.unittestexample.utils.PromptpayNoUtilViewModel
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class NAIDTest{

    @Test
    fun NAIDValidate_Correct_ReturnTrue(){
        assertTrue(PromptpayNoUtilViewModel.validateNAIDNo("1234567890123"))
    }

    @Test
    fun NAIDValidate_Invalid_ReturnFalse(){
        assertFalse(PromptpayNoUtilViewModel.validateNAIDNo("1234567"))
    }

    @Test
    @Parameters(
        "1234567890123|1-2345-67890-12-3"
    )
    fun NAIDformat_InputValue(inputValue: String, expectedResult: String) {
        val result = PromptpayNoUtilViewModel.getNAIDFormat(inputValue)
        Assert.assertEquals(result, expectedResult)
    }
}