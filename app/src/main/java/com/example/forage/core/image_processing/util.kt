package com.example.forage.core.image_processing

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getImageFromInternalStorageLauncher(useBitmap: (Bitmap) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        uri?.let {
            context.contentResolver.openInputStream(it).use { stream ->
                useBitmap(BitmapFactory.decodeStream(stream))
            }
        }
    }
}