package com.andyanika.translator.feature.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.andyanika.translator.feature.translate.databinding.FragmentTranslationBinding
import com.andyanika.translator.feature.translate.mvi.TranslationAction
import com.andyanika.translator.feature.translate.mvi.TranslationFeature
import com.andyanika.translator.feature.translate.mvi.TranslationViewState
import com.andyanika.widgets.observe
import core.constants.Extras
import core.constants.Screens
import core.interfaces.ScreenRouter
import core.models.LanguageCode
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import java.util.*

class TranslationFragment : ScopeFragment() {
    // NOTE: as a variant: manually link feature's scope to fragment's scope
    private val featureScope = getKoin()
        .getOrCreateScope<TranslationFeature>("")
        .apply { scope.linkTo(this) }

    private val feature by inject<TranslationFeature>()
    private val router: ScreenRouter by inject()

    private val binding by lazy { FragmentTranslationBinding.bind(requireView()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translation, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            feature
                .observe()
                .collect(::render)
        }

        lifecycleScope.launchWhenResumed {
            binding
                .editInput
                .observe()
                .collect {
                    feature.accept(TranslationAction.Translate(it))
                }
        }

        with(binding) {

            btnLangSrc.setOnClickListener {
                router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_SRC)
            }

            btnLangDst.setOnClickListener {
                router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_DST)
            }

            btnLangSwap.setOnClickListener {
                lifecycleScope.launch {
                    feature.accept(TranslationAction.SwapDirection)
                }
            }

            btnClear.setOnClickListener {
                lifecycleScope.launch {
                    feature.accept(TranslationAction.Clear)
                }
            }

            btnRetry.setOnClickListener {
                lifecycleScope.launch {
                    feature.accept(TranslationAction.Retry)
                }
            }
        }
    }

    override fun onStop() = with(binding) {
        // NOTE: as a variant: manually clear feature if not bound to fragment's lifecycle
        featureScope.close()

        super.onStop()
    }

    private fun showNotFound() {
        binding.txtTranslated.setText(R.string.translation_not_found)
    }

    private fun showTranslation(textTranslated: String) {
        binding.txtTranslated.text = textTranslated
    }

    private fun showProgress(visible: Boolean) {
        binding.searchProgress.isVisible = visible
    }

    private fun showError(visible: Boolean) {
        binding.errorLayout.isVisible = visible
    }

    private fun setSrcLabel(text: String?) {
        binding.btnLangSrc.text = text
    }

    private fun setDstLabel(text: String?) {
        binding.btnLangDst.text = text
    }

    private fun showOffline(visible: Boolean) {
        binding.iconOffline.isVisible = visible
    }

    private fun showClearBtn(visible: Boolean) {
        binding.btnClear.isVisible = visible
    }

    private fun render(state: TranslationViewState) = with(binding) {
        println("### $state")

        showProgress(state.isLoading)
        showError(state.isError)
        showOffline(state.isOffline)
        showClearBtn(state.canClear)
        if (state.isNotFound) showNotFound()

        setSrcLabel(getLanguageLabel(state.srcCode))
        setDstLabel(getLanguageLabel(state.dstCode))
        showTranslation(state.output)

        if (editInput.text.toString() != state.input) {
            editInput.text.replace(0, editInput.text.length, state.input)
        }
    }

    private fun getLanguageLabel(code: LanguageCode): String {
        val resourceName = "lang_" + code.toString().lowercase(Locale.getDefault())
        val id = resources.getIdentifier(resourceName, "string", requireActivity().packageName)
        return getString(id)
    }

    private var View.isVisible: Boolean
        get() {
            return visibility == View.VISIBLE
        }
        set(value) {
            visibility = if (value) View.VISIBLE else View.INVISIBLE
        }
}
