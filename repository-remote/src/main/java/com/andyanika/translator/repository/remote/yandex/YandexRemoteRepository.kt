package com.andyanika.translator.repository.remote.yandex

import core.interfaces.RemoteRepository
import core.models.TranslateRequest
import core.models.TranslateResult
import com.andyanika.translator.repository.remote.BuildConfig

internal class YandexRemoteRepository constructor(
    private val api: YandexApi,
    private val directionBuilder: YandexTranslationParamsBuilder,
    private val modelsAdapter: YandexModelsAdapter
) : RemoteRepository {

    private val key: String = BuildConfig.ApiKey

    init {
        if (key.isEmpty()) {
            System.err.println("Yandex api key not defined in gradle.properties")
        }
    }

    override suspend fun translate(request: TranslateRequest): TranslateResult? {
        val direction = directionBuilder.buildParam(request.direction)
        return api.translate(key, request.text, direction)
            .let { response -> modelsAdapter.convert(request, response) }
            .takeIf { modelsAdapter.isTranslationFound(it) }
    }
}
