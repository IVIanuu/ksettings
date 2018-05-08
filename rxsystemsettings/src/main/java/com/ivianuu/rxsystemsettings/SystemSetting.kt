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

import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * System setting
 */
interface SystemSetting<T> {

    val name: String

    val uri: Uri

    val type: SettingsType

    fun get(): T

    fun set(value: T)

    fun exists(): Boolean

    fun observe(): Observable<T>

    fun consume(): Consumer<T>

}

/**
 * Implementation of an [SystemSetting]
 */
internal class RealSystemSetting<T>(
    private val contentResolver: ContentResolver,
    override val name: String,
    private val defaultValue: T,
    private val adapter: Adapter<T>,
    private val contentObserverFactory: ContentObserverFactory,
    override val type: SettingsType
) : SystemSetting<T> {

    override val uri: Uri
        get() = when (type) {
            SettingsType.GLOBAL -> Settings.Global.getUriFor(name)
            SettingsType.SECURE -> Settings.Secure.getUriFor(name)
            SettingsType.SYSTEM -> Settings.System.getUriFor(name)
        }

    override fun get() = adapter[name, defaultValue, contentResolver, type]

    override fun set(value: T) {
        adapter[name, value, contentResolver] = type
    }

    override fun exists() = SettingsValidator.doesExist(name)

    override fun observe(): Observable<T> = contentObserverFactory.observe(uri)
        .map { get() }
        .startWith(get()) // trigger initial value

    override fun consume() = Consumer<T> { this.set(it) }
}
