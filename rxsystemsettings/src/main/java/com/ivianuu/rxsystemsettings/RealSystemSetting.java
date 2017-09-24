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
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.ivianuu.rxsystemsettings.adapter.Adapter;

import java.util.function.Consumer;

import io.reactivex.Observable;

/**
 * Implementation of an system setting
 */
final class RealSystemSetting<T> implements SystemSetting<T> {

    private final ContentResolver contentResolver;
    private final String name;
    private final T defaultValue;
    private final Adapter<T> adapter;
    private final ContentObserverFactory contentObserverFactory;
    private final int type;

    RealSystemSetting(@NonNull ContentResolver contentResolver,
                      @NonNull String name,
                      @NonNull T defaultValue,
                      @NonNull Adapter<T> adapter,
                      @NonNull ContentObserverFactory contentObserverFactory,
                      @SettingsType int type) {
        this.contentResolver = contentResolver;
        this.name = name;
        this.defaultValue = defaultValue;
        this.adapter = adapter;
        this.contentObserverFactory = contentObserverFactory;
        this.type = type;
    }

    @NonNull
    @Override
    public String name() {
        return name;
    }

    @NonNull
    @Override
    public Uri getUri() {
        switch (type) {
            case SettingsType.GLOBAL:
                return Settings.Global.getUriFor(name);
            case SettingsType.SECURE:
                return Settings.Secure.getUriFor(name);
            case SettingsType.SYSTEM:
                return Settings.System.getUriFor(name);
            default:
                throw new IllegalStateException("unknown type " + type);
        }
    }

    @NonNull
    @Override
    public T get() {
        return adapter.get(name, defaultValue, contentResolver, type);
    }

    @Override
    public void set(@NonNull T value) {
        adapter.set(name, value, contentResolver, type);
    }

    @NonNull
    @Override
    public Observable<T> observe() {
        return contentObserverFactory.observe(getUri())
                .startWith(this) // trigger initial value
                .map(__ -> get());
    }

    @NonNull
    @Override
    public Consumer<T> consume() {
        return this::set;
    }
}
