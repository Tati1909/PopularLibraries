package com.example.popularlibraries.view.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.popularlibraries.R
import com.example.popularlibraries.base.di.findComponentDependencies
import com.example.popularlibraries.databinding.FragmentUsersBinding
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.view.users.di.UsersComponent
import com.example.popularlibraries.viewmodel.lazyViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersFragment : Fragment(R.layout.fragment_users), BackButtonListener {

    @Inject lateinit var viewModelFactory: UsersViewModel.Factory

    private val viewModel: UsersViewModel by lazyViewModel { viewModelFactory.create() }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        UsersAdapter(viewModel::onUserClicked)
    }

    /** Здесь мы инжектим зависимости(router,gitHubUserRepository). */
    override fun onAttach(context: Context) {
        UsersComponent.build(findComponentDependencies()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usersRecyclerview.adapter = adapter.withLoadStateFooter(FooterUsersAdapter())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { uiState ->
                showState(uiState)
            }
        }
    }

    private fun showState(state: UiState<Flow<PagingData<GithubUser>>>) {
        hideAllStates(state)
        when (state) {
            is UiState.Error -> {
                showError(state.description)
            }
            is UiState.Content -> {
                showUsers(state.data)
            }
            is UiState.Loading -> {
                binding.progressBar.isVisible = true
            }
        }
    }

    private fun hideAllStates(excludeState: UiState<*>) {
        if (excludeState !is UiState.Content) {
            binding.swipeRefreshLayout.isVisible = false
        }
        if (excludeState !is UiState.Loading) {
            binding.progressBar.isVisible = false
        }
    }

    //Уничтожаем ссылки на View Binding в onDestroyView, чтобы не возникла утечка памяти,
    //так как в биндинге хранится ссылка на View.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUsers(usersFlow: Flow<PagingData<GithubUser>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            usersFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed() = viewModel.backPressed()

    companion object {

        fun newInstance(): Fragment = UsersFragment().apply { arguments }
    }
}