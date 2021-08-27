package com.andyanika.translator.feature.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.constants.Extras
import core.constants.Screens
import core.interfaces.ScreenRouter
import core.interfaces.usecase.GetLanguagesUseCase
import core.interfaces.usecase.SelectLanguageUseCase
import core.models.ui.DisplayLanguageModel
import kotlinx.coroutines.launch

internal class SelectLanguageViewModel(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val selectLanguageUseCase: SelectLanguageUseCase,
    private val router: ScreenRouter
) : ViewModel() {

    private val _data: MutableLiveData<List<DisplayLanguageModel>> = MutableLiveData<List<DisplayLanguageModel>>()
    val data: LiveData<List<DisplayLanguageModel>> = _data

    private var isSrcMode = false

    fun setMode(mode: String) {
        isSrcMode = Extras.MODE_SRC == mode
    }

    fun loadData() {
        viewModelScope.launch {
            _data.value = getLanguagesUseCase.run(isSrcMode)
        }
    }

//    fun subscribeItemClick(single: Single<DisplayLanguageModel?>) {
//        itemClickDisposable = single
//            .flatMapCompletable { model ->
//                Timber.d("select language -> click received (isSrcMode = %b)", isSrcMode)
//                if (isSrcMode) {
//                    return@flatMapCompletable selectLanguageUseCase.setSrc(model.getCode())
//                } else {
//                    return@flatMapCompletable selectLanguageUseCase.setDst(model.getCode())
//                }
//            }
//            .observeOn(uiScheduler)
//            .doOnComplete { Timber.d("navigate back") }
//            .subscribe { router.backTo(Screens.TRANSLATION) }
//    }

    fun onItemClick(model: DisplayLanguageModel) {
        viewModelScope.launch {
            if (isSrcMode) {
                selectLanguageUseCase.setSrc(model.code)
            } else {
                selectLanguageUseCase.setDst(model.code)
            }
        }
        router.backTo(Screens.TRANSLATION)
    }
}
