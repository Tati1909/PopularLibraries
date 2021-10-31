package com.example.popularlibraries

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

//View должна быть максимально «глупой», а это значит, что все её активные действия сводятся к сообщению
//презентеру о нажатии и установке текста кнопкам.
//При уничтожении View, например, при повороте экрана, она отсоединится от Presenter, а при пересоздании
//присоединится вновь. И на новой View согласно стратегиям будут выполняться команды.
//Используя аннотацию @StateStrategyType, выбираем стратегию сохранения команд AddToEndSingle.
//AddToEndSingleStrategy — добавит пришедшую команду в конец очереди команд. Если
//команда такого типа уже есть в очереди, то действующая удалится. В большинстве случаев
//подходит именно эта стратегия.

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    @AddToEndSingle
    fun setButton1Text(text: String)
    fun setButton2Text(text: String)
    fun setButton3Text(text: String)

}