package org.beetl.annotation;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * IDEA配置： Preference >> Inspections >> Java >> Probable Bugs >> @NotNull/@Nullable Problems
 * >> configure Annotations >> 添加 @NonNull 和 @Nullable 的全限定类名
 */
public class AnnotationTest {

    @Test
    public void testAll() {
        String res = formatValue(null); // 编译器提示： Passing 'null' argument to parameter annotated as @NotNull

        testNullableObject().toString(); // 编译器提示： Method invocation 'toString' may produce 'NullPointerException'

        testMagicConstant(3); // 编译器提示： Should be one of: AnnotationTest.REQUEST_LOCATION_CODE, AnnotationTest.REQUEST_CAMERA_CODE, ...
    }

    @NotNull
    public String formatValue(@NotNull String value) {
        return "format: " + value;
    }

    @NotNull
    public String getNotNullString() {
        return null; // 编译器提示： 'null' is returned by the method declared as @NonNull
    }

    @Nullable
    public Object testNull() {
        return null;
    }

    @Nullable // 编译器提示：Primitive type members cannot be annotated
    public int testNullableInt() {
        return -2;
    }

    @Nullable
    public Object testNullableObject() {
        return null;
    }



    /** req code - 0 */
    public static final int REQUEST_CODE_0 = 0;
    /** req code - 1 */
    public static final int REQUEST_CODE_1 = 1;
    /** req code - 2 */
    public static final int REQUEST_CODE_2 = 2;
    /** 魔法数字检查的用法示例 */
    @MagicConstant(intValues = {REQUEST_CODE_0, REQUEST_CODE_1, REQUEST_CODE_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReqCode {
    }
    public void testMagicConstant(@ReqCode int reqCode) {
    }

}
