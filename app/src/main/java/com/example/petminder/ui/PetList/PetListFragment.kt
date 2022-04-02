package com.example.petminder.ui.PetList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petminder.R
import com.example.petminder.adapters.PetAdapter
import com.example.petminder.adapters.PetListener
import com.example.petminder.databinding.FragmentPetListBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.pets.PetModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

class PetListFragment : PetListener, Fragment() {

    lateinit var app : MainApp
    private var _fragBinding: FragmentPetListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var viewSearchBar = false
    private lateinit var petListViewModel: PetListViewModel

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
//        fragBinding.recyclerView.adapter = PetAdapter(donationList, this)

        fragBinding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString());
            }
        })

        petListViewModel = ViewModelProvider(this).get(PetListViewModel::class.java)
        petListViewModel.observablePetList.observe(viewLifecycleOwner, Observer {
                pets: List<PetModel> ->
            pets?.let { render(pets) }
        })
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = PetListFragmentDirections.actionPetListFragmentToPetAddFragment(false, PetModel())
            findNavController().navigate(action)
        }


        return root
    }

    fun filter(s: String){
//        if(s==""){
////            fragBinding.recyclerView.adapter = PetAdapter(app.pets.findAll(), this)
//        }else {
//            val filteredPets = ArrayList<PetModel>();
//            for (pet in app.pets.findAll()) {
//                if (pet.toString().uppercase().contains(s.uppercase())) {
//                    filteredPets.add(pet)
//                }
//            }
//            fragBinding.recyclerView.adapter = PetAdapter(filteredPets, this)
//        }
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
        val direction =
            PetListFragmentDirections.actionPetListFragmentToPetInfoFragment(
                pet
            )
        findNavController().navigate(direction)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == R.id.item_search){
            viewSearchBar = !viewSearchBar
            if(viewSearchBar){
                fragBinding.searchBar.visibility = View.VISIBLE
            } else {
                fragBinding.searchBar.visibility = View.GONE
            }
            return true
        } else {
            return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
        }
    }

    private fun render(petList: List<PetModel>){
        fragBinding.recyclerView.adapter = PetAdapter(petList, this)
        if(petList.isEmpty()){
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.petsNotFound.visibility = View.VISIBLE
        }else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.petsNotFound.visibility = View.GONE
        }
    }

}