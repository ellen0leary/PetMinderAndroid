package com.example.petminder.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.adapters.ExerciseAdapter
import com.example.petminder.adapters.ExercsieListener
import com.example.petminder.adapters.FeedAdapter
import com.example.petminder.adapters.FeedListener
import com.example.petminder.databinding.FragmentPetAddBinding
import com.example.petminder.databinding.FragmentPetInfoBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import timber.log.Timber

private const val  ARG_PET = "pet"
class PetInfoFragment : Fragment(), ExercsieListener, FeedListener {

    var pet: PetModel? = null
    lateinit var app : MainApp
    private var _fragBinding: FragmentPetInfoBinding? = null
    private val fragBinding get() = _fragBinding!!
    var tab= ""

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

        tab = "feed"
        loadFeeds()
//        Picasso.get().load(pet.image).into(binding.petImage)
        val ageText = "Age - " + pet?.age.toString() + " years"
        fragBinding.ageText.setText(ageText)

        val weightText = "Weight- " + pet?.weight.toString() + "Kg"
        fragBinding.weightText.setText(weightText)

        val layoutManager = LinearLayoutManager(activity)
        fragBinding.recycler.layoutManager = layoutManager
        setButtonListener(fragBinding)
        return root
    }

    private fun changeTab(){
        if(tab=="feed") {
            Timber.i("loading Exercises")
            loadExercises()
            tab="exer"
        }else if(tab=="exer"){
            Timber.i("loading Feeds")
            loadFeeds()
            tab="feed"
        }
    }

    fun setButtonListener(layout: FragmentPetInfoBinding) {
        layout.newExerBtn.setOnClickListener{
            val directions = PetInfoFragmentDirections.actionPetInfoFragmentToExerciseFragment(pet!!, ExerciseModel(), false)
            findNavController().navigate(directions)
        }
        layout.newFeedBtn.setOnClickListener{
            val directions = PetInfoFragmentDirections.actionPetInfoFragmentToFeedFragment(pet!!)
            findNavController().navigate(directions)
        }
        layout.toggleButton.setOnClickListener{
            setTab()
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

    private fun loadFeeds(){
        showFeeds(app.feeds.findByPet(pet!!.id))
    }

    fun showFeeds(feeds: List<FeedModel>){
        Timber.i(feeds.size.toString())
        fragBinding.recycler.adapter = FeedAdapter(feeds,this)
        fragBinding.recycler.adapter?.notifyDataSetChanged()
    }

    private fun loadExercises(){
        showExercises(app.exercises.findByPet(pet!!.id))
    }

    fun showExercises(exercise: List<ExerciseModel>){
        Timber.i(exercise.size.toString())
        fragBinding.recycler.adapter = ExerciseAdapter(exercise,this)
        fragBinding.recycler.adapter?.notifyDataSetChanged()
    }

    public fun setTab(){
        changeTab()
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        val directions = PetInfoFragmentDirections.actionPetInfoFragmentToExerciseFragment(pet!!, exercise, true)
        findNavController().navigate(directions)
    }

    override fun onFeedClick(feed: FeedModel) {
        TODO("Not yet implemented")
    }

}