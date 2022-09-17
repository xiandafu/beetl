package org.beetl.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标识一个类或者方法是线程安全的
 */
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadSafety {
}
