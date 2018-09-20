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

package com.ivianuu.ksystemsettings.rx

import com.ivianuu.ksystemsettings.SystemSetting
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

/**
 * Returns a [Observable] which emits on changes of [this]
 */
val <T> SystemSetting<T>.observable: Observable<T>
    get() = io.reactivex.Observable.create(PreferenceObservableOnSubscribe(this))

private class PreferenceObservableOnSubscribe<T>(private val systemSetting: SystemSetting<T>) :
    ObservableOnSubscribe<T> {
    override fun subscribe(emitter: ObservableEmitter<T>) {
        val listener: (T) -> Unit = {
            if (!emitter.isDisposed) {
                emitter.onNext(it)
            }
        }

        emitter.setCancellable { systemSetting.removeListener(listener) }

        if (!emitter.isDisposed) {
            systemSetting.addListener(listener)
        }
    }
}