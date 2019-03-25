package se.devies.qapitaldemo.data

import com.google.gson.*
import com.google.gson.JsonParseException
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type


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
    val feed: List<Feed>
)

class DateTimeTypeConverter : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    override fun serialize(src: DateTime, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toString())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): DateTime {
        return DateTime(json.asString)
    }
}