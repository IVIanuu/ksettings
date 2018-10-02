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

package com.ivianuu.ksystemsettings

import android.net.Uri

/**
 * System setting
 */
interface SystemSetting<T> {

    /**
     * The current value of this system setting
     */
    var value: T

    /**
     * The name of this system setting
     */
    val name: String

    /**
     * The uri of this system setting
     */
    val uri: Uri

    /**
     * Whether or not the system setting exists
     */
    val exists: Boolean

    /**
     * The type of this system setting
     */
    val type: SettingsType

    /**
     * Notifies the [listener] on changes of this setting
     */
    fun addListener(listener: ChangeListener<T>)

    /**
     * Removes the previously added [listener]
     */
    fun removeListener(listener: ChangeListener<T>)
}