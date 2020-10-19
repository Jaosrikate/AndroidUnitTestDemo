# AndroidUnitTestDemo
Android Unit Test Example with Custom Edit Text

# Demo
<img src="https://github.com/Jaosrikate/AndroidUnitTestDemo/blob/main/screenshot/Screen%20Shot%202563-10-17%20at%2019.17.04.png?raw=true" width="250">  <img src="https://github.com/Jaosrikate/AndroidUnitTestDemo/blob/main/screenshot/Screen%20Shot%202563-10-17%20at%2019.17.59.png?raw=true" width="250">  <img src="https://github.com/Jaosrikate/AndroidUnitTestDemo/blob/main/screenshot/Screen%20Shot%202563-10-17%20at%2019.18.14.png?raw=true" width="250">

# Example Test Case

```
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
```

```
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
```

```
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
```
