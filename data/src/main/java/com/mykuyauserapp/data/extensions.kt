package com.mykuyauserapp.data

import io.reactivex.rxjava3.core.Observable
import com.mykuyauserapp.data.repo.News
import com.mykuyauserapp.data.repo.Service
import com.mykuyauserapp.data.repo.User
import com.mykuyauserapp.network.model.NewsResponse
import com.mykuyauserapp.network.model.ProfileResponse
import com.mykuyauserapp.network.model.PromotionResponse
import com.mykuyauserapp.network.model.ServicesResponse
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
internal fun Observable<PromotionResponse>.toPromotions() = this.map { it.promotions }
    .map { it.map { Service(it.title, it.icon) } }

internal fun Observable<ServicesResponse>.toServices() = map { it.services }.map {
    it.map { Service(it.title, it.icon) }
}

internal fun Observable<NewsResponse>.toNews() = map { it.news }.map {
    it.map { News(it.id, it.title, it.description, it.banner) }
}

internal fun Observable<ProfileResponse>.toUser() = map { it.profile }.map { User(it.id, it.name, it.lastName) }