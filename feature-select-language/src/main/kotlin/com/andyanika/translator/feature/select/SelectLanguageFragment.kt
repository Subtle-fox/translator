package com.andyanika.translator.feature.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyanika.translator.feature.select.databinding.FragmentSelectLanguageBinding
import core.constants.Extras
import core.models.ui.DisplayLanguageModel
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectLanguageFragment : ScopeFragment() {
    private val vm by viewModel<SelectLanguageViewModel>()
    private val action = { model: DisplayLanguageModel -> vm.onItemClick(model) }
    private val adapter: SelectLanguageListAdapter by inject { parametersOf(action) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_language, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.title_select_language)

        with(FragmentSelectLanguageBinding.bind(view)) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }

        vm.data.observe(viewLifecycleOwner, adapter::setData)
        if (savedInstanceState == null) {
            arguments?.getString(Extras.SELECT_MODE)?.let {
                vm.setMode(it)
                vm.loadData()
            }
        }
    }

    companion object {
        fun create(mode: String): SelectLanguageFragment {
            return SelectLanguageFragment().apply {
                val extra = Bundle()
                extra.putString(Extras.SELECT_MODE, mode)
                arguments = extra
            }
        }
    }
}
