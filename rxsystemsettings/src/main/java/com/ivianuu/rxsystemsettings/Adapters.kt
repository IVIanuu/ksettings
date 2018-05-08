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
import android.provider.Settings

/**
 * Reads and writes [T] from system settings
 */
internal interface Adapter<T> {

    operator fun get(
        name: String,
        defaultValue: T,
        contentResolver: ContentResolver,
        type: SettingsType
    ): T

    operator fun set(
        name: String,
        value: T,
        contentResolver: ContentResolver,
        type: SettingsType
    )
}

/**
 * Reads and writes [Float] values
 */
object FloatAdapter : Adapter<Float> {

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

/**
 * Reads and writes [Int] values
 */
internal object IntegerAdapter : Adapter<Int> {

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

/**
 * Reads and writes [Long] values
 */
object LongAdapter : Adapter<Long> {

    override fun get(
        name: String,
        defaultValue: Long,
        contentResolver: ContentResolver,
        type: SettingsType
    ): Long = when (type) {
        SettingsType.GLOBAL -> Settings.Global.getLong(
            contentResolver, name,
            defaultValue
        )
        SettingsType.SECURE -> Settings.Secure.getLong(
            contentResolver, name,
            defaultValue
        )
        SettingsType.SYSTEM -> Settings.System.getLong(
            contentResolver, name,
            defaultValue
        )
    }

    override fun set(
        name: String,
        value: Long,
        contentResolver: ContentResolver,
        type: SettingsType
    ) {
        when (type) {
            SettingsType.GLOBAL -> Settings.Global.putLong(contentResolver, name, value)
            SettingsType.SECURE -> Settings.Secure.putLong(contentResolver, name, value)
            SettingsType.SYSTEM -> Settings.System.putLong(contentResolver, name, value)
        }
    }
}

/**
 * Reads and writes [String] values
 */
object StringAdapter : Adapter<String> {

    override fun get(
        name: String,
        defaultValue: String,
        contentResolver: ContentResolver,
        type: SettingsType
    ): String {
        val value: String? = when (type) {
            SettingsType.GLOBAL -> Settings.Global.getString(contentResolver, name)
            SettingsType.SECURE -> Settings.Secure.getString(contentResolver, name)
            SettingsType.SYSTEM -> Settings.System.getString(contentResolver, name)
        }

        return value ?: defaultValue
    }

    override fun set(
        name: String,
        value: String,
        contentResolver: ContentResolver,
        type: SettingsType
    ) {
        when (type) {
            SettingsType.GLOBAL -> Settings.Global.putString(contentResolver, name, value)
            SettingsType.SECURE -> Settings.Secure.putString(contentResolver, name, value)
            SettingsType.SYSTEM -> Settings.System.putString(contentResolver, name, value)
        }
    }
}