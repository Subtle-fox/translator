package com.andyanika.translator.feature.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andyanika.translator.common.constants.Extras
import com.andyanika.translator.common.models.ui.DisplayLanguageModel
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectLanguageFragment : ScopeFragment() {
    private val action: (DisplayLanguageModel) -> Unit = { vm.onItemClick(it) }
    private val adapter: SelectLanguageListAdapter by inject { parametersOf(action) }

    //    @Inject
//    var viewModelFactory: ViewModelProvider.Factory? = null
    private val vm: SelectLanguageViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_language, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.title_select_language)

        val layoutManager = LinearLayoutManager(context)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get<SelectLanguageViewModel>(SelectLanguageViewModel::class.java)
        vm.data.observe(viewLifecycleOwner, { newData: List<DisplayLanguageModel> -> adapter.setData(newData) })
        if (savedInstanceState == null) {
            arguments?.getString(Extras.SELECT_MODE)?.let {
                vm.setMode(it)
                vm.loadData()
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        viewModel!!.subscribeItemClick(adapter.getObservable())
    }

    override fun onStop() {
//        viewModel!!.unsubscribeItemClick()
        super.onStop()
    }

    companion object {
        fun create(mode: String?): SelectLanguageFragment {
            val extra = Bundle()
            extra.putString(Extras.SELECT_MODE, mode)
            val fragment = SelectLanguageFragment()
            fragment.arguments = extra
            return fragment
        }
    }
}
