package com.hc;

import com.hc.library.util.PinyinUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void testPinyin(){
//        String str = PinyinUtils.getFirstSpell("喆");
//        assertEquals(str,"z");
        String str = PinyinUtils.getFirstSpell("--Z");
        assertEquals(str,"zZ");
    }
}