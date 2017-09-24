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
 * Reads and writes integer values
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class IntegerAdapter implements Adapter<Integer> {

    public static final Adapter<Integer> INSTANCE = new IntegerAdapter();

    @NonNull
    @Override
    public Integer get(@NonNull String name,
                       @NonNull Integer defaultValue,
                       @NonNull ContentResolver contentResolver,
                       @SettingsType int type) {
        switch (type) {
            case SettingsType.GLOBAL:
                return Settings.Global.getInt(contentResolver, name, defaultValue);
            case SettingsType.SECURE:
                return Settings.Secure.getInt(contentResolver, name, defaultValue);
            case SettingsType.SYSTEM:
                return Settings.System.getInt(contentResolver, name, defaultValue);
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }

    @Override
    public void set(@NonNull String name,
                    @NonNull Integer value,
                    @NonNull ContentResolver contentResolver,
                    @SettingsType int type) {
        switch (type) {
            case SettingsType.GLOBAL:
                Settings.Global.putInt(contentResolver, name, value);
                break;
            case SettingsType.SECURE:
                Settings.Secure.putInt(contentResolver, name, value);
                break;
            case SettingsType.SYSTEM:
                Settings.System.putInt(contentResolver, name, value);
                break;
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }
}
