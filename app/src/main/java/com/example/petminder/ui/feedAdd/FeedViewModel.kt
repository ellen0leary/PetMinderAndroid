package com.example.petminder.ui.feedAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.firebase.db.FeedDBManager
import com.example.petminder.models.feeds.FeedModel
import timber.log.Timber
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.IDN

class FeedViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatis: LiveData<Boolean>
        get() = status

    fun addFeed(petId: String,feed: FeedModel){
        status.value = try{
//            FeedManager.create(feed)
            FeedDBManager.create(petId,feed)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }


    fun updateFeed(petId: String,feed: FeedModel){
        status.value = try{
            FeedDBManager.update(petId,feed)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }


    fun delete(petId: String, feedId: String) {
        try {
            FeedDBManager.deleteOne(petId,feedId)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}