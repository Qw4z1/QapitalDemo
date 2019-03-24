package se.devies.qapitaldemo.data


import android.annotation.SuppressLint
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class DemoStore(
    db: AppDatabase
) {

    private val dao = db.goalsDao()

    @SuppressLint("CheckResult")
    fun insertGoals(newList: List<SavingsGoal>) = dao.insertGoals(newList)

    val savingsGoals: Observable<List<SavingsGoal>>
        get() = dao.getAll()

    fun insertFeed(newList: List<Feed>) {
        dao.insertFeed(newList)
    }

    fun feedItems(userIds: List<Int>): Flowable<List<Feed>> = dao.getFeedForUsers(userIds)

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

    @Query("SELECT * from feed WHERE feed.userId IN (:users)")
    fun getFeedForUsers(users: List<Int>): Flowable<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeed(goals: List<Feed>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRules(goals: List<SavingsRule>)

}