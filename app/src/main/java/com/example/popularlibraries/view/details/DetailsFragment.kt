package com.example.popularlibraries.view.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.popularlibraries.base.di.findComponentDependencies
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.view.details.di.DetailComponent
import com.example.popularlibraries.viewmodel.lazyViewModel
import javax.inject.Inject

class DetailsFragment : Fragment(), BackButtonListener {

    @Inject lateinit var viewModelFactory: DetailsViewModel.Factory

    /** Получаем наш аргумент */
    private val userLogin: String by lazy { arguments?.getString(ARG_USER).orEmpty() }

    /** userLogin - передаем наш логин из bundle вьюмодели. */
    private val viewModel: DetailsViewModel by lazyViewModel {
        viewModelFactory.create(userLogin = userLogin)
    }

    override fun onAttach(context: Context) {
        DetailComponent.build(findComponentDependencies()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent { DetailsScreen(viewModel) }
    }

    override fun backPressed() = viewModel.backPressed()

    companion object {

        //аргумент, который мы передаем во фрагмент
        const val ARG_USER = "arg_user"

        //newInstance вызываем в DetailScreen и передаем в аргумент login из модели
        //а DetailScreen создаем в UsersPresenter в методе displayUser
        fun newInstance(userLogin: String): Fragment {
            return DetailsFragment().apply {
                arguments = bundleOf(ARG_USER to userLogin)
                //to - создает кортеж типа Pair из ARG_USER и userId
            }
        }
    }
}