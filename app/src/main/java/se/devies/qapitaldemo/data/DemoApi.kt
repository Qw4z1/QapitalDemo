package se.devies.qapitaldemo.data

import io.reactivex.Single
import org.joda.time.DateTime
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

data class SavingsGoal(
    val goalImageURL: String,
    val userId: Int,
    val targetAmount: Float,
    val currentBalance: Float,
    val status: String,
    val name: String,
    val id: Int,
    val created: IntArray,
    val connectedUsers: List<Int>?
)

data class SavingsRule(
    val id: Int,
    val type: String,
    val amount: Int
)

data class FeedWrapper(
    val feed: Feed
)

data class Feed(
    val id: String,
    val type: String,
    val timestamp: DateTime,
    val message: String,
    val amount: Float,
    val userId: Int,
    val savingsRuleId: Int
)