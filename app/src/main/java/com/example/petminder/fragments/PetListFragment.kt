package com.example.petminder.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.R
import com.example.petminder.adapters.PetAdapter
import com.example.petminder.adapters.PetListener
import com.example.petminder.databinding.FragmentPetListBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.google.android.material.internal.TextWatcherAdapter
import timber.log.Timber

class PetListFragment : PetListener, Fragment() {

    lateinit var app : MainApp
    private var _fragBinding: FragmentPetListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var viewSearchBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentPetListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fragBinding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        fragBinding.recyclerView.adapter = PetAdapter(app.pets.findAll(), this)
        fragBinding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString());
            }
        })


        return root
    }

    fun filter(s: String){
        if(s==""){
            fragBinding.recyclerView.adapter = PetAdapter(app.pets.findAll(), this)
        }else {
            val filteredPets = ArrayList<PetModel>();
            for (pet in app.pets.findAll()) {
                if (pet.toString().uppercase().contains(s.uppercase())) {
                    filteredPets.add(pet)
                }
            }
            fragBinding.recyclerView.adapter = PetAdapter(filteredPets, this)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            PetListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onPetClick(pet: PetModel) {
        Timber.i("Clicked on pet")
        val direction = PetListFragmentDirections.actionPetListFragmentToPetInfoFragment(pet)
        findNavController().navigate(direction)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewSearchBar = !viewSearchBar
        if(viewSearchBar){
            fragBinding.searchBar.visibility = View.VISIBLE
        } else {
            fragBinding.searchBar.visibility = View.GONE
        }
        return true
    }


}