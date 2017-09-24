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

package com.ivianuu.rxsystemsettings.adapter;

import android.content.ContentResolver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxsystemsettings.SettingsType;

/**
 * Reads and writes long values
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class LongAdapter implements Adapter<Long> {

    public static final Adapter<Long> INSTANCE = new LongAdapter();

    @NonNull
    @Override
    public Long get(@NonNull String name,
                    @NonNull Long defaultValue,
                    @NonNull ContentResolver contentResolver,
                    @SettingsType int type) {
        switch (type) {
            case SettingsType.GLOBAL:
                return Settings.Global.getLong(contentResolver, name, defaultValue);
            case SettingsType.SECURE:
                return Settings.Secure.getLong(contentResolver, name, defaultValue);
            case SettingsType.SYSTEM:
                return Settings.System.getLong(contentResolver, name, defaultValue);
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }

    @Override
    public void set(@NonNull String name,
                    @NonNull Long value,
                    @NonNull ContentResolver contentResolver,
                    @SettingsType int type) {
        switch (type) {
            case SettingsType.GLOBAL:
                Settings.Global.putLong(contentResolver, name, value);
                break;
            case SettingsType.SECURE:
                Settings.Secure.putLong(contentResolver, name, value);
                break;
            case SettingsType.SYSTEM:
                Settings.System.putLong(contentResolver, name, value);
                break;
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }
}
