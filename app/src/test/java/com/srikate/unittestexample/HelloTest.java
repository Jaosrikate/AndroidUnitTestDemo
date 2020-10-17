package com.srikate.unittestexample;

import com.srikate.unittestexample.basic.Hello;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HelloTest {

    @Test
    public void shouldNotNull(){
        Hello hello = new Hello();
        assertNotNull(hello);
    }

    @Test
    public void shouldSayHello() {
        Hello hello = new Hello();
        String actualResult = hello.say();
        assertEquals("Hello", actualResult);
    }

}