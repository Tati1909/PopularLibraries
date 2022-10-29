package com.example.popularlibraries.networkstatus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.NetworkInfo
import io.reactivex.rxjava3.android.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * NetworkState передаем в наш Observable как сигнал, который мы будем обрабатывать.
 * NetworkState будет следить за тем, есть ли у нас интернет или нет.
 * В конструктор класса передаем context, чтобы получить доступ к ConnectivityManager.
 */

class NetworkStateObservable(private val context: Context) : Observable<NetworkState>() {

    /**
     * subscribeActual(слушатель сети) вызовется в момент подписки в MainActivity  в onCreate
     * Оборачиваем наш слушатель NetworkStateListener с BroadcastReceiver в ректив
     */
    override fun subscribeActual(observer: Observer<in NetworkState>) {
        val listener = NetworkStateListener(context, observer)
        observer.onSubscribe(listener)
    }

    class NetworkStateListener(
        private val context: Context,
        private val observer: Observer<in NetworkState>
    ) : BroadcastReceiver(), Disposable {

        /**
         * Отписываемся на главном потоке
         */
        private val disposable = object : MainThreadDisposable() {
            override fun onDispose() {
                context.unregisterReceiver(this@NetworkStateListener)
            }
        }

        private val connectivityManager: ConnectivityManager by lazy {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        /**
         * Регистрируем Receiver
         */
        init {
            context.registerReceiver(this, IntentFilter(CONNECTIVITY_ACTION))
        }

        /**
         * Получаем обновления:
         * при наличии интернета передаем в onNext наши статусы сети(connected и disconnected)
         * при отсутствии - бросаем ошибку в onError
         */
        override fun onReceive(context: Context, intent: Intent) {
            try {
                if (!isDisposed && intent.action == CONNECTIVITY_ACTION) {
                    takeNetworkState(connectivityManager.activeNetworkInfo)
                        .let(observer::onNext)
                }
            } catch (error: Throwable) {
                observer.onError(error)
            }
        }

        /**
         * Превращаем NetworkInfo в наши статусы сети
         */
        private fun takeNetworkState(networkInfo: NetworkInfo?): NetworkState =
            when (networkInfo?.isConnected) {
                true -> NetworkState.CONNECTED
                else -> NetworkState.DISCONNECTED
            }

        override fun dispose() = disposable.dispose()

        override fun isDisposed(): Boolean = disposable.isDisposed

    }
}