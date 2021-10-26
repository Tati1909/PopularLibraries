package com.example.popularlibraries

//View должна быть максимально «глупой», а это значит, что все её активные действия сводятся к сообщению
//презентеру о нажатии и установке текста кнопкам. Кнопки пока будем отличать по индексу (это
//неправильно):
interface MainView {

    fun setButton1Text(text: String)
    fun setButton2Text(text: String)
    fun setButton3Text(text: String)

}