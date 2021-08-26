package com.andyanika.translator.common.models

import java.nio.channels.IllegalSelectorException
import java.util.*

enum class LanguageCode {
    RU, UK, ZH, EN;

    companion object {
        fun tryParse(value: String?, defaultValue: LanguageCode): LanguageCode {
            if (value == null || value.isEmpty()) {
                System.err.print("LanguageCode: failed to parse: $value")
                return defaultValue
            }
            return try {
                valueOf(value.uppercase(Locale.getDefault()))
            } catch (e: IllegalSelectorException) {
                e.printStackTrace()
                defaultValue
            }
        }
    }
}
