package com.example.popularlibraries.view.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibraries.App
import com.example.popularlibraries.R
import com.example.popularlibraries.databinding.FragmentUsersBinding
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.di.components.UsersComponent
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.BackButtonListener
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class UsersFragment : MvpAppCompatFragment(R.layout.fragment_users), UsersView, BackButtonListener {

    @Inject lateinit var router: Router
    @Inject lateinit var schedulers: Schedulers
    @Inject lateinit var gitHubUserRepository: GithubUsersRepository

    /** объявляем Presenter и делегируем его создание и хранение
    через делегат moxyPresenter.
    moxyPresenter создает новый экземпляр MoxyKtxDelegate.
    Делегат подключается к жизненному циклу фрагмента */
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            model = gitHubUserRepository,
            router = router
        )
    }

    private var usersComponent: UsersComponent? = null
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        UsersAdapter(presenter::onUserClicked)
    }

    /**
     * Здесь мы инжектим зависимости(router, schedulers,gitHubUserRepository),
     * т.к. MoxyPresenter начинает создаваться в onAttach.
     * Сохраняем их в usersComponent, чтобы потом очистить в onDestroy
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        usersComponent =
            (requireActivity().application as? App)
                ?.applicationComponent
                ?.gitHubUsersComponent()
                ?.build()
                ?.also {
                    it.inject(this@UsersFragment)
                }
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

        binding.usersRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.usersRecyclerview.adapter = adapter.withLoadStateFooter(FooterUsersAdapter())
    }

    //Уничтожаем ссылки на View Binding в onDestroyView, чтобы не возникла утечка памяти,
    //так как в биндинге хранится ссылка на View.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Очищаем экран с субкомпонентом
     */
    override fun onDestroy() {
        super.onDestroy()
        usersComponent = null
    }

    override fun init(usersFlow: Flow<PagingData<GithubUser>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            usersFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed() = presenter.backPressed()

    companion object {

        fun newInstance(): Fragment = UsersFragment().apply { arguments }
    }
}