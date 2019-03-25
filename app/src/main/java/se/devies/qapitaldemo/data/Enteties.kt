package se.devies.qapitaldemo.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import org.joda.time.DateTime

@Entity
data class SavingsGoal(
    @PrimaryKey val id: Int,
    val userId: Int,
    val goalImageURL: String,
    val targetAmount: Float,
    val currentBalance: Float,
    val status: String,
    val name: String,
    val created: List<Int>,
    val connectedUsers: List<Int>?
)

@Entity
data class SavingsRule(
    @PrimaryKey val id: Int,
    val type: String,
    val amount: Int
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SavingsGoal::class,
            childColumns = arrayOf("savingsGoalId"),
            parentColumns = arrayOf("id")
        )]
)
data class Feed(
    @PrimaryKey val id: String,
    val savingsGoalId: Int,
    val type: String,
    @JsonAdapter(DateTimeTypeConverter::class) val timestamp: DateTime,
    val message: String,
    val amount: Float,
    val userId: Int,
    val savingsRuleId: Int
)

class Converters {

    @TypeConverter
    fun listToJson(value: List<Int>?): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String?): List<Int>? {
        if (value.isNullOrEmpty() || value == "null") return null
        val objects = Gson().fromJson(value, Array<Int>::class.java) as Array<Int>
        return objects.toList()
    }
}

class DateConverter {

    @TypeConverter
    fun dateToJson(date: DateTime): Long = date.millis

    @TypeConverter
    fun jsonToDate(value: Long): DateTime = DateTime(value)
}