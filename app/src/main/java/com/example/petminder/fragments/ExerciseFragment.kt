package com.example.petminder.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.petminder.R
import com.example.petminder.databinding.FragmentExerciseBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar

private const val  ARG_PET = "pet"
private const val ARG_EXERCISE = "exercise"
private const val ARG_EDIT = "edit"
class ExerciseFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentExerciseBinding? = null
    private val fragBinding get() = _fragBinding!!
    var pet = PetModel()
    var exercise=  ExerciseModel()
    var edit =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
            pet = it.getParcelable(ARG_PET)!!
            edit = it.getBoolean(ARG_EDIT)
            if(edit) {
                exercise = it.getParcelable(ARG_EXERCISE)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentExerciseBinding.inflate(inflater,container,false)
        val root = fragBinding.root

        if(edit) {
            fragBinding.exerciseType.setText(exercise.name)
            fragBinding.exerciseLength.setText(exercise.length.toString())
            fragBinding.placemarkLocation.setText(R.string.update_location_btn)
            fragBinding.btnAdd.setText(R.string.update_exercise_btn)
        }

        setButtonListener(fragBinding)
        return root
    }

    fun setButtonListener(layout: FragmentExerciseBinding){
        layout.btnAdd.setOnClickListener(){
            exercise.petId = pet.id
            exercise.name = layout.exerciseType.text.toString()
            exercise.length = layout.exerciseLength.text.toString().toInt()
//            exercise.lat = location.lat
//            exercise.lng = location.lng
//            exercise.zoom = location.zoom
            if (exercise.name.isEmpty()) {
                Snackbar.make(it, R.string.enter_exercise_name, Snackbar.LENGTH_SHORT).show()
            } else{
                if (edit) {
                    app.exercises.update(exercise.copy())
                } else {
                    app.exercises.create(exercise.copy())
                }
                findNavController().navigateUp()
            }

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(pet: PetModel, exercise: ExerciseModel) =
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(edit) {
            inflater.inflate(R.menu.menu_remove, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        app.exercises.deleteOne(exercise.id)
        findNavController().navigateUp()
        return true
    }
}