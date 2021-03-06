package com.example.petminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.databinding.CardExerciseBinding
import com.example.petminder.models.exercises.ExerciseModel


interface ExercsieListener{
    fun onExerciseClick(exercise: ExerciseModel)
}
class ExerciseAdapter constructor(private var exercises: List<ExerciseModel>, private val listener: ExercsieListener) :
    RecyclerView.Adapter<ExerciseAdapter.MainHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.MainHolder {
        val binding = CardExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val exercise=  exercises[holder.adapterPosition]
        holder.bind(exercise, listener)
    }

    override fun getItemCount(): Int = exercises.size


    class MainHolder(private val binding: CardExerciseBinding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(exercise: ExerciseModel, listener: ExercsieListener){
//                    binding.feedTime.text = exercise.name
//                    binding.feedWeight.text = exercise.length.toString()
                    binding.exercise = exercise
                    binding.root.setOnClickListener{listener.onExerciseClick(exercise)}
                    binding.executePendingBindings()
                }
            }


}