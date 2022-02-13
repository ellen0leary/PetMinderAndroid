package com.example.petminder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.petminder.R
import com.example.petminder.databinding.FragmentPetAddBinding
import com.example.petminder.helpers.showImagePicker
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.Location
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber

private const val ARG_PET = "pet"
private const val ARG_EDIT = "edit"
class PetAddFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentPetAddBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var pet = PetModel()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
            edit = it.getBoolean(ARG_EDIT)
            if(edit) {
                pet = it.getParcelable(ARG_PET)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentPetAddBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        if(edit){
            fragBinding.petName.setText(pet.name)
            fragBinding.petWeight.setText(pet.weight.toString())
            fragBinding.petAge.setText(pet.age.toString())
            fragBinding.btnAdd.setText(R.string.save_pet)
            fragBinding.chooseImage.setText(R.string.update_image)
            Picasso.get().load(pet.image).into(fragBinding.petImage)
        }
        setButtonListener(fragBinding)
        registerImagePickerCallback()
        return root
    }


    fun setButtonListener(layout: FragmentPetAddBinding) {
        layout.btnAdd.setOnClickListener(){
            Timber.i("cling button")
            pet.name = layout.petName.text.toString()
            pet.weight = layout.petWeight.text.toString().toFloat()
            pet.age = layout.petAge.text.toString().toInt()
            if (pet.name.isEmpty()) {
                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else {
                if(edit){
                    app.pets.update(pet.copy())
                    findNavController().navigateUp()
                } else {
                    app.pets.create(pet.copy())
                    val directions = PetAddFragmentDirections.actionPetAddFragmentToPetListFragment()
                    findNavController().navigate(directions)
                }
            }
        }
        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            Timber.i("click button")
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            PetAddFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            pet.image = result.data!!.data!!
                            Picasso.get()
                                .load(pet.image)
                                .into(fragBinding.petImage)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

}