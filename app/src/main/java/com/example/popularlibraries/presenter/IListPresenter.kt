package com.example.popularlibraries.presenter

import com.example.popularlibraries.view.IItemView
import com.example.popularlibraries.view.UserItemView

//Так как теоретически в нашем приложении может быть много списков
//пользователей, создадим для него интерфейс. Далее реализуем интерфейс в Presenter экрана, а
//затем отдадим эту реализацию в качестве интерфейса в адаптер, чтобы делегировать ему всю
//логику:
//Здесь V представляет собой тип View для строки списка, а itemClickListener — функция,
//принимающая на вход эту самую View. Таким образом, при обработке клика мы получаем от View
//позицию и находим требуемый элемент.
//В интерфейс мы вынесли общие для всех списков вещи:
//● слушатель клика;
//● функция получения количества элементов;
//● функция наполнения View.
interface IListPresenter<V : IItemView> {

    var itemClickListener: ((V) -> Unit)?

    fun bindView(view: V)
    fun getCount(): Int
}
interface IUserListPresenter : IListPresenter<UserItemView>