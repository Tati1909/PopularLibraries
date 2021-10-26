package com.example.popularlibraries

class Model {

    val counters = mutableListOf (0, 0, 0)

    //вернуть текущий элемент списка
    private fun getCurrent(index: Int): Int {

        return counters[index]
    }

    //увеличиваем выбранный счетчик и возвращаем его
    fun next(index: Int): Int {

        counters[index]++
        return getCurrent(index)
    }

    fun set(index: Int, value: Int){

        counters[index] = value
    }
}