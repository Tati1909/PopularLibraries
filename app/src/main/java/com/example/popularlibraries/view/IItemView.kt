package com.example.popularlibraries.view

//Мы создали ещё один интерфейс — IItemView, куда вынесли позицию элемента списка. Она
//понадобится для элемента любого списка в нашем приложении.
interface IItemView {
    var pos: Int
}

//Адаптер — это фреймворковая вещь, а, значит, он —
//ui. Соответственно, логики и данных прямо в нём быть не должно.
//Может показаться, что если адаптер — ui, то он и
//есть View. Но это не так. Мы отображаем элементы списка, а значит, View — ViewHolder. Создадим
//для него интерфейс View(ViewHolder будет имплементить наш интерфейс UserItemView)!!
interface UserItemView : IItemView {
    fun setLogin(text: String)
}