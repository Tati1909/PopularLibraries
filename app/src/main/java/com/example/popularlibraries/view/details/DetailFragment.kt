package com.example.popularlibraries.view.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibraries.App
import com.example.popularlibraries.R
import com.example.popularlibraries.databinding.FragmentDetailsBinding
import com.example.popularlibraries.model.di.components.DetailComponent
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.scheduler.Schedulers
import com.example.popularlibraries.view.setStartDrawableCircleImageFromUri
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class DetailFragment : MvpAppCompatFragment(R.layout.fragment_details), DetailsView, BackButtonListener {

    companion object {

        //аргумент, который мы передаем во фрагмент
        const val ARG_USER = "arg_user"

        //newInstance вызываем в DetailScreen и передаем в аргумент login из модели
        //а DetailScreen создаем в UsersPresenter в методе displayUser
        fun newInstance(userLogin: String): Fragment {
            return DetailFragment().apply {
                arguments = bundleOf(ARG_USER to userLogin)
                //to - создает кортеж типа Pair из ARG_USER и userId
            }
        }
    }

    @Inject
    lateinit var schedulers: Schedulers

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var gitHubUserRepository: GithubUsersRepository

    private var detailComponent: DetailComponent? = null

    /**
     * Получаем наш аргумент
     */
    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER).orEmpty()
    }

    /**
     * userLogin - передаем наш логин из bundle презентеру.
     */
    private val presenter: DetailPresenter by moxyPresenter {
        DetailPresenter(
            userLogin = userLogin,
            gitHubRepo = gitHubUserRepository,
            schedulers = schedulers,
            router = router
        )
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val userReposAdapter by lazy(LazyThreadSafetyMode.NONE) {
        UserReposAdapter(onItemClicked = presenter::onItemClicked)
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

        binding.listRepositoryRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.listRepositoryRecyclerview.adapter = userReposAdapter
    }

    override fun showUser(user: GitHubUserEntity) {
        user.avatarUrl?.let { avatarUrl ->
            binding.loginUser.setStartDrawableCircleImageFromUri(avatarUrl)
        }
        binding.loginUser.text = user.login
    }

    override fun showRepos(gitHubUserRepos: List<GitHubUserRepoEntity>) {
        userReposAdapter.submitList(gitHubUserRepos)
    }

    override fun loadingLayoutIsVisible(isVisible: Boolean) {
        binding.loadingLayout.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun showUserNotFound() {
        Toast.makeText(
            requireContext(),
            getString(R.string.user_not_found_message),
            Toast.LENGTH_LONG
        ).show()

    }

    override fun showReposNotFound() {
        Toast.makeText(
            requireContext(),
            getString(R.string.user_repositories_not_found_message),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed() = presenter.backPressed()

    override fun onDestroy() {
        super.onDestroy()
        detailComponent = null
    }
}