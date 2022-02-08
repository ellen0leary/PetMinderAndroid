package com.example.petminder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.petminder.R
import com.example.petminder.databinding.FragmentPetAddBinding
import com.example.petminder.helpers.showImagePicker
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class PetAddFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentPetAddBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private val pet = PetModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentPetAddBinding.inflate(inflater, container, false)
        val root = fragBinding.root
//        activity?.title = getString(R.string.app_name)

        setButtonListener(fragBinding)
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
                app.pets.create(pet.copy())
            }
        }
        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            Timber.i("cling button")
        }


    }
    companion object {
        @JvmStatic
        fun newInstance() =
            PetAddFragment().apply {
                arguments = Bundle().apply {}
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