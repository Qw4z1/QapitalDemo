package se.devies.qapitaldemo

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.devies.qapitaldemo.data.AppDatabase
import se.devies.qapitaldemo.data.DemoApi
import se.devies.qapitaldemo.data.DemoRepo
import se.devies.qapitaldemo.data.DemoStore

class GoalsApplication : Application() {

    val repo: DemoRepo by lazy { DemoRepo(createApi(), createStore()) }

    private fun createApi() =
        Retrofit.Builder()
            .baseUrl("http://qapital-ios-testtask.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(DemoApi::class.java)

    private fun createStore() = DemoStore(createDb())

    private fun createDb() =
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "demo-db"
        ).build()
}

val Fragment.repo
    get() = (activity?.application as GoalsApplication).repo