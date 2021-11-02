package com.example.popularlibraries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibraries.databinding.ItemUserBinding
import com.example.popularlibraries.presenter.IUserListPresenter

//Адаптер не имеет ссылок на данные и полностью делегирует процесс наполнения View в Presenter.

class UsersRVAdapter(val presenter: IUserListPresenter) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
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

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        //ViewHolder передаётся в функцию bindView в качестве интерфейса
        presenter.bindView(holder.apply {
            pos = position
        })

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root), UserItemView {
        //переменная и метод интерфейсов
        override var pos = -1

        //устанавливаем логин
        override fun setLogin(text: String) = with(binding) {
            loginTextView.text = text
        }
    }
}