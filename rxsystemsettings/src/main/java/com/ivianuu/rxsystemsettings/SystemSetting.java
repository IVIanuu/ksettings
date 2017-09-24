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
    @NonNull Uri getUri();

    /**
     * Returns the current value of this system setting
     */
    @NonNull T get();

    /**
     * Sets the current value of this system setting
     */
    @RequiresPermission(anyOf = {Manifest.permission.WRITE_SETTINGS, Manifest.permission.WRITE_SECURE_SETTINGS}, conditional = true)
    void set(@NonNull T value);

    /**
     * Observes the changes of this system setting and emits the newest value
     */
    @CheckResult @NonNull Observable<T> observe();

    /**
     * Sets the value
     */
    @NonNull Consumer<T> consume();
}
