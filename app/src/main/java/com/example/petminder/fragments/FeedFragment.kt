package com.example.petminder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petminder.R
import com.example.petminder.databinding.FragmentFeedBinding
import com.example.petminder.databinding.FragmentPetAddBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

private const val ARG_PET = "pet"
private const val ARG_FEED = "feed"
class FeedFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentFeedBinding? = null
    private val fragBinding get() = _fragBinding!!
    var pet: PetModel? = null
    var feed = FeedModel()

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
        _fragBinding = FragmentFeedBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        val spinner = fragBinding.spinner
        val adapter = this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.timeOfDay, android.R.layout.simple_spinner_item
            )
        }
        if (adapter != null) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner.adapter = adapter
        setButtonListener(fragBinding)
        return  root
    }

    fun setButtonListener(layout: FragmentFeedBinding) {
        layout.btnAdd.setOnClickListener {
            Timber.i("CLicking btn")
            feed.petId = pet!!.id
            feed.time = layout.spinner.selectedItem.toString()
            feed.weigth = layout.feedWeight.text.toString().toFloat()
            if (feed.time.isEmpty()) {
                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else{
//                if (edit) {
//                    app.feeds.update(feed.copy())
//                } else {
                    app.feeds.create(feed.copy())
                requireActivity().supportFragmentManager.popBackStack()
//                }
            }
        }
    }

    private fun backToPetInfo(){
        val directions = FeedFragmentDirections.actionFeedFragmentToPetInfoFragment(pet!!)
        findNavController().navigate(directions)
    }
    companion object {
        @JvmStatic
        fun newInstance(pet: PetModel, feed: FeedModel) =
            FeedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PET, pet)
                    putParcelable(ARG_FEED, feed)
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