package com.example.petminder.firebase.db

import androidx.lifecycle.MutableLiveData
import com.example.petminder.models.pets.PetModel
import com.example.petminder.models.pets.PetStore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import timber.log.Timber

object PetDBManager: PetStore {
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(petList: MutableLiveData<List<PetModel>>) {
        database.child("pets")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Donation error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<PetModel>()
                    val children = snapshot.children
                    children.forEach {
                        val pet = it.getValue(PetModel::class.java)
                        localList.add(pet!!)
                    }
                    database.child("donations")
                        .removeEventListener(this)

                    petList.value = localList
                }
            })
    }

    override fun findAll(userid: String, petList: MutableLiveData<List<PetModel>>) {
        Timber.i("Firebase DB Reference : $ , findAll")
        database.child("user-pets").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Donation error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<PetModel>()
                    val children = snapshot.children
                    children.forEach {
                        val donation = it.getValue(PetModel::class.java)
                        localList.add(donation!!)
                    }
                    database.child("user-pets").child(userid)
                        .removeEventListener(this)

                    petList.value = localList
                }
            })
    }

    override fun findOne(userid: String,petid: String, pet: MutableLiveData<PetModel>) {
        database.child("user-pets").child(userid)
            .child(petid).get().addOnSuccessListener {
                pet.value = it.getValue(PetModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error Getting data $it")
            }
    }

    override fun create(firbaseUser: MutableLiveData<FirebaseUser>, pet: PetModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firbaseUser.value!!.uid
        val key = database.child("pets").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        pet.uid = key
        val petValues = pet.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/pets/$key"] = petValues
        childAdd["/user-pets/$uid/$key"] = petValues

        database.updateChildren(childAdd)
    }

    override fun update(userid: String, petid: String, pet: PetModel) {
        val petValue = pet.toMap()
        val childUpdate: MutableMap<String, Any?> = HashMap()
        Timber.i("pets/$petid")
        Timber.i("/user-pets/$userid/$petid")
        childUpdate["pets/$petid"] = petValue
        childUpdate["user-pets/$userid/$petid"] = petValue
        database.updateChildren(childUpdate)
    }

    override fun deleteOne(userid: String, petid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        Timber.i("pets/$petid")
        Timber.i("/user-pets/$userid/$petid")
        childDelete["pets/$petid"] = null
        childDelete["/user-pets/$userid/$petid"] = null

        database.updateChildren(childDelete)
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userDonations = database.child("user-donations").child(userid)
        val allDonations = database.child("donations")

        userDonations.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all donations that match 'it'
                        val donation = it.getValue(PetModel::class.java)
                        allDonations.child(donation!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }

    fun updatePetImageRef(petid: String,imageUri: String, userid: String) {

        val userPets = database.child("user-pets").child(userid).child(petid)
        val allPets = database.child("pets")

        allPets.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("image").setValue(imageUri)
                        //Update all donations that match 'it'
                        val donation = it.getValue(PetModel::class.java)
                        userPets
                            .child("image").setValue(imageUri)
                    }
                }
            })
    }

}