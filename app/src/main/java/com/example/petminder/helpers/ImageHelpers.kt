package com.example.petminder.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.petminder.R


fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_pet_image.toString())
    intentLauncher.launch(chooseFile)
}