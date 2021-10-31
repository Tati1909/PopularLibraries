package com.example.popularlibraries

import moxy.MvpPresenter

//в презентере нельзя упоминать класс R, относящийся к AndroidSDK
//c Moxy model передаем в конструктор
class Presenter(val model: Model): MvpPresenter<MainView>() {

    //Нажимаем счетчик
    fun counter1Click(){
        //увеличиваем счетчик1 и возвращаем его
        val nextValue = model.next(0)
        viewState.setButton1Text(nextValue.toString())
    }
    fun counter2Click(){
        //увеличиваем счетчик2 и возвращаем его
        val nextValue = model.next(1)
        viewState.setButton2Text(nextValue.toString())
    }
    fun counter3Click(){
        //увеличиваем счетчик3 и возвращаем его
        val nextValue = model.next(2)
        viewState.setButton3Text(nextValue.toString())
    }
}