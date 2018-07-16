/*
 * Copyright 2018 https://www.valiktor.org
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

package org.valiktor.i18n

/**
 * Interpolate the message with parameters
 *
 * @param messageBundle specifies the [MessageBundle] that contains the messages
 * @param messageKey specifies the message key in the message properties
 * @param messageParams specifies the parameters to replace in the message
 * @return the interpolated message
 */
internal fun interpolate(messageBundle: MessageBundle, messageKey: String, messageParams: Map<String, *>): String =
        messageParams.toList()
                .stream()
                .reduce(messageBundle.getMessage(messageKey),
                        { message, pair ->
                            message.replace("{${pair.first}}",
                                    pair.second?.let { Formatters[it.javaClass.kotlin].format(it, messageBundle) }
                                            ?: "")
                        },
                        { message, _ -> message })