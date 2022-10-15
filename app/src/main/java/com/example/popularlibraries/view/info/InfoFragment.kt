package com.example.popularlibraries.view.info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.popularlibraries.App
import com.example.popularlibraries.R
import com.example.popularlibraries.base.collectNotEmptyWhenStarted
import com.example.popularlibraries.base.collectNotNullWhenStarted
import com.example.popularlibraries.base.collectTrueWhenStarted
import com.example.popularlibraries.base.collectWhenStarted
import com.example.popularlibraries.databinding.FragmentInfoBinding
import com.example.popularlibraries.model.di.components.InfoComponent
import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.scheduler.Schedulers
import com.example.popularlibraries.viewmodel.lazyViewModel
import javax.inject.Inject

class InfoFragment : Fragment(R.layout.fragment_info) {

    @Inject lateinit var schedulers: Schedulers
    @Inject lateinit var userRepository: GithubUsersRepository
    private var infoComponent: InfoComponent? = null
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    //получаем наш аргумент
    private val repositoryUrl: String by lazy { arguments?.getString(ARG_REPO_URL).orEmpty() }

    /** repositoryUrl - передаем ссылку на репозиторий из bundle презентеру. */
    val viewModel: InfoViewModel by lazyViewModel {
        InfoViewModel(
            gitHubUsersRepository = userRepository,
            repositoryUrl = repositoryUrl,
            schedulers = schedulers
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        infoComponent =
            (requireActivity().application as? App)
                ?.applicationComponent
                ?.gitHubInfoComponent()
                ?.build()
                ?.also { it.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectNotNullWhenStarted(viewModel.repositoryInfo, ::showRepoInfo)
        collectWhenStarted(viewModel.loading, ::toggleLoading)
        collectNotEmptyWhenStarted(viewModel.error, ::showError)
        collectTrueWhenStarted(viewModel.repositoryNotFoundShowed) {
            showRepoNotFound()
            viewModel.onRepositoryNotFoundShowed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        infoComponent = null
    }

    private fun showRepoInfo(gitHubUserRepoInfoEntity: GitHubUserRepoInfoEntity) {
        with(binding) {
            name.text = gitHubUserRepoInfoEntity.name
            fullName.text = gitHubUserRepoInfoEntity.fullName
            size.text = gitHubUserRepoInfoEntity.size.toString()
            watchersCount.text = gitHubUserRepoInfoEntity.watchersCount.toString()
            language.text = gitHubUserRepoInfoEntity.language
            forksCount.text = gitHubUserRepoInfoEntity.forksCount.toString()

            toggleLoading(false)
        }

    }

    private fun toggleLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun showError(error: String) {
        toggleLoading(false)
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun showRepoNotFound() {
        Toast.makeText(
            requireContext(),
            getString(R.string.user_repository_not_found_message),
            Toast.LENGTH_LONG
        ).show()
        toggleLoading(false)
    }

    companion object {

        //аргумент, который мы передаем во фрагмент
        const val ARG_REPO_URL = "repository_url"

        //newInstance вызываем в InfoScreen и передаем в аргумент repositoryUrl из модели,
        //а InfoScreen создаем в InfoPresenter в методе displayUser
        fun newInstance(repositoryUrl: String): Fragment {
            return InfoFragment().apply {
                arguments = bundleOf(ARG_REPO_URL to repositoryUrl)
                //to - создает кортеж типа Pair из ARG_REPO_URL и repositoryUrl
            }
        }
    }
}