package com.csp.cache

import ApiManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.csp.cache.bean.InspectAppUpdateBean
import com.csp.cache.bean.UserBean
import com.csp.cache.net.ApiBaseEntity
import com.csp.cache.util.LogUtil
import com.csp.cache.util.SPUtil
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnA.setOnClickListener {
            getAAA()
        }
        btnB.setOnClickListener {
            getBBB()
        }
        clearCach.setOnClickListener {
            SPUtil.clear(this)
        }
    }

    fun getAAA() {
        ApiManager.getInstance().getApiService().getAAA()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ApiBaseEntity<InspectAppUpdateBean>> {


                override fun onSuccess(t: ApiBaseEntity<InspectAppUpdateBean>) {
                    tv.text = t.data.toString()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    LogUtil.i(e.toString())
                }
            })
    }

    fun getBBB() {
        ApiManager.getInstance().getApiService().getBBB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ApiBaseEntity<UserBean>> {

                override fun onSuccess(t: ApiBaseEntity<UserBean>) {
                    tv.text = t.data.toString()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    LogUtil.i(e.toString())
                }
            })
    }

}
