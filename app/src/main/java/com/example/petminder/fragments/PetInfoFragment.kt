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


class PetInfoFragment : Fragment() {

    lateinit var app : MainApp
    private var _fragBinding: FragmentPetInfoBinding? = null
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
        _fragBinding = FragmentPetInfoBinding.inflate(inflater, container, false)
        val root = fragBinding.root
//        val layoutManager = LinearLayoutManager(this)
        fragBinding.recycler.setLayoutManager(LinearLayoutManager(activity))

        return root

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PetListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}