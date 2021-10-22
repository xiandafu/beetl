/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.beetl.core.util;

import org.beetl.core.runtime.BeetlRuntime;

/**
 * Class to configure several of the util classes.
 *
 * @hide
 */
public class UtilConfig {
    /**
     * 表示数组越界时，是否抛出异常(默认应该为 true)，
     * 创建出来的数组的长度可能会大于size，例如 {@link ArrayUtils#newUnpaddedIntArray(int)}
     * 因此实际使用中可能并不会导致越界异常，但是 Debug 模式建议还是抛出异常
     */
    static boolean sThrowExceptionForUpperArrayOutOfBounds = BeetlRuntime.getConfigManager().isDebug();

    /**
     * 为 {@code android.util.*} 这些类设置是否在 {@code index > *.size} 时抛出越界异常
     *
     * @param check 是否抛出异常
     */
    public static void setThrowExceptionForUpperArrayOutOfBounds(boolean check) {
        sThrowExceptionForUpperArrayOutOfBounds = check;
    }
}
