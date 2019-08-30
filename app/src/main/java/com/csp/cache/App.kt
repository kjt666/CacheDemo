package com.csp.cache

import android.app.Application
import com.csp.cache.net.UrlConstant
import java.io.File

/**
 * Created by kjt on 2019-08-29
 */
class App : Application() {

    companion object {
        lateinit var app: App
        var cacheUrls = arrayListOf(
            ApiManager.baseUrl + UrlConstant.aaa,
            ApiManager.baseUrl + UrlConstant.bbb)
        lateinit var CACHE_PATH: String
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        CACHE_PATH = cacheDir.absolutePath + File.separator
    }
}
