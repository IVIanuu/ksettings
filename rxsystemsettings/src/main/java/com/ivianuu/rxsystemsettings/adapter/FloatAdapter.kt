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
 * Reads and writes [Float] values
 */
internal object FloatAdapter : Adapter<Float> {

    override fun get(
        name: String,
        defaultValue: Float,
        contentResolver: ContentResolver,
        type: SettingsType
    ): Float =
        when (type) {
            SettingsType.GLOBAL -> Settings.Global.getFloat(
                contentResolver, name,
                defaultValue
            )
            SettingsType.SECURE -> Settings.Secure.getFloat(
                contentResolver, name,
                defaultValue
            )
            SettingsType.SYSTEM -> Settings.System.getFloat(
                contentResolver, name,
                defaultValue
            )
        }

    override fun set(
        name: String,
        value: Float,
        contentResolver: ContentResolver,
        type: SettingsType
    ) {
        when (type) {
            SettingsType.GLOBAL -> Settings.Global.putFloat(contentResolver, name, value)
            SettingsType.SECURE -> Settings.Secure.putFloat(contentResolver, name, value)
            SettingsType.SYSTEM -> Settings.System.putFloat(contentResolver, name, value)
        }
    }
}