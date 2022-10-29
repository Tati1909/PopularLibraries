package com.example.popularlibraries.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class DefaultSchedulers @Inject constructor() : Schedulers {

    //фоновый поток
    override fun background(): Scheduler =
        io.reactivex.rxjava3.schedulers.Schedulers.newThread()

    //главный поток
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}