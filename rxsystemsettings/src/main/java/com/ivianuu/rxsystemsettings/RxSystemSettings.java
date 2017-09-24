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
import com.ivianuu.rxsystemsettings.adapter.IntAdapter;
import com.ivianuu.rxsystemsettings.adapter.LongAdapter;
import com.ivianuu.rxsystemsettings.adapter.StringAdapter;

/**
 * Rx system settings
 */
public final class RxSystemSettings {

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
        return new RxSystemSettings(
                context.getContentResolver(), new ContentObserverFactory(context));
    }

    /**
     * Returns a new float system setting
     */
    @NonNull
    public SystemSetting<Float> getFloat(@NonNull String name, @SettingsType int type) {
        return new RealSystemSetting<>(
                contentResolver, name, FloatAdapter.INSTANCE, contentObserverFactory, type);
    }

    /**
     * Returns a new int system setting
     */
    @NonNull
    public SystemSetting<Integer> getInt(@NonNull String name, @SettingsType int type) {
        return new RealSystemSetting<>(
                contentResolver, name, IntAdapter.INSTANCE, contentObserverFactory, type);
    }

    /**
     * Returns a new long system setting
     */
    @NonNull
    public SystemSetting<Long> getLong(@NonNull String name, @SettingsType int type) {
        return new RealSystemSetting<>(
                contentResolver, name, LongAdapter.INSTANCE, contentObserverFactory, type);
    }

    /**
     * Returns a new string system setting
     */
    @NonNull
    public SystemSetting<String> getString(@NonNull String name, @SettingsType int type) {
        return new RealSystemSetting<>(
                contentResolver, name, StringAdapter.INSTANCE, contentObserverFactory, type);
    }
}
