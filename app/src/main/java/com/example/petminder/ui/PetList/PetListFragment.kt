package com.example.petminder.ui.PetList

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.R
import com.example.petminder.adapters.PetAdapter
import com.example.petminder.adapters.PetListener
import com.example.petminder.databinding.FragmentPetListBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.pets.PetModel
import com.example.petminder.ui.auth.LoggedInViewModel
import com.example.petminder.utils.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

class PetListFragment : PetListener, Fragment() {

    lateinit var app : MainApp
    private var _fragBinding: FragmentPetListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var viewSearchBar = false
    private lateinit var petListViewModel: PetListViewModel
    lateinit var loader :AlertDialog
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

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
        loader = createLoader(requireActivity())
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
//        fragBinding.recyclerView.adapter = PetAdapter(donationList, this)
        showLoader(loader,"Downloading Pets")
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
            pets?.let { render(pets as ArrayList<PetModel>)
            hideLoader(loader)}
        })
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = PetListFragmentDirections.actionPetListFragmentToPetAddFragment(false, PetModel())
            findNavController().navigate(action)
        }

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Donation")
                val adapter = fragBinding.recyclerView.adapter as PetAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                petListViewModel.delete(
                    petListViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as PetModel).uid!!
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)


        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Timber.i(viewHolder.itemView.tag.toString())
                onPetClick(viewHolder.itemView.tag as PetModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
            itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)


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
        inflater.inflate(R.menu.menu_list, menu)

        val item = menu.findItem(R.id.toggleDonations) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleDonations: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleDonations.isChecked = false

        toggleDonations.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) petListViewModel.loadAll()
            else petListViewModel.load()
        }

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

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Pets")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser: FirebaseUser ->
            if (firebaseUser != null) {
                petListViewModel.liveFirebaseUser.value = firebaseUser
                petListViewModel.load()
            }
        })
//        hideLoader(loader)
    }


    private fun render(petList: ArrayList<PetModel>){
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