package com.mykuyauserapp.base
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
sealed class Loadable<out T> {
    object Loading: Loadable<Nothing>()
    data class Loaded<out T>(val data: T): Loadable<T>()
    data class Failed(val throwable: Throwable): Loadable<Nothing>()
}

fun <T> Loadable<T>.onLoad(invoke: T.() -> Unit) {
    when (this) {
        is Loadable.Loaded -> invoke(this.data)
    }
}

fun <T> Loadable<T>.onFail(invoke: Throwable.() -> Unit) {
    when (this) {
        is Loadable.Failed -> invoke(this.throwable)
    }
}

fun <T> Loadable<T>.onLoading(invoke: () -> Unit) {
    when (this) {
        is Loadable.Loading -> invoke()
    }
}