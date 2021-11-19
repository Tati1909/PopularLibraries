package com.example.popularlibraries.view.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibraries.App
import com.example.popularlibraries.databinding.FragmentUsersBinding
import com.example.popularlibraries.model.GitHubUserRepositoryFactory
import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.presenter.UsersPresenter
import com.example.popularlibraries.scheduler.SchedulersFactory
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, UsersRVAdapter.Delegate,
    BackButtonListener {

    companion object {
        fun newInstance(): Fragment = UsersFragment().apply { arguments }
    }

    ////объявляем Presenter и делегируем его создание и хранение
    //через делегат moxyPresenter.
    //moxyPresenter создает новый экземпляр MoxyKtxDelegate.
    //Делегат подключается к жизненному циклу фрагмента
    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            schedulers = SchedulersFactory.create(),
            model = GitHubUserRepositoryFactory.create(),
            router = App.instance.router
        )
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter = UsersRVAdapter(delegate = this)

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

        binding.usersRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.usersRecyclerview.adapter = adapter
    }

    //Уничтожаем ссылки на View Binding в onDestroyView, чтобы не возникла утечка памяти,
    //так как в биндинге хранится ссылка на View.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun init(users: List<GithubUser>) {
        //чтобы обновить представление списка, вызовите submitList(),
        //передав список пользователей из модели
        adapter.submitList(users)
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(user: GithubUser) {
        presenter.displayUser(user)
    }

    override fun backPressed() = presenter.backPressed()


}