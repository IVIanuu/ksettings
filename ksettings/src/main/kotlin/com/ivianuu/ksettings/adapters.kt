/*
 * Copyright 2018 Manuel Wrage
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

import android.content.ContentResolver
import android.provider.Settings

internal object FloatAdapter : RealSetting.Adapter<Float> {

    override fun get(
        name: String,
        defaultValue: Float,
        contentResolver: ContentResolver,
        type: Setting.Type
    ): Float =
        when (type) {
            Setting.Type.Global -> Settings.Global.getFloat(
                contentResolver, name,
                defaultValue
            )
            Setting.Type.Secure -> Settings.Secure.getFloat(
                contentResolver, name,
                defaultValue
            )
            Setting.Type.System -> Settings.System.getFloat(
                contentResolver, name,
                defaultValue
            )
        }

    override fun set(
        name: String,
        value: Float,
        contentResolver: ContentResolver,
        type: Setting.Type
    ) {
        when (type) {
            Setting.Type.Global -> Settings.Global.putFloat(contentResolver, name, value)
            Setting.Type.Secure -> Settings.Secure.putFloat(contentResolver, name, value)
            Setting.Type.System -> Settings.System.putFloat(contentResolver, name, value)
        }
    }
}

internal object IntAdapter : RealSetting.Adapter<Int> {

    override fun get(
        name: String,
        defaultValue: Int,
        contentResolver: ContentResolver,
        type: Setting.Type
    ): Int =
        when (type) {
            Setting.Type.Global -> Settings.Global.getInt(
                contentResolver, name,
                defaultValue
            )
            Setting.Type.Secure -> Settings.Secure.getInt(
                contentResolver, name,
                defaultValue
            )
            Setting.Type.System -> Settings.System.getInt(
                contentResolver, name,
                defaultValue
            )
        }

    override fun set(
        name: String,
        value: Int,
        contentResolver: ContentResolver,
        type: Setting.Type
    ) {
        when (type) {
            Setting.Type.Global -> Settings.Global.putInt(contentResolver, name, value)
            Setting.Type.Secure -> Settings.Secure.putInt(contentResolver, name, value)
            Setting.Type.System -> Settings.System.putInt(contentResolver, name, value)
        }
    }
}

internal object LongAdapter : RealSetting.Adapter<Long> {

    override fun get(
        name: String,
        defaultValue: Long,
        contentResolver: ContentResolver,
        type: Setting.Type
    ): Long = when (type) {
        Setting.Type.Global -> Settings.Global.getLong(
            contentResolver, name,
            defaultValue
        )
        Setting.Type.Secure -> Settings.Secure.getLong(
            contentResolver, name,
            defaultValue
        )
        Setting.Type.System -> Settings.System.getLong(
            contentResolver, name,
            defaultValue
        )
    }

    override fun set(
        name: String,
        value: Long,
        contentResolver: ContentResolver,
        type: Setting.Type
    ) {
        when (type) {
            Setting.Type.Global -> Settings.Global.putLong(contentResolver, name, value)
            Setting.Type.Secure -> Settings.Secure.putLong(contentResolver, name, value)
            Setting.Type.System -> Settings.System.putLong(contentResolver, name, value)
        }
    }
}

internal object StringAdapter : RealSetting.Adapter<String> {

    override fun get(
        name: String,
        defaultValue: String,
        contentResolver: ContentResolver,
        type: Setting.Type
    ): String {
        return when (type) {
            Setting.Type.Global -> Settings.Global.getString(contentResolver, name)
            Setting.Type.Secure -> Settings.Secure.getString(contentResolver, name)
            Setting.Type.System -> Settings.System.getString(contentResolver, name)
        } ?: defaultValue
    }

    override fun set(
        name: String,
        value: String,
        contentResolver: ContentResolver,
        type: Setting.Type
    ) {
        when (type) {
            Setting.Type.Global -> Settings.Global.putString(contentResolver, name, value)
            Setting.Type.Secure -> Settings.Secure.putString(contentResolver, name, value)
            Setting.Type.System -> Settings.System.putString(contentResolver, name, value)
        }
    }
}