package com.srikate.unittestexample

import com.srikate.unittestexample.basic.Hello
import org.junit.Assert
import org.junit.Test

class HelloTest {
    @Test
    fun shouldNotNull() {
        val hello = Hello()
        Assert.assertNotNull(hello)
    }

    @Test
    fun shouldSayHello() {
        val hello = Hello()
        val actualResult = hello.say()
        Assert.assertEquals("Hello", actualResult)
    }
}