package com.andyanika.translator.feature.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.andyanika.translator.feature.translate.databinding.FragmentTranslationBinding
import com.andyanika.widgets.observe
import core.constants.Extras
import core.constants.Screens
import core.interfaces.ScreenRouter
import core.models.ui.DisplayTranslateResult
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.parameter.parametersOf

class TranslationFragment : ScopeFragment(), TranslationView {
    private val presenter by inject<TranslationPresenter> { parametersOf(this) }
    private val router: ScreenRouter by inject()

    private val binding by lazy { FragmentTranslationBinding.bind(requireView()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translation, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            presenter.subscribe(binding.editInput.observe())
        }

        lifecycleScope.launchWhenCreated {
            presenter.subscribeLanguageDirection()
        }
    }

    override fun onStart() = with(binding) {
        super.onStart()

        btnLangSrc.setOnClickListener {
            router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_SRC)
        }

        btnLangDst.setOnClickListener {
            router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_DST)
        }

        btnLangSwap.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                presenter.swapDirection()
            }
        }

        btnClear.setOnClickListener {
            presenter.clear()
        }

        btnRetry.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                presenter.translate(editInput.text.toString())
            }
        }
    }

    override fun onStop() = with(binding) {
        btnRetry.setOnClickListener(null)
        btnLangSrc.setOnClickListener(null)
        btnLangDst.setOnClickListener(null)
        super.onStop()
    }

    override fun showNotFound() {
        binding.txtTranslated.setText(R.string.translation_not_found)
    }

    override fun showTranslation(result: DisplayTranslateResult) {
        binding.txtTranslated.text = result.textTranslated
    }

    override fun showProgress() {
        binding.searchProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.searchProgress.visibility = View.INVISIBLE
    }

    override fun showErrorLayout() {
        binding.errorLayout.visibility = View.VISIBLE
    }

    override fun hideErrorLayout() {
        binding.errorLayout.visibility = View.INVISIBLE
    }

    override fun setSrcLabel(text: String?) {
        binding.btnLangSrc.text = text
    }

    override fun setDstLabel(text: String?) {
        binding.btnLangDst.text = text
    }

    override fun showOffline() {
        binding.iconOffline.visibility = View.VISIBLE
    }

    override fun hideOffline() {
        binding.iconOffline.visibility = View.INVISIBLE
    }

    override fun showClearBtn() {
        binding.btnClear.visibility = View.VISIBLE
    }

    override fun hideClearBtn() {
        binding.btnClear.visibility = View.INVISIBLE
    }

    override fun clearResult() {
        binding.editInput.text.clear()
    }

    override fun clearTranslation() {
        binding.editInput.text.clear()
    }
}
