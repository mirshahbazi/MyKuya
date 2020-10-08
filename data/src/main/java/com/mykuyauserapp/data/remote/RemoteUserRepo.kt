package com.mykuyauserapp.data.remote

import io.reactivex.rxjava3.core.Observable
import com.mykuyauserapp.data.repo.User
import com.mykuyauserapp.data.repo.UserRepo
import com.mykuyauserapp.data.toUser
import com.mykuyauserapp.network.api.ProfileApi
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
class RemoteUserRepo(
    private val profileApi: ProfileApi
) : UserRepo {
    override fun getProfile(): Observable<User> = profileApi.getProfile().toObservable().toUser()
}