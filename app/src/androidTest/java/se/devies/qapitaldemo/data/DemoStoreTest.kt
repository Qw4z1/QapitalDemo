package se.devies.qapitaldemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DemoStoreTest {
    private lateinit var dao: DemoDao
    private lateinit var db: AppDatabase

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.goalsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertGoals() {
        val goals = createGoals()
        dao.insertGoals(goals).test().assertComplete()
        val testObserver = dao.getAll().test()
        testObserver.assertValue(goals)
        testObserver.assertValueCount(1)
    }

    @Test
    fun insertGoals_withNullUsers() {
        val goals = listOf(goal3)
        dao.insertGoals(goals).test().assertComplete()
        val testObserver = dao.getAll().test()
        testObserver.assertValue(goals)
        testObserver.assertValueCount(1)
    }
}

private fun createGoals() =
    listOf(
        SavingsGoal(
            id = 1,
            created = listOf(2019, 3, 19),
            name = "Test Goal 1",
            currentBalance = 4.5f,
            connectedUsers = listOf(1),
            goalImageURL = "url.com",
            status = "active",
            targetAmount = 5000f,
            userId = 1
        ),
        SavingsGoal(
            id = 2,
            created = listOf(2019, 3, 20),
            name = "Test Goal 2",
            currentBalance = 4.5f,
            connectedUsers = listOf(1, 2),
            goalImageURL = "url.com",
            status = "active",
            targetAmount = 5000f,
            userId = 2
        )
    )

private val goal3 = SavingsGoal(
    id = 3,
    created = listOf(2019, 3, 20),
    name = "Test Goal 2",
    currentBalance = 4.5f,
    connectedUsers = null,
    goalImageURL = "url.com",
    status = "active",
    targetAmount = 5000f,
    userId = 1
)