package com.andyanika.translator.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyanika.translator.feature.history.databinding.FragmentHistoryBinding
import com.andyanika.usecases.di.koin.UseCaseComponent
import com.andyanika.widgets.observe
import core.models.FavoriteModel
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf

class HistoryFragment : ScopeFragment() {
    init {
        loadKoinModules(UseCaseComponent.getModule())
    }

    private val vm by viewModel<HistoryViewModel>()
    private val action = { model: FavoriteModel -> vm.subscribeItemClick(model) }
    private val adapter by inject<HistoryListAdapter> { parametersOf(action) }

    private lateinit var binding: FragmentHistoryBinding

    private val inputTextFlow: Flow<String> by lazy {
        binding.editInput.observe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        with(binding) {
            vm.data.observe(viewLifecycleOwner, adapter::setData)
            vm.showClearBtn.observe(viewLifecycleOwner, { show: Boolean ->
                if (show) {
                    btnClear.visibility = View.VISIBLE
                } else {
                    btnClear.visibility = View.INVISIBLE
                    editInput.setText("")
                }
            })

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        binding.btnClear.setOnClickListener { vm.showClearBtn.setValue(false) }
        vm.subscribeSearch(inputTextFlow, LIMIT)
    }

    override fun onStop() {
        binding.btnClear.setOnClickListener(null)
        super.onStop()
    }

    companion object {
        private const val LIMIT = 100
    }
}
