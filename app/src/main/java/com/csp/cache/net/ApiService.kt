package com.csp.coroutines.net

import com.csp.cache.bean.InspectAppUpdateBean
import com.csp.cache.bean.UserBean
import com.csp.cache.net.ApiBaseEntity
import com.csp.cache.net.UrlConstant
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by kjt on 2019-07-24
 */
interface ApiService {


    @GET(UrlConstant.aaa)
    fun getAAA():Single<ApiBaseEntity<InspectAppUpdateBean>>

    @GET(UrlConstant.bbb)
    fun getBBB():Single<ApiBaseEntity<UserBean>>
}