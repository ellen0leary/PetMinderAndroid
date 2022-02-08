package com.example.petminder.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.R
import com.example.petminder.adapters.PetAdapter
import com.example.petminder.adapters.PetListener
import com.example.petminder.databinding.FragmentPetListBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import timber.log.Timber

class PetListFragment : PetListener, Fragment() {

    lateinit var app : MainApp
    private var _fragBinding: FragmentPetListBinding? = null
    private val fragBinding get() = _fragBinding!!

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
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = PetAdapter(app.pets.findAll(), this)


        return root
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
//        TODO("Add Pet click")
        val direction = PetListFragmentDirections.actionPetListFragmentToPetInfoFragment()
        findNavController().navigate(direction)

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