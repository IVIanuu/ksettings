/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.rxsystemsettings;

import android.Manifest;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * System setting
 */
public interface SystemSetting<T> {

    /**
     * Returns the name of this system setting
     */
    @NonNull String name();

    /**
     * Returns the uri of this system setting
     */
    @NonNull Uri uri();

    /**
     * Returns the current value of this system setting
     */
    @NonNull T get();

    /**
     * Sets the current value of this system setting
     */
    void set(@NonNull T value);

    /**
     * Returns whether the device has the setting or not
     */
    boolean exists();

    /**
     * Emits the current value on subscribe and on changes
     */
    @CheckResult @NonNull Observable<T> observe();

    /**
     * Sets the value
     */
    @NonNull Consumer<T> consume();
}
