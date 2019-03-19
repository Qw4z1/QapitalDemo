package se.devies.qapitaldemo.presentation.goals

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class GoalHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(goal: GoalViewModel) {
        binding.setVariable(BR.goal, goal)
        binding.executePendingBindings()
    }
}