package com.example.petminder.ui.feedAdd

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petminder.R
import com.example.petminder.databinding.FragmentFeedBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.feeds.FeedModel
import com.example.petminder.models.pets.PetModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

private const val ARG_PET = "pet"
private const val ARG_FEED = "feed"
private const val ARG_EDIT = "edit"
class FeedFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentFeedBinding? = null
    private val fragBinding get() = _fragBinding!!
    var pet= PetModel()
    var feed = FeedModel()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
            pet = it.getParcelable(ARG_PET)!!
            edit = it.getBoolean(ARG_EDIT)
            if(edit){
                feed = it.getParcelable(ARG_FEED)!!
            }
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


        if(edit){
            fragBinding.spinner.setSelection(
                (fragBinding.spinner.getAdapter() as ArrayAdapter<String?>).getPosition(
                    feed.time
                )
            )

            fragBinding.spinner.selectedItem.toString()
            fragBinding.feedWeight.setText(feed.weigth.toString())
            fragBinding.btnAdd.setText(R.string.update_feed_btn)
        }
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
                if (edit) {
                    app.feeds.update(feed.copy())
                } else {
                    app.feeds.create(feed.copy())
                }
                findNavController().navigateUp()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(edit) {
            inflater.inflate(R.menu.menu_remove, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        app.feeds.deleteOne(feed.id)
        findNavController().navigateUp()
        return true
    }
}