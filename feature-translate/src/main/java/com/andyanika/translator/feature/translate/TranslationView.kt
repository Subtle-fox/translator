package com.andyanika.translator.feature.translate

import com.andyanika.translator.common.models.ui.DisplayTranslateResult

interface TranslationView {
    fun showTranslation(result: DisplayTranslateResult)
    fun showNotFound()
    fun showProgress()
    fun hideProgress()
    fun showErrorLayout()
    fun hideErrorLayout()
    fun showClearBtn()
    fun hideClearBtn()
    fun clearResult()
    fun clearTranslation()
    fun setSrcLabel(text: String?)
    fun setDstLabel(text: String?)
    fun showOffline()
    fun hideOffline()
}
