package com.srikate.unittestexample

import com.srikate.unittestexample.utils.AccountNoFormatUtilViewModel
import junit.framework.Assert.assertTrue
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class AccountNoFormatTest {

    @Test
    @Parameters(
        "####-###-######-#|(\\d{4})(\\d{3})(\\d{6})(\\d{1})",
        "###-#-#####-#|(\\d{3})(\\d{1})(\\d{5})(\\d{1})",
        "###-#-#####-###|(\\d{3})(\\d{1})(\\d{5})(\\d{3})",
        "###-#-#####-##|(\\d{3})(\\d{1})(\\d{5})(\\d{2})"
    )
    fun `validate when read format from bank list and get replacement `(format: String, actualPattern: String) {
        val pattern = AccountNoFormatUtilViewModel.getFormatBankAccountNoReplacement(format)
        Assert.assertEquals(pattern, actualPattern)
    }

    @Test
    @Parameters(
        "####-###-######-#|(\\d{4})\\-(\\d{3})\\-(\\d{6})\\-(\\d{1})",
        "###-#-#####-#|(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{1})",
        "###-#-#####-###|(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{3})",
        "###-#-#####-##|(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{2})"
    )
    fun `validate when read format from bank list and get dash pattern `(format: String, actualPattern: String) {
        val pattern = AccountNoFormatUtilViewModel.getFormatBankAccountNo(format)
        Assert.assertEquals(pattern, actualPattern)
    }

    @Test
    @Parameters(
        "(\\d{4})(\\d{3})(\\d{6})(\\d{1})|12345678901234|1234-567-890123-4",
        "(\\d{3})(\\d{1})(\\d{5})(\\d{1})|1234567890|123-4-56789-0",
        "(\\d{3})(\\d{1})(\\d{5})(\\d{3})|123456789012|123-4-56789-012",
        "(\\d{3})(\\d{1})(\\d{5})(\\d{2})|12345678901|123-4-56789-01"
    )
    fun `validate input account no after formatted `(
        pattern: String,
        input: String,
        actualResult: String
    ) {
        val result = AccountNoFormatUtilViewModel.formatAccountNoInput(pattern, input)
        Assert.assertEquals(result, actualResult)
    }

    @Test
    @Parameters(
        "(\\d{4})\\-(\\d{3})\\-(\\d{6})\\-(\\d{1})|1234-567-890123-4|true",
        "(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{1})|123-4-56789-0|true",
        "(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{3})|123-4-56789-012|true",
        "(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{2})|123-4-56789-01|true",
        "(\\d{3})\\-(\\d{1})\\-(\\d{5})\\-(\\d{2})|123-45-6789-01|false",
        "(\\d{4})\\-(\\d{3})\\-(\\d{6})\\-(\\d{1})|123-45-6789-01234|false"
    )
    fun `validate account no pattern `(
        dashFormat: String,
        inputFormatted: String,
        expectResult: Boolean
    ) {
        val actualResult =
            AccountNoFormatUtilViewModel.validateAccountAfterFormat(dashFormat, inputFormatted)
        Assert.assertEquals(expectResult, actualResult)
    }

    @Test
    @Parameters(
        "067|12345678901234|####-###-######-#|1234-567-890123-4", //14
        "070|1234567890|###-#-#####-#|123-4-56789-0", //10
        "080|123456789012|###-#-#####-###|123-4-56789-012", //12
        "039|12345678901|###-#-#####-##|123-4-56789-01" //11
    )
    fun `validate dash format `(
        bankCode: String,
        noDashValue: String,
        format : String,
        expectResult: String
    ) {
        val actualResult =
            AccountNoFormatUtilViewModel.getFormatAccountNoWithDash(bankCode, format , noDashValue )
        Assert.assertEquals(expectResult, actualResult)
    }

    @Test
    @Parameters(
        "067|xxxxxxxxx1234x|####-###-######-#|xxxx-xxx-xx1234-x", //14
        "070|xxxxx1234x|###-#-#####-#|xxx-x-x1234-x", //10
        "080|xxxxxxx1234x|###-#-#####-###|xxx-x-xxx12-34x", //12
        "039|xxxxxx1234x|###-#-#####-##|xxx-x-xx123-4x" //11
    )
    fun `validate mask account with dash `(
        bankCode: String,
        noDashValue: String,
        format : String,
        expectResult: String
    ) {
        val actualResult =
            AccountNoFormatUtilViewModel.getMaskAccountNoWithDash(bankCode, format , noDashValue )
        Assert.assertEquals(expectResult, actualResult)
    }

    @Test
    @Parameters(
        "####-###-######-#|(\\w{4})(\\w{3})(\\w{6})(\\w{1})",
        "###-#-#####-#|(\\w{3})(\\w{1})(\\w{5})(\\w{1})",
        "###-#-#####-###|(\\w{3})(\\w{1})(\\w{5})(\\w{3})",
        "###-#-#####-##|(\\w{3})(\\w{1})(\\w{5})(\\w{2})"
    )
    fun `validate when read format from bank list and get masking pattern for replace `(format: String, actualPattern: String) {
        val pattern = AccountNoFormatUtilViewModel.getFormatMaskingBankAccountNoReplacement(format)
        Assert.assertEquals(pattern, actualPattern)
    }

}