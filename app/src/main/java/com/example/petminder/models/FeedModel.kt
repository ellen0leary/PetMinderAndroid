package com.example.petminder.models

data class FeedModel (var id: Long=0,
                      var petId: Long =0,
                      var time: String="",
                      var weigth: Float=0.0f,
)