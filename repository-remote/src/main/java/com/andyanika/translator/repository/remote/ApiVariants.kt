package com.andyanika.translator.repository.remote

internal sealed class ApiVariants {
    object Yandex : ApiVariants()
    object Stub : ApiVariants()
}
