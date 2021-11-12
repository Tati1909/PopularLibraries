package com.example.popularlibraries.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.popularlibraries.databinding.FragmentDetailsBinding
import com.example.popularlibraries.model.GitHubUserRepositoryFactory
import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.presenter.DetailPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class DetailFragment : MvpAppCompatFragment(), DetailsView {

    companion object {

        //аргумент, который мы передаем во фрагмент
        private const val ARG_USER = "arg_user"

        //newInstance вызываем в DetailScreen и передаем в аргумент login из модели
        //а DetailScreen создаем в UsersPresenter в методе displayUser
        fun newInstance(userId: String): Fragment {
            return DetailFragment().apply {
                arguments = bundleOf(ARG_USER to userId)
                //to - создает кортеж типа Pair из ARG_USER и userId
            }
        }
    }

    //получаем наш аргумент
    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER).orEmpty()
    }

    //передаем наш логин из bundle презентеру
    val presenter: DetailPresenter by moxyPresenter {
        DetailPresenter(
            userLogin = userLogin,
            gitHubRepo = GitHubUserRepositoryFactory.create()
        )
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun showUser(user: GithubUser) {
        binding.loginUser.text = user.login
    }
}