package com.andyanika.translator.feature.translate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.andyanika.resources.ResourceImpl
import com.andyanika.translator.common.constants.Extras
import com.andyanika.translator.common.constants.Screens
import com.andyanika.translator.common.interfaces.ScreenRouter
import com.andyanika.translator.common.models.ui.DisplayTranslateResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.parameter.parametersOf

class TranslationFragment : ScopeFragment(), TranslationView {
    private val presenter: TranslationPresenter by inject { parametersOf(this, ResourceImpl(requireActivity())) }
    private val router: ScreenRouter by inject()

    private lateinit var editInput: EditText
    private var txtTranslated: TextView? = null
    private var progress: View? = null
    private var errorLayout: View? = null
    private var clearBtn: View? = null
    private var retryBtn: View? = null
    private var offlineIcon: View? = null
    private var srcLangBtn: Button? = null
    private var dstLangBtn: Button? = null
    private var swapLangBtn: ImageButton? = null

    private val textInputFlow by lazy {
        callbackFlow {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s?.toString().orEmpty())
                }
            }
            editInput.addTextChangedListener(listener)
            awaitClose { editInput.removeTextChangedListener(listener) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translation, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editInput = view.findViewById(R.id.edit_input)
        txtTranslated = view.findViewById(R.id.txt_translated)
        progress = view.findViewById(R.id.search_progress)
        errorLayout = view.findViewById(R.id.error_layout)
        retryBtn = view.findViewById(R.id.btn_retry)
        clearBtn = view.findViewById(R.id.btn_clear)
        srcLangBtn = view.findViewById(R.id.btn_lang_src)
        dstLangBtn = view.findViewById(R.id.btn_lang_dst)
        swapLangBtn = view.findViewById(R.id.btn_lang_swap)
        offlineIcon = view.findViewById(R.id.icon_offline)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            presenter.subscribe(textInputFlow, lifecycleScope)
        }
//        presenter.subscribe(textInputFlow, lifecycleScope)

        srcLangBtn!!.setOnClickListener { router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_SRC) }
        dstLangBtn!!.setOnClickListener { router.navigateTo(Screens.SELECT_LANGUAGE, Extras.MODE_DST) }
        swapLangBtn!!.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                presenter.swapDirection()
            }
        }
        clearBtn!!.setOnClickListener { presenter.clear() }
        retryBtn!!.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                presenter.translate(editInput.text.toString())
            }
        }
    }

    override fun onStop() {
        retryBtn!!.setOnClickListener(null)
        srcLangBtn!!.setOnClickListener(null)
        dstLangBtn!!.setOnClickListener(null)
        super.onStop()
    }

    override fun showNotFound() {
        txtTranslated!!.setText(R.string.translation_not_found)
    }

    override fun showTranslation(response: DisplayTranslateResult) {
        txtTranslated!!.text = response.textTranslated
    }

    override fun showProgress() {
        progress!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress!!.visibility = View.INVISIBLE
    }

    override fun showErrorLayout() {
        errorLayout!!.visibility = View.VISIBLE
    }

    override fun hideErrorLayout() {
        errorLayout!!.visibility = View.INVISIBLE
        errorLayout!!.visibility = View.VISIBLE
    }

    override fun setSrcLabel(text: String?) {
        srcLangBtn!!.text = text
    }

    override fun setDstLabel(text: String?) {
        dstLangBtn!!.text = text
    }

    override fun showOffline() {
        offlineIcon!!.visibility = View.VISIBLE
    }

    override fun hideOffline() {
        offlineIcon!!.visibility = View.INVISIBLE
    }

    override fun showClearBtn() {
        clearBtn!!.visibility = View.VISIBLE
    }

    override fun hideClearBtn() {
        clearBtn!!.visibility = View.INVISIBLE
    }

    override fun clearResult() {
        editInput!!.text.clear()
    }

    override fun clearTranslation() {
        txtTranslated!!.text = ""
    }
}
