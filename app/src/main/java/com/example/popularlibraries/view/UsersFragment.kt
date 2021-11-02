package com.example.popularlibraries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibraries.databinding.FragmentUsersBinding
import com.example.popularlibraries.model.GithubUsersRepo
import com.example.popularlibraries.presenter.UsersPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

//Суть практически не изменилась. Все изменения, кроме нового параметра в конструкторе Presenter,
//касаются процедуры создания самого фрагмента. Единственный момент, на который стоит обратить
//внимание — уничтожение ссылки на View Binding в onDestroyView, чтобы не возникла утечка памяти,
//так как в биндинге хранится ссылка на View.
class UsersFragment : MvpAppCompatFragment(), UsersView {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(GithubUsersRepo())
    }
    private var adapter: UsersRVAdapter? = null
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Так как всё, что появится на экране — просто список, интерфейс включает всего два метода:
    //● init() — для первичной инициализации списка, который мы будем вызывать при
    //присоединении View к Presenter;
    //● updateList() — для обновления содержимого списка.
    override fun init() {
        binding.usersRecyclerview.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        binding.usersRecyclerview.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    // override fun backPressed() = presenter.backPressed()
}