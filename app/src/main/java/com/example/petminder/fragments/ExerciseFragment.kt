package com.example.petminder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.R
import com.example.petminder.databinding.FragmentExerciseBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar

private const val  ARG_PET = "pet"
private const val ARG_EXERCISE = "exercise"
class ExerciseFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentExerciseBinding? = null
    private val fragBinding get() = _fragBinding!!
    var pet : PetModel? = null
    var exercise = ExerciseModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
            pet = it.getParcelable(ARG_PET)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentExerciseBinding.inflate(inflater,container,false)
        val root = fragBinding.root


        setButtonListener(fragBinding)
        return root
    }

    fun setButtonListener(layout: FragmentExerciseBinding){
        layout.btnAdd.setOnClickListener(){
            exercise.petId = pet!!.id
            exercise.name = layout.exerciseType.text.toString()
            exercise.length = layout.exerciseLength.text.toString().toInt()
//            exercise.lat = location.lat
//            exercise.lng = location.lng
//            exercise.zoom = location.zoom
            if (exercise.name.isEmpty()) {
                Snackbar.make(it, R.string.enter_exercise_name, Snackbar.LENGTH_SHORT).show()
            } else{
//                if (edit) {
//                    app.exercises.update(exercise.copy())
//                } else {
                    app.exercises.create(exercise.copy())
//                }
                requireActivity().supportFragmentManager.popBackStack()
            }

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}