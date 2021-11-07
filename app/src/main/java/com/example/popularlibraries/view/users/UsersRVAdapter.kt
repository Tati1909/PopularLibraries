package com.example.popularlibraries.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.databinding.ItemUserBinding
import com.example.popularlibraries.presenter.IItemView
import com.example.popularlibraries.presenter.IUserListPresenter

//Адаптер не имеет ссылок на данные и полностью делегирует процесс наполнения View в Presenter.
//Адаптер — это фреймворковая вещь, а, значит, он —
//ui. Соответственно, логики и данных прямо в нём быть не должно.
//Может показаться, что если адаптер — ui, то он и
//есть View. Но это не так.

//IUserListPresenter имплементит IListPresenter с общими методами обработки списка

class UsersRVAdapter(val presenter: IUserListPresenter) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root), UserItemView {

        override var pos = -1

        override fun setLogin(text: String) = with(binding) {
            loginTextView.text = text
        }
    }

    //ViewHolder имплементит наш интерфейс UserItemView с методом setLogin.
    //А UserItemView имплементит IItemView с позицией элемента списка var pos.
    interface UserItemView : IItemView {
        fun setLogin(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                ////Для вызова itemClickListener используется функция invoke(), так как он может быть равен null. А
                ////также это связано с синтаксическими ограничениями, не позволяющими иначе осуществить вызов
                ////nullable-значения функционального типа. Проще говоря,
                ////presenter.itemClickListener?.invoke(holder) вызовет itemClickListener, если он не равен null.
                presenter.itemClickListener?.invoke(this)
            }
        }
        return viewHolder
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        //ViewHolder передаётся в функцию bindView в качестве интерфейса
        presenter.bindView(holder.apply { pos = position })
}