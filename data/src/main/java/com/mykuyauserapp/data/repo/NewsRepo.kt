package com.mykuyauserapp.data.repo

import io.reactivex.rxjava3.core.Observable
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
interface NewsRepo {
    fun getNews(): Observable<List<News>>
}