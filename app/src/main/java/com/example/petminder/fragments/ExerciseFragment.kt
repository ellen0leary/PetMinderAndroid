package com.example.petminder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petminder.databinding.FragmentExerciseBinding
import com.example.petminder.main.MainApp


class ExerciseFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentExerciseBinding? = null
    private val fragBinding get() = _fragBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentExerciseBinding.inflate(inflater,container,false)
        val root = fragBinding.root
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}