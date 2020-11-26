package org.beetl.android.text;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class TextUtilsTest {

    @Test
    public void testGetChars() {
        int id = 1;
        String tag = "getChars#";

        char[] actual = new char[4];
        TextUtils.getChars("12345", 0, 2, actual, 1);
        char[] expected = new char[]{'\0', '1', '2', '\0'};
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i], tag + id++);
        }
    }

    @Test
    public void testIndexOf() {
        int id = 1;
        String tag = "indexOf#";
        assertEquals(TextUtils.indexOf("12345", "123"), 0, tag + id++);
        assertEquals(TextUtils.indexOf("12345", "23"), 1, tag + id++);
        assertEquals(TextUtils.indexOf("12345", "32"), -1, tag + id++);
    }

    @Test
    public void testLastIndexOf() {
        int id = 1;
        String tag = "lastIndexOf#";
        assertEquals(TextUtils.lastIndexOf("12345", '1'), 0, tag + id++);
        assertEquals(TextUtils.lastIndexOf("12345", '6'), -1, tag + id++);
        assertEquals(TextUtils.lastIndexOf("12345", '5'), 4, tag + id++);
    }

    @Test
    public void testRegionMatches() {
    }

    @Test
    public void testSubstring() {
    }

    @Test
    public void testJoin() {
    }

    @Test
    public void testTestJoin() {
    }

    @Test
    public void testSplit() {
    }

    @Test
    public void testTestSplit() {
    }

    @Test
    public void testIsEmpty() {
        int id = 1;
        String tag = "lastIndexOf#";
        assertTrue(TextUtils.isEmpty(""), tag + id++);
        assertTrue(TextUtils.isEmpty(null), tag + id++);
        assertTrue(TextUtils.isEmpty(new StringBuilder()), tag + id++);
        assertTrue(TextUtils.isEmpty(new StringBuffer()), tag + id++);
        assertTrue(TextUtils.isEmpty(new String()), tag + id++);

        assertFalse(TextUtils.isEmpty("\0"), tag + id++);
    }

    @Test
    public void testNullIfEmpty() {
        int id = 1;
        String tag = "nullIfEmpty#";
        assertNull(TextUtils.nullIfEmpty(""), tag + id++);
    }

    @Test
    public void testEmptyIfNull() {
        int id = 1;
        String tag = "nullIfEmpty#";
        assertNull(TextUtils.nullIfEmpty(""), tag + id++);
    }

    @Test
    public void testFirstNotEmpty() {
        int id = 1;
        String tag = "firstNotEmpty#";

        assertEquals(TextUtils.firstNotEmpty(null, "b"), "b", tag + id++);
        assertEquals(TextUtils.firstNotEmpty("a", "b"), "a", tag + id++);
        assertEquals(TextUtils.firstNotEmpty("", "b"), "b", tag + id++);
    }

    @Test
    public void testLength() {
        int id = 1;
        String tag = "length#";

        assertEquals(TextUtils.length(null), 0, tag + id++);
        assertEquals(TextUtils.length("我a"), 2, tag + id++);
        assertEquals(TextUtils.length("我们"), 2, tag + id++);
        assertEquals(TextUtils.length("\t\n\0"), 3, tag + id++);
    }

    @Test
    public void testSafeIntern() {
    }

    @Test
    public void testGetTrimmedLength() {
    }

    @Test
    public void testTestEquals() {
    }

    @Test
    public void testGetOffsetBefore() {
    }

    @Test
    public void testGetOffsetAfter() {
    }

    @Test
    public void testCouldAffectRtl() {
    }

    @Test
    public void testDoesNotNeedBidi() {
    }

    @Test
    public void testObtain() {
    }

    @Test
    public void testRecycle() {
    }

    @Test
    public void testHtmlEncode() {
    }

    @Test
    public void testConcat() {
    }

    @Test
    public void testIsGraphic() {
    }

    @Test
    public void testIsDigitsOnly() {
    }

    @Test
    public void testIsPrintableAscii() {
    }

    @Test
    public void testIsPrintableAsciiOnly() {
    }

    @Test
    public void testDelimitedStringContains() {
    }

    @Test
    public void testPackRangeInLong() {
    }

    @Test
    public void testUnpackRangeStartFromLong() {
    }

    @Test
    public void testUnpackRangeEndFromLong() {
    }

    @Test
    public void testWrap() {
    }

    @Test
    public void testTrimToSize() {
    }

    @Test
    public void testWithoutPrefix() {
    }
}