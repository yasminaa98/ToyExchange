package com.example.toyexchange.Common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.util.*

class PicturesConverter {
    companion object {
        fun managePicture(context: Context, key:String, selectedPhotoUri: Uri) : MultipartBody.Part? {
            val contentResolver = context.contentResolver
            val uniqueCode: String = UUID.randomUUID().toString()
            val file = File(context.cacheDir, uniqueCode + ".png")
            Log.i("file", file.toString())

            selectedPhotoUri?.let { uri ->
                val inputStream = contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                val imageFile = file.asRequestBody("image/png".toMediaTypeOrNull())
                Log.i("file", imageFile.toString())
                val photoPart = MultipartBody.Part.createFormData(key, file.name, imageFile)

                return photoPart
            }
            return null
        }

            suspend fun getRoundedBitmap(bitmap: Bitmap, size: Int): Bitmap {
                val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(output)
                val color = -0xbdbdbe
                val paint = Paint()
                val rect = Rect(0, 0, size, size)
                val radius = size / 2f
                paint.isAntiAlias = true
                canvas.drawARGB(0, 0, 0, 0)
                paint.color = color
                canvas.drawCircle(radius, radius, radius, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                val matrix = Matrix()
                matrix.setRectToRect(
                    RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()),
                    RectF(0f, 0f, size.toFloat(), size.toFloat()),
                    Matrix.ScaleToFit.CENTER
                )
                canvas.drawBitmap(bitmap, matrix, paint)
                return output
            }


        }
}