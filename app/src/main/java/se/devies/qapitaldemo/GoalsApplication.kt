package se.devies.qapitaldemo

import android.app.Application
import androidx.fragment.app.Fragment
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.devies.qapitaldemo.data.DemoApi
import se.devies.qapitaldemo.data.DemoRepo
import se.devies.qapitaldemo.data.DemoStore

class GoalsApplication: Application() {

    lateinit var repo: DemoRepo

    override fun onCreate() {
        super.onCreate()
        initRepo()
    }

    private fun initRepo() {
        val api = createApi()
        val store = createStore()
        repo = DemoRepo(api, store)
    }

    private fun createApi() =
        Retrofit.Builder()
            .baseUrl("http://qapital-ios-testtask.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(DemoApi::class.java)

    private fun createStore() = DemoStore()
}

val Fragment.repo
    get() = (activity?.application as GoalsApplication).repo