package com.example.petminder.ui.PetAdd

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.petminder.R
import com.example.petminder.databinding.FragmentPetAddBinding
import com.example.petminder.helpers.showImagePicker
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.security.acl.Owner

private const val ARG_PET = "pet"
private const val ARG_EDIT = "edit"
class PetAddFragment : Fragment() {

//    lateinit var app: MainApp
    private var _fragBinding: FragmentPetAddBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var pet = PetModel()
    private lateinit var petAddViewModel: PetAddViewModel
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        app = activity?.application as MainApp
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
        activity?.title = getString(R.string.app_name)

        val spinner = fragBinding.petType
        val adapter = this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.petType, android.R.layout.simple_spinner_item
            )
        }
        if (adapter != null) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner.adapter = adapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(spinner.selectedItem.toString().equals("Other")){
                    fragBinding.petTypeText.visibility = View.VISIBLE
                } else {
                    fragBinding.petTypeText.visibility = View.GONE
                }
                fragBinding.petTypeText.setText(spinner.selectedItem.toString())

            }

        }
        if(edit){
            fragBinding.petName.setText(pet.name)
            fragBinding.petWeight.setText(pet.weight.toString())
            fragBinding.petAge.setText(pet.age.toString())
            fragBinding.petTypeText.visibility = View.VISIBLE
            fragBinding.petTypeText.setText(pet.type)
            fragBinding.btnAdd.setText(R.string.save_pet)
            fragBinding.chooseImage.setText(R.string.update_image)
            Picasso.get().load(pet.image).into(fragBinding.petImage)
        }

        petAddViewModel = ViewModelProvider(this).get(PetAddViewModel::class.java)
        petAddViewModel.observableStatis.observe(viewLifecycleOwner, Observer {
                status: Boolean ->
            status?.let { render(status) }
        })

        setButtonListener(fragBinding)
        registerImagePickerCallback()
        return root
    }


    private fun render(status:Boolean){
        when(status){
            true ->{
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.petError), Toast.LENGTH_LONG).show()
        }
    }
    fun setButtonListener(layout: FragmentPetAddBinding) {
        layout.btnAdd.setOnClickListener(){
            Timber.i("cling button")
            val name = layout.petName.text.toString()
            val weight = layout.petWeight.text.toString().toFloat()
            val age = layout.petAge.text.toString().toInt()
            val type = layout.petTypeText.toString()
            if (name.isEmpty()) {
                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else {
                petAddViewModel.addPet(PetModel(type = type, name = name, weight = weight, age = age))
//                if(edit){
//                    app.pets.update(pet.copy())
//                    findNavController().navigateUp()
//                } else {
//                    app.pets.create(pet.copy())
//                    val directions = PetAddFragmentDirections.actionPetAddFragmentToPetListFragment()
//                    findNavController().navigate(directions)
//                }
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
        Timber.i(item.getItemId().toString())
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

}