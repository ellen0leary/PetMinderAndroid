package com.example.petminder.ui.exceriseAdd

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petminder.R
import com.example.petminder.databinding.FragmentExerciseBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.exercises.Location
import com.example.petminder.models.pets.PetModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

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
    var location=  Location()
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
            pet = it.getParcelable(ARG_PET)!!
            edit = it.getBoolean(ARG_EDIT)
            if(edit) {
                exercise = it.getParcelable(ARG_EXERCISE)!!
                location.lat = exercise.lat
                location.lng = exercise.lng
                location.zoom = exercise.zoom
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

        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        if(edit) {
            fragBinding.exerciseType.setText(exercise.name)
            fragBinding.exerciseLength.setText(exercise.length.toString())
            fragBinding.placemarkLocation.setText(R.string.update_location_btn)
            fragBinding.startLoc.setText(location.toString())
            fragBinding.btnAdd.setText(R.string.update_exercise_btn)
        }
        fragBinding.startLoc.text = location.toString()

        setButtonListener(fragBinding)
        return root
    }

    fun setButtonListener(layout: FragmentExerciseBinding){
        layout.btnAdd.setOnClickListener {
            exercise.petId = pet.uid
            exercise.name = layout.exerciseType.text.toString()
            exercise.length = layout.exerciseLength.text.toString().toInt()
            exercise.lat = location.lat
            exercise.lng = location.lng
            exercise.zoom = location.zoom
            if (exercise.name.isEmpty()) {
                Snackbar.make(it, R.string.enter_exercise_name, Snackbar.LENGTH_SHORT).show()
            } else{
                if (edit) {
//                    app.exercises.update(exercise.copy())
                } else {
                    exerciseViewModel.addExercise(exercise.uid,exercise)
                }
                findNavController().navigateUp()
            }
        }
        layout.placemarkLocation.setOnClickListener {
            Timber.i("Button Clicked "+ location.lat.toString())

            val directions = ExerciseFragmentDirections.actionExerciseFragmentToMapsFragment(edit, location)
            findNavController().navigate(directions)
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
//        app.exercises.deleteOne(exercise.id)
        findNavController().navigateUp()
        return true
    }

    fun setExerciseLocation(loc: Location){
        edit = true
        location.lat = loc.lat
        location.lng = loc.lng
        location.zoom = loc.zoom
        Timber.i(edit.toString())
    }

}