package com.example.popularlibraries.view.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.popularlibraries.App
import com.example.popularlibraries.R
import com.example.popularlibraries.base.collectNotEmptyListWhenStarted
import com.example.popularlibraries.base.collectNotEmptyWhenStarted
import com.example.popularlibraries.base.collectNotNullWhenStarted
import com.example.popularlibraries.base.collectTrueWhenStarted
import com.example.popularlibraries.base.collectWhenStarted
import com.example.popularlibraries.databinding.FragmentDetailsBinding
import com.example.popularlibraries.model.di.components.DetailComponent
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.scheduler.Schedulers
import com.example.popularlibraries.view.setStartDrawableCircleImageFromUri
import com.example.popularlibraries.viewmodel.lazyViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details), BackButtonListener {

    @Inject lateinit var schedulers: Schedulers
    @Inject lateinit var router: Router
    @Inject lateinit var gitHubUserRepository: GithubUsersRepository
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var detailComponent: DetailComponent? = null

    /** Получаем наш аргумент */
    private val userLogin: String by lazy { arguments?.getString(ARG_USER).orEmpty() }

    /** userLogin - передаем наш логин из bundle вьюмодели. */
    private val viewModel: DetailsViewModel by lazyViewModel {
        DetailsViewModel(
            userLogin = userLogin,
            gitHubRepo = gitHubUserRepository,
            schedulers = schedulers,
            router = router
        )
    }

    private val userReposAdapter by lazy(LazyThreadSafetyMode.NONE) {
        UserReposAdapter(onItemClicked = viewModel::onItemClicked)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        detailComponent =
            (requireActivity().application as? App)
                ?.applicationComponent
                ?.gitHubUserComponent()
                ?.build()
                ?.also { it.inject(this) }
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
        collectNotNullWhenStarted(viewModel.userEntity, ::showUser)
        collectNotEmptyListWhenStarted(viewModel.repoEntity, ::showRepos)
        collectWhenStarted(viewModel.loading, ::loadingLayoutIsVisible)
        collectNotEmptyWhenStarted(viewModel.error, ::showError)
        collectTrueWhenStarted(viewModel.userNotFoundShowed) {
            showUserNotFound()
            viewModel.onUserNotFoundShowed()
        }
        collectTrueWhenStarted(viewModel.reposNotFoundShowed) {
            showReposNotFound()
            viewModel.onReposNotFoundShowed()
        }
    }

    private fun showUser(user: GitHubUserEntity) {
        user.avatarUrl?.let { avatarUrl ->
            binding.loginUser.setStartDrawableCircleImageFromUri(avatarUrl)
        }
        binding.loginUser.text = user.login
    }

    private fun showRepos(gitHubUserRepos: List<GitHubUserRepoEntity>) {
        userReposAdapter.submitList(gitHubUserRepos)
    }

    private fun loadingLayoutIsVisible(isVisible: Boolean) {
        binding.loadingLayout.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
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

    override fun onDestroy() {
        super.onDestroy()
        detailComponent = null
    }

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