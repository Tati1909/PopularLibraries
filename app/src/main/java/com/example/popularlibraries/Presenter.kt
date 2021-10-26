package com.example.popularlibraries

//в презентере нельзя упоминать класс R, относящийся к AndroidSDK
class Presenter(val view: MainView) {

    private val model = Model()

    //Нажимаем счетчик
    fun counter1Click(){
        //увеличиваем счетчик1 и возвращаем его
        val nextValue = model.next(0)
        view.setButton1Text(nextValue.toString())
    }
    fun counter2Click(){
        //увеличиваем счетчик2 и возвращаем его
        val nextValue = model.next(1)
        view.setButton2Text(nextValue.toString())
    }
    fun counter3Click(){
        //увеличиваем счетчик3 и возвращаем его
        val nextValue = model.next(2)
        view.setButton3Text(nextValue.toString())
    }
}