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

package com.ivianuu.rxsystemsettings.adapter

import android.content.ContentResolver
import android.provider.Settings
import com.ivianuu.rxsystemsettings.SettingsType

/**
 * Reads and writes [Int] values
 */
internal object IntAdapter : Adapter<Int> {

    override fun get(
        name: String,
        defaultValue: Int,
        contentResolver: ContentResolver,
        type: SettingsType
    ): Int =
        when (type) {
            SettingsType.GLOBAL -> Settings.Global.getInt(
                contentResolver, name,
                defaultValue
            )
            SettingsType.SECURE -> Settings.Secure.getInt(
                contentResolver, name,
                defaultValue
            )
            SettingsType.SYSTEM -> Settings.System.getInt(
                contentResolver, name,
                defaultValue
            )
        }

    override fun set(
        name: String,
        value: Int,
        contentResolver: ContentResolver,
        type: SettingsType
    ) {
        when (type) {
            SettingsType.GLOBAL -> Settings.Global.putInt(contentResolver, name, value)
            SettingsType.SECURE -> Settings.Secure.putInt(contentResolver, name, value)
            SettingsType.SYSTEM -> Settings.System.putInt(contentResolver, name, value)
        }
    }
}