package com.example.toyexchange.Common

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class PicturesConverter {
    companion object {
       suspend fun sendImage(selectedImage: ImageView):String? = withContext(Dispatchers.Default)  {
           val drawable = selectedImage.drawable ?: return@withContext null
            val selectedBitmap = (selectedImage.drawable as BitmapDrawable).bitmap
            val outputStream = ByteArrayOutputStream()
            selectedBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byteArray = outputStream.toByteArray()

            val encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT)
            Log.i("image decoded", encodedString)
            // Now you can send the encodedString to the backend server
           return@withContext encodedString

        }
        suspend fun base64ToBitmap(base64String: String?): Bitmap?= withContext(Dispatchers.Default)  {
            if (base64String == null) {
                return@withContext null
            }
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(decodedBytes)
            return@withContext try {
                BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                null
            }
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
            matrix.setRectToRect(RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()), RectF(0f, 0f, size.toFloat(), size.toFloat()), Matrix.ScaleToFit.CENTER)
            canvas.drawBitmap(bitmap, matrix, paint)
            return output
        }



    }
}