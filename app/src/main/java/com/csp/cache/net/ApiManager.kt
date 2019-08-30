import com.csp.cache.net.MyInterceptor
import com.csp.coroutines.net.ApiService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by kjt on 2019-07-24
 */
class ApiManager private constructor() {

    companion object {
         val baseUrl = "http://127.0.0.1:8080/"
        fun getInstance(): ApiManager {
            val instance: ApiManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                ApiManager()
            }
            return instance
        }
    }

    private lateinit var mRetrofit: Retrofit
    //    private val baseUrl = "http://v.juhe.cn/"


    private val mApiService: ApiService by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(MyInterceptor())
            .build()
        mRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        mRetrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        return mApiService
    }

}