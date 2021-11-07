package com.example.popularlibraries.presenter

import com.example.popularlibraries.view.users.UsersRVAdapter

//ОБЩИЙ ИНТЕРФЕЙС ДЛЯ ОБРАБОТКИ СПИСКОВ

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

//Специальный интерфейс для обработки именно списка пользователей(может понадобятся методы)
//IListPresenter - общий интерфейс для обработки списков
interface IUserListPresenter : IListPresenter<UsersRVAdapter.UserItemView>

//В IItemView мы вынесли позицию элемента списка.
//Она понадобится для элемента любого списка в нашем приложении.
//Например IItemView имплементит UserItemView в UsersRVAdapter
interface IItemView {
    var pos: Int
}
