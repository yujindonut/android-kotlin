package work.risingcamp.week9

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetWorkClient {

    private const val DUST_BASE_URL = "http://openapi.seoul.go.kr:8088/6d4748497a62626234384854785472/json/bikeList/850/950/"


    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val bicycleRetrofit = Retrofit.Builder()
        .baseUrl(DUST_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(createOkHttpClient()).build()

    val bicycleNetWork: NetWorkInterface = bicycleRetrofit.create(NetWorkInterface::class.java)

}