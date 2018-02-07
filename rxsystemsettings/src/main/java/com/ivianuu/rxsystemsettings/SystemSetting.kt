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

package com.ivianuu.rxsystemsettings

import android.content.ContentResolver
import android.net.Uri
import android.provider.Settings
import android.support.annotation.CheckResult

import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * System setting
 */
interface SystemSetting<T> {

    /**
     * Returns the name of this system setting
     */
    fun name(): String

    /**
     * Returns the [Uri] of this system setting
     */
    fun uri(): Uri

    /**
     * Returns the [SettingsType] of this system setting
     */
    fun type(): SettingsType

    /**
     * Returns the current value of this system setting
     */
    fun get(): T

    /**
     * Sets the current value of this system setting
     */
    fun set(value: T)

    /**
     * Returns whether the device has the setting or not
     * Note that this method will use reflection to check this
     */
    fun exists(): Boolean

    /**
     * Emits the current value on subscribe and on changes
     */
    @CheckResult fun observe(): Observable<T>

    /**
     * Sets the value
     */
    fun consume(): Consumer<T>
}

/**
 * Implementation of an [SystemSetting]
 */
internal class RealSystemSetting<T>(private val contentResolver: ContentResolver,
                                    private val name: String,
                                    private val defaultValue: T,
                                    private val adapter: Adapter<T>,
                                    private val contentObserverFactory: ContentObserverFactory,
                                    private val type: SettingsType
) : SystemSetting<T> {

    override fun name() = name

    override fun uri(): Uri = when (type) {
        SettingsType.GLOBAL -> Settings.Global.getUriFor(name)
        SettingsType.SECURE -> Settings.Secure.getUriFor(name)
        SettingsType.SYSTEM -> Settings.System.getUriFor(name)
    }

    override fun type(): SettingsType = type

    override fun get() = adapter[name, defaultValue, contentResolver, type]

    override fun set(value: T) {
        adapter[name, value, contentResolver] = type
    }

    override fun exists() = SettingsValidator.doesExist(name)

    override fun observe() = contentObserverFactory.observe(uri())
        .map { get() }
        .startWith(get()) // trigger initial value

    override fun consume() = Consumer<T> { this.set(it) }
}
