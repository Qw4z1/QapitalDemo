package se.devies.qapitaldemo.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DemoApi {
    @GET("/savingsgoals")
    fun getSavingsGoals(): Single<GoalsWrapper>

    @GET("/savingsrules")
    fun getSavingsRules(): Single<List<SavingsRule>>

    @GET("/savingsgoals/{id}/feed")
    fun getFeed(@Path("id") id: Int): Single<FeedWrapper>
}

data class GoalsWrapper(
    val savingsGoals: List<SavingsGoal>
)

data class FeedWrapper(
    val feed: Feed
)
