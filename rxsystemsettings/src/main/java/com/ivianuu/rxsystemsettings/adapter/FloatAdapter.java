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
 * Reads and writes float values
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class FloatAdapter implements Adapter<Float> {

    public final static Adapter<Float> INSTANCE = new FloatAdapter();

    @NonNull
    @Override
    public Float get(@NonNull String name,
                       @NonNull ContentResolver contentResolver,
                       @SettingsType int type) throws Settings.SettingNotFoundException {
        switch (type) {
            case SettingsType.GLOBAL:
                return Settings.Global.getFloat(contentResolver, name);
            case SettingsType.SECURE:
                return Settings.Secure.getFloat(contentResolver, name);
            case SettingsType.SYSTEM:
                return Settings.System.getFloat(contentResolver, name);
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }

    @Override
    public void set(@NonNull String name,
                    @NonNull Float value,
                    @NonNull ContentResolver contentResolver,
                    @SettingsType int type) {
        switch (type) {
            case SettingsType.GLOBAL:
                Settings.Global.putFloat(contentResolver, name, value);
                break;
            case SettingsType.SECURE:
                Settings.Secure.putFloat(contentResolver, name, value);
                break;
            case SettingsType.SYSTEM:
                Settings.System.putFloat(contentResolver, name, value);
                break;
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }
}
