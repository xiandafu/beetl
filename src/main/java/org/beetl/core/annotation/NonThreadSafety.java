package org.beetl.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标识一个类或者方法非线程安全
 */
@Retention(RetentionPolicy.SOURCE)
public @interface NonThreadSafety {
}
