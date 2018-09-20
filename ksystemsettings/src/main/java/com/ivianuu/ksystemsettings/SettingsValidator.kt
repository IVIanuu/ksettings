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

import android.provider.Settings
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

/**
 * Checks if a specific setting exists
 */
internal object SettingsValidator {

    private val CACHED = HashMap<String, Boolean>()

    fun doesExist(name: String): Boolean {
        return CACHED.getOrPut(name) {
            var exists: Boolean

            // global
            exists = doesGlobalSettingExists(name)

            // secure
            if (!exists) {
                exists = doesSecureSettingExists(name)
            }

            // system
            if (!exists) {
                exists = doesSystemSettingExists(name)
            }

            exists
        }
    }

    private fun doesGlobalSettingExists(name: String): Boolean {
        return checkFieldValues(Settings.Global::class.java.declaredFields, name)
    }

    private fun doesSecureSettingExists(name: String): Boolean {
        return checkFieldValues(Settings.Secure::class.java.declaredFields, name)
    }

    private fun doesSystemSettingExists(name: String): Boolean {
        return checkFieldValues(Settings.System::class.java.declaredFields, name)
    }

    private fun checkFieldValues(fields: Array<Field>, value: String): Boolean {
        for (field in fields) {
            // ignore private fields
            if (field.modifiers and Modifier.PRIVATE == Modifier.PRIVATE) continue

            // ignore non string values
            if (!field.type.isAssignableFrom(String::class.java)) continue

            try {
                val fieldValue = field.get(null) as String
                if (fieldValue == value) {
                    // the field does exist
                    return true
                }
            } catch (ignore: Exception) {
                // catch errors
            }

        }

        return false
    }
}
