package se.devies.qapitaldemo.data

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class DemoStore {
    private var goals = BehaviorSubject.create<List<SavingsGoal>>()

    fun upsertGoals(newList: List<SavingsGoal>) {
        goals.onNext(newList)
    }

    val savingsGoals: Observable<List<SavingsGoal>>
        get() = goals

}