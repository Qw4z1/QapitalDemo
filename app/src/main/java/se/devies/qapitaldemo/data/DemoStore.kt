package se.devies.qapitaldemo.data


import android.annotation.SuppressLint
import android.util.Log
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

class DemoStore(
    db: AppDatabase
) {
    private val dao = db.goalsDao()

    @SuppressLint("CheckResult")
    fun insertGoals(newList: List<SavingsGoal>) = dao.insertGoals(newList)

    val savingsGoals: Observable<List<SavingsGoal>>
        get() = dao.getAll()

    fun insertFeed(newList: List<Feed>) = dao.insertFeed(newList)

    fun observeGoal(goalId: Int) = dao.observeGoal(goalId)

    fun observeFeed(goalId: Int): Observable<List<Feed>> = dao.getFeedForGoal(goalId)

}

@Database(
    exportSchema = false,
    entities = [
        SavingsGoal::class,
        SavingsRule::class,
        Feed::class
    ],
    version = 1
)
@TypeConverters(
    Converters::class,
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalsDao(): DemoDao
}

@Dao
interface DemoDao {

    @Query("SELECT * from savingsgoal")
    fun getAll(): Observable<List<SavingsGoal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoals(goals: List<SavingsGoal>): Completable

    @Query("SELECT * from feed WHERE feed.savingsGoalId = :goalId")
    fun getFeedForGoal(goalId: Int): Observable<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeed(goals: List<Feed>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRules(goals: List<SavingsRule>)

    @Query("SELECT * from savingsgoal WHERE id = :goalId")
    fun observeGoal(goalId: Int): Observable<SavingsGoal>

}