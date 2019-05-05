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

package com.ivianuu.ksettings

import android.net.Uri

/**
 * Setting
 */
interface Setting<T> {

    /**
     * The name of this setting
     */
    val name: String

    /**
     * The uri of this setting
     */
    val uri: Uri

    /**
     * Whether or not the setting exists
     */
    val exists: Boolean

    /**
     * The type of this setting
     */
    val type: Type

    /**
     * Returns the current value of this [Setting]
     */
    fun get(): T

    /**
     * Sets the value of this setting to [value]
     */
    fun set(value: T)

    /**
     * Notifies the [listener] on changes of this setting
     */
    fun addListener(listener: ChangeListener<T>)

    /**
     * Removes the previously added [listener]
     */
    fun removeListener(listener: ChangeListener<T>)

    /**
     * Types of settings
     */
    enum class Type {
        GLOBAL, SECURE, SYSTEM
    }
}