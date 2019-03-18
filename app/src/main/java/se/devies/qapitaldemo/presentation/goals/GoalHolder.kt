package se.devies.qapitaldemo.presentation.goals

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import se.devies.qapitaldemo.data.SavingsGoal

class GoalHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(goal: SavingsGoal) {
        binding.setVariable(BR.goal, goal)
        binding.executePendingBindings()
    }
}