package com.example.petminder.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.databinding.FragmentPetAddBinding
import com.example.petminder.databinding.FragmentPetInfoBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel

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

        setButtonListener(fragBinding)
        return root
    }

    fun setButtonListener(layout: FragmentPetInfoBinding) {
        layout.newExerBtn.setOnClickListener{
            val directions = PetInfoFragmentDirections.actionPetInfoFragmentToExerciseFragment(pet!!)
            findNavController().navigate(directions)
        }
        layout.newFeedBtn.setOnClickListener{
            val directions = PetInfoFragmentDirections.actionPetInfoFragmentToFeedFragment(pet!!)
            findNavController().navigate(directions)
        }
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