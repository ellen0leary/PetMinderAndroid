package com.example.petminder.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.R
import com.example.petminder.adapters.*
import com.example.petminder.databinding.FragmentPetInfoBinding
import com.example.petminder.firebase.FirebaseImageManager
import com.example.petminder.main.MainApp
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.feeds.FeedModel
import com.example.petminder.models.exercises.Location
import com.example.petminder.models.pets.PetModel
import com.example.petminder.ui.PetList.PetListFragment
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import timber.log.Timber

private const val  ARG_PET = "pet"
class PetInfoFragment : Fragment(), ExercsieListener, FeedListener {

    var pet: PetModel? = null
    lateinit var app : MainApp
    private var _fragBinding: FragmentPetInfoBinding? = null
    private val fragBinding get() = _fragBinding!!
    var tab= ""
    private lateinit var petInfoViewModel: PetInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        arguments?.let {
            pet = it.getParcelable(ARG_PET)
            Timber.i(pet.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentPetInfoBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fragBinding.recycler.setLayoutManager(LinearLayoutManager(activity))

        activity?.title = pet!!.name
        tab = "feed"
//        loadFeeds()
//        Picasso.get().load(pet!!.image).into(fragBinding.petImage)
        val ageText = "Age - " + pet?.age.toString() + " years"
        fragBinding.ageText.setText(ageText)

        val weightText = "Weight- " + pet?.weight.toString() + "Kg"
        fragBinding.weightText.setText(weightText)
        val layoutManager = LinearLayoutManager(activity)
        fragBinding.recycler.layoutManager = layoutManager

        petInfoViewModel = ViewModelProvider(this).get(PetInfoViewModel::class.java)
        petInfoViewModel.observableExerciseList.observe(viewLifecycleOwner, Observer {
                exercise: List<ExerciseModel> ->
            exercise?.let { render(exercise) }
        })
//        setImageView()
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
            val directions = PetInfoFragmentDirections.actionPetInfoFragmentToExerciseFragment(pet!!, ExerciseModel(), false, false, Location())
            findNavController().navigate(directions)
        }
        layout.newFeedBtn.setOnClickListener{
            val directions = PetInfoFragmentDirections.actionPetInfoFragmentToFeedFragment(pet!!, FeedModel(), false)
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
//        showFeeds(app.feeds.findByPet(pet!!.id))
        petInfoViewModel.observalePet.value = pet!!.uid
        petInfoViewModel.load()
        petInfoViewModel.observableFeedList.observe(viewLifecycleOwner, Observer {
                feed: List<FeedModel> ->
            feed?.let { renderFeed(feed) }
        })

    }

    fun showFeeds(feeds: List<FeedModel>){
        Timber.i(feeds.size.toString())
        fragBinding.recycler.adapter = FeedAdapter(feeds,this)
        fragBinding.recycler.adapter?.notifyDataSetChanged()
    }

    private fun loadExercises(){
//        showExercises(app.exercises.findByPet(pet!!.id))
        petInfoViewModel.observalePet.value = pet!!.uid
        petInfoViewModel.loadExe()
        petInfoViewModel.observableExerciseList.observe(viewLifecycleOwner, Observer {
                exercise: List<ExerciseModel> ->
            exercise?.let { render(exercise) }
        })
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
        val directions = PetInfoFragmentDirections.actionPetInfoFragmentToExerciseFragment(pet!!, exercise, true, false, Location())
        findNavController().navigate(directions)
    }

    override fun onFeedClick(feed: FeedModel) {
        val directions = PetInfoFragmentDirections.actionPetInfoFragmentToFeedFragment(pet!!, feed, true)
        findNavController().navigate(directions)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.getItemId()){
            R.id.udapte -> {
                findNavController().navigate(
                    PetInfoFragmentDirections.actionPetInfoFragmentToPetAddFragment(
                        true,
                        pet!!
                    )
                )
                return true
            }
            R.id.delete ->{
//                app.pets.deleteOne(pet!!.id)
                findNavController().navigateUp()
            }
        }
        return true
    }

    private fun render(exerciseList: List<ExerciseModel>){
        fragBinding.recycler.adapter = ExerciseAdapter(exerciseList, this)
        fragBinding.recycler.adapter?.notifyDataSetChanged()
        if(exerciseList.isEmpty()){
            fragBinding.recycler.visibility = View.GONE
            fragBinding.exerciseNotFound.visibility = View.VISIBLE
        }else {
            fragBinding.recycler.visibility = View.VISIBLE
            fragBinding.exerciseNotFound.visibility = View.GONE
        }
    }

    private fun renderFeed(feedList: List<FeedModel>){
        Timber.i(feedList.size.toString())
        fragBinding.recycler.adapter = FeedAdapter(feedList, this)
        fragBinding.recycler.adapter?.notifyDataSetChanged()
        if(feedList.isEmpty()){
            fragBinding.recycler.visibility = View.GONE
            fragBinding.exerciseNotFound.visibility = View.VISIBLE
        }else {
            fragBinding.recycler.visibility = View.VISIBLE
            fragBinding.exerciseNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()


        Timber.i("Uri - ${pet?.image}")
        if (pet?.image  != "")
        {
            Timber.i("DX Loading Existing imageUri")
            pet?.let {
                Timber.i("Uri - ${it.image}")
//                FirebaseImageManager.updatePetImage(
//                    it.uid,
//                    it.image.toUri(),
//                    fragBinding.petImage,false, it
//                )
                Picasso.get().load(it.image.toUri()).into(fragBinding.petImage)
            }
        }
        petInfoViewModel.observalePet.value = pet!!.uid
        petInfoViewModel.loadExe()
        petInfoViewModel.observableExerciseList.observe(viewLifecycleOwner, Observer {
                exercise: List<ExerciseModel> ->
            exercise?.let { render(exercise) }
        })
//            }
//        })
//        petInfoViewModel.observalePet = pet!!.uid
//        Timber.i(petInfoViewModel.observalePet.value)
//        petInfoViewModel.load()
    }



}