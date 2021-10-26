package com.example.popularlibraries

class Presenter(val view: MainView) {

    val model = Model()

    //Архитектурная ошибка. В качестве практического задания -- исправить
    //Нажимаем счетчик
    fun counterClick(id: Int){
        when(id){
            R.id.btn_counter1 -> {
                //увеличиваем счетчик1 и возвращаем его
                val nextValue = model.next(0)
                view.setButtonText(0, nextValue.toString())
            }
            R.id.btn_counter2 -> {
                //увеличиваем счетчик2 и возвращаем его
                val nextValue = model.next(1)
                view.setButtonText(1, nextValue.toString())
            }
            R.id.btn_counter3 -> {
                //увеличиваем счетчик3 и возвращаем его
                val nextValue = model.next(2)
                view.setButtonText(2, nextValue.toString())
            }
        }
    }
}