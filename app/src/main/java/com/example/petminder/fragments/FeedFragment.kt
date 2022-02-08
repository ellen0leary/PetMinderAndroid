package com.example.petminder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petminder.databinding.FragmentFeedBinding
import com.example.petminder.main.MainApp


class FeedFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentFeedBinding? = null
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
        _fragBinding = FragmentFeedBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        return  root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedFragment().apply {
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