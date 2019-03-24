package se.devies.qapitaldemo.data

import io.reactivex.Completable
import io.reactivex.Observable

class DemoRepo(
    private val demoApi: DemoApi,
    private val demoStore: DemoStore
) {

    fun refreshGoals(): Completable =
        demoApi.getSavingsGoals()
            .flatMapCompletable { demoStore.insertGoals(it.savingsGoals) }

    fun observeGoals(): Observable<List<SavingsGoal>> =
        demoStore.savingsGoals

}