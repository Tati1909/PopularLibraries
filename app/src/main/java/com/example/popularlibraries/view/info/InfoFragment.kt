package com.example.popularlibraries.view.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.popularlibraries.R
import com.example.popularlibraries.databinding.FragmentInfoBinding
import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.view.inject.DaggerMvpFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class InfoFragment : DaggerMvpFragment(R.layout.fragment_info), InfoView {

    companion object {

        //аргумент, который мы передаем во фрагмент
        private const val ARG_REPO_URL = "repository_url"

        //newInstance вызываем в InfoScreen и передаем в аргумент repositoryUrl из модели,
        //а InfoScreen создаем в InfoPresenter в методе displayUser
        fun newInstance(repositoryUrl: String): Fragment {
            return InfoFragment().apply {
                arguments = bundleOf(ARG_REPO_URL to repositoryUrl)
                //to - создает кортеж типа Pair из ARG_REPO_URL и repositoryUrl
            }
        }
    }

    @Inject
    lateinit var presenterFactory: InfoPresenterFactory

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    //получаем наш аргумент
    private val repositoryUrl: String by lazy {
        arguments?.getString(ARG_REPO_URL).orEmpty()
    }

    /**
     * repositoryUrl - передаем ссылку на репозиторий из bundle презентеру.
     */
    private val presenter: InfoPresenter by moxyPresenter {
        presenterFactory.create(repositoryUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun showRepoInfo(gitHubUserRepoInfoEntity: GitHubUserRepoInfoEntity) {
        with(binding) {
            name.text = gitHubUserRepoInfoEntity.name
            fullName.text = gitHubUserRepoInfoEntity.fullName
            size.text = gitHubUserRepoInfoEntity.size.toString()
            watchersCount.text = gitHubUserRepoInfoEntity.watchersCount.toString()
            language.text = gitHubUserRepoInfoEntity.language
            forksCount.text = gitHubUserRepoInfoEntity.forksCount.toString()

            loadingLayoutIsVisible(false)
        }

    }

    override fun loadingLayoutIsVisible(isVisible: Boolean) {
        binding.loadingLayout.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
        loadingLayoutIsVisible(false)
    }

    override fun showRepoNotFound() {
        Toast.makeText(
            requireContext(),
            getString(R.string.user_repository_not_found_message),
            Toast.LENGTH_LONG
        ).show()
        loadingLayoutIsVisible(false)
    }

}