package se.devies.qapitaldemo.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DemoRepo(
    private val demoApi: DemoApi,
    private val demoStore: DemoStore
) {

    fun refreshGoals(): Completable =
        demoApi.getSavingsGoals()
            .flatMapCompletable { demoStore.insertGoals(it.savingsGoals) }

    fun observeGoals(): Observable<List<SavingsGoal>> =
        demoStore.savingsGoals

    fun observeGoal(goalId: Int): Observable<SavingsGoal> =
        demoStore.observeGoal(goalId)

    fun refreshFeed(goalId: Int) =
        demoApi.getFeed(goalId)
            .map { it.feed }
            .map { list -> list.map { it.copy(savingsGoalId = goalId) } }
            .flatMapCompletable(demoStore::insertFeed)
            .subscribeOn(Schedulers.io())
            .subscribe()

    fun observeFeed(goalId: Int): Observable<List<Feed>> =
        demoStore.observeFeed(goalId)
            .doOnSubscribe { refreshFeed(goalId) }

}