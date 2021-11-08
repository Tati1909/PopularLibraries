package com.example.popularlibraries.view.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.App
import com.example.popularlibraries.databinding.FragmentUsersBinding
import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.presenter.UsersPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    ////объявляем Presenter и делегируем его создание и хранение
    //через делегат moxyPresenter.
    //moxyPresenter создает новый экземпляр MoxyKtxDelegate.
    //Делегат подключается к жизненному циклу фрагмента
    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(GithubUsersRepo(), App.instance.router)
    }
    private var adapter: UsersRVAdapter? = null

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Уничтожаем ссылки на View Binding в onDestroyView, чтобы не возникла утечка памяти,
    //так как в биндинге хранится ссылка на View.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun init() {
        recyclerView = binding.usersRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val userAdapter = UsersRVAdapter(presenter.usersListPresenter)
        recyclerView.adapter = userAdapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}