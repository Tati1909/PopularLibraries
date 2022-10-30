package com.example.popularlibraries.view.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.popularlibraries.R
import com.example.popularlibraries.base.collectNotEmptyWhenStarted
import com.example.popularlibraries.base.collectNotNullWhenStarted
import com.example.popularlibraries.base.collectWhenStarted
import com.example.popularlibraries.base.di.findComponentDependencies
import com.example.popularlibraries.databinding.FragmentDetailsBinding
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.view.details.di.DetailComponent
import com.example.popularlibraries.view.setStartDrawableCircleImageFromUri
import com.example.popularlibraries.viewmodel.lazyViewModel
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details), BackButtonListener {

    @Inject lateinit var viewModelFactory: DetailsViewModel.Factory
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    /** Получаем наш аргумент */
    private val userLogin: String by lazy { arguments?.getString(ARG_USER).orEmpty() }

    /** userLogin - передаем наш логин из bundle вьюмодели. */
    private val viewModel: DetailsViewModel by lazyViewModel {
        viewModelFactory.create(userLogin = userLogin)
    }

    private val userReposAdapter by lazy(LazyThreadSafetyMode.NONE) {
        UserReposAdapter(onItemClicked = viewModel::onItemClicked)
    }

    override fun onAttach(context: Context) {
        DetailComponent.build(findComponentDependencies()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.repositories.adapter = userReposAdapter
        initStateHandlers()
    }

    private fun initStateHandlers() {
        collectNotNullWhenStarted(viewModel.data) { (user: GitHubUserEntity, gitHubUserRepos: List<GitHubUserRepoEntity>) ->
            showUser(user, gitHubUserRepos)
        }
        collectWhenStarted(viewModel.loading, ::loadingLayoutIsVisible)
        collectNotEmptyWhenStarted(viewModel.error, ::showError)
    }

    private fun showUser(user: GitHubUserEntity, gitHubUserRepos: List<GitHubUserRepoEntity>) {
        user.avatarUrl?.let { avatarUrl ->
            binding.loginUser.setStartDrawableCircleImageFromUri(avatarUrl)
        }
        binding.loginUser.text = user.login
        userReposAdapter.submitList(gitHubUserRepos)
    }

    private fun loadingLayoutIsVisible(loading: Boolean) {
        binding.loadingLayout.root.isVisible = loading
    }

    private fun showUserNotFound() {
        Toast.makeText(
            requireContext(),
            getString(R.string.user_not_found_message),
            Toast.LENGTH_LONG
        ).show()

    }

    private fun showReposNotFound() {
        Toast.makeText(
            requireContext(),
            getString(R.string.user_repositories_not_found_message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
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