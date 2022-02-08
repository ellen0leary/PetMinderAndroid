package com.example.petminder.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.R
import com.example.petminder.databinding.FragmentPetInfoBinding
import com.example.petminder.databinding.FragmentPetListBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.squareup.picasso.Picasso

private const val  ARG_PET = "pet"
class PetInfoFragment : Fragment() {

    var pet: PetModel? = null
    lateinit var app : MainApp
    private var _fragBinding: FragmentPetInfoBinding? = null
    private val fragBinding get() = _fragBinding!!

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
        _fragBinding = FragmentPetInfoBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fragBinding.recycler.setLayoutManager(LinearLayoutManager(activity))

//        Picasso.get().load(pet.image).into(binding.petImage)
        val ageText = "Age - " + pet?.age.toString() + " years"
        fragBinding.ageText.setText(ageText)

        val weightText = "Weight- " + pet?.weight.toString() + "Kg"
        fragBinding.weightText.setText(weightText)
        return root

    }

    companion object {
        @JvmStatic
        fun newInstance(pet: PetModel) =
            PetListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PET, pet)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}