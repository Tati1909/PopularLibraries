package com.example.popularlibraries.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

//@AddToEndSingle - есть ещё такой алиас
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView