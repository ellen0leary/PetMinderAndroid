package com.example.petminder.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.example.petminder.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


const val  PET_JSON_FILE = "pets.json"
val petGsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val PetlistType = object : TypeToken<ArrayList<PetModel>>() {}.type

fun generateRandomId() : Long{
    return  Random().nextLong()
}
class PetJSONStore(private val context: Context) : PetStore{
    var pets = mutableListOf<PetModel>()


    init {
        if (exists(context, PET_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PetModel> {
        logAll()
        return pets
    }

    override fun create(pet: PetModel) {
        pet.id = generateRandomId()
        pets.add(pet)
        serialize()
    }


    override fun update(pet: PetModel) {
        // todo
        var foundPet: PetModel? = pets.find {p-> p.id == pet.id}
        if(foundPet!= null){
            foundPet.name = pet.name
            foundPet.weight = pet.weight
            foundPet.age = pet.age
            foundPet.image = pet.image
//            logAll()
            serialize()
        }
    }

    private fun serialize() {
        val jsonString = petGsonBuilder.toJson(pets, PetlistType)
        write(context, PET_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, PET_JSON_FILE)
        pets = petGsonBuilder.fromJson(jsonString, PetlistType)
    }

    private fun logAll() {
        pets.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}