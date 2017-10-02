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

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ivianuu.rxsystemsettings.adapter.FloatAdapter;
import com.ivianuu.rxsystemsettings.adapter.IntegerAdapter;
import com.ivianuu.rxsystemsettings.adapter.LongAdapter;
import com.ivianuu.rxsystemsettings.adapter.StringAdapter;

import static com.ivianuu.rxsystemsettings.Preconditions.checkNotNull;

/**
 * Rx system settings
 */
public final class RxSystemSettings {
    private static final Float DEFAULT_FLOAT = 0f;
    private static final Integer DEFAULT_INTEGER = 0;
    private static final Long DEFAULT_LONG = 0L;
    private static final String DEFAULT_STRING = "";

    private final ContentResolver contentResolver;
    private final ContentObserverFactory contentObserverFactory;

    private RxSystemSettings(@NonNull ContentResolver contentResolver,
                             @NonNull ContentObserverFactory contentObserverFactory) {
        this.contentResolver = contentResolver;
        this.contentObserverFactory = contentObserverFactory;
    }

    /**
     * Returns a new rx system settings instance
     */
    @NonNull
    public static RxSystemSettings create(@NonNull Context context) {
        checkNotNull(context, "context == null");
        return new RxSystemSettings(
                context.getContentResolver(), new ContentObserverFactory(context));
    }

    /**
     * Returns a new float system setting
     */
    @NonNull
    public SystemSetting<Float> getFloat(@NonNull String name, @SettingsType int type) {
        return getFloat(name, DEFAULT_FLOAT, type);
    }

    /**
     * Returns a new float system setting
     */
    @NonNull
    public SystemSetting<Float> getFloat(@NonNull String name, @NonNull Float defaultValue,
                                         @SettingsType int type) {
        checkNotNull(name, "name == null");
        checkNotNull(defaultValue, "defaultValue == null");
        return new RealSystemSetting<>(
                contentResolver, name, defaultValue, FloatAdapter.INSTANCE, contentObserverFactory, type);
    }

    /**
     * Returns a new integer system setting
     */
    @NonNull
    public SystemSetting<Integer> getInteger(@NonNull String name, @SettingsType int type) {
        return getInteger(name, DEFAULT_INTEGER, type);
    }

    /**
     * Returns a new integer system setting
     */
    @NonNull
    public SystemSetting<Integer> getInteger(@NonNull String name, @NonNull Integer defaultValue,
                                             @SettingsType int type) {
        checkNotNull(name, "name == null");
        checkNotNull(defaultValue, "defaultValue == null");
        return new RealSystemSetting<>(
                contentResolver, name, defaultValue, IntegerAdapter.INSTANCE, contentObserverFactory, type);
    }

    /**
     * Returns a new long system setting
     */
    @NonNull
    public SystemSetting<Long> getLong(@NonNull String name, @SettingsType int type) {
        return getLong(name, DEFAULT_LONG, type);
    }

    /**
     * Returns a new long system setting
     */
    @NonNull
    public SystemSetting<Long> getLong(@NonNull String name, @NonNull Long defaultValue,
                                       @SettingsType int type) {
        checkNotNull(name, "name == null");
        checkNotNull(defaultValue, "defaultValue == null");
        return new RealSystemSetting<>(
                contentResolver, name, defaultValue, LongAdapter.INSTANCE, contentObserverFactory, type);
    }

    /**
     * Returns a new string system setting
     */
    @NonNull
    public SystemSetting<String> getString(@NonNull String name, @SettingsType int type) {
        return getString(name, DEFAULT_STRING, type);
    }

    /**
     * Returns a new string system setting
     */
    @NonNull
    public SystemSetting<String> getString(@NonNull String name, @NonNull String defaultValue,
                                           @SettingsType int type) {
        checkNotNull(name, "name == null");
        checkNotNull(defaultValue, "defaultValue == null");
        return new RealSystemSetting<>(
                contentResolver, name, defaultValue, StringAdapter.INSTANCE, contentObserverFactory, type);
    }
}
