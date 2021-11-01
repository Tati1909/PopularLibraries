package com.example.popularlibraries.view

//Адаптер — это фреймворковая вещь, а, значит, он —
//ui. Соответственно, логики и данных прямо в нём быть не должно.
//Может показаться, что если адаптер — ui, то он и
//есть View. Но это не так. Мы отображаем элементы списка, а значит, View — ViewHolder. Создадим
//для него интерфейс View:
interface UserItemView: IItemView {
    fun setLogin(text: String)
}