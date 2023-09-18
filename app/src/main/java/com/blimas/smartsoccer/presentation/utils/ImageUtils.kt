package com.blimas.smartsoccer.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.provider.MediaStore
import android.util.LruCache
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


//    https://medium.com/@atifsayings/get-save-bitmap-from-any-ui-android-studio-kotlin-cd9ea422eb7c
//    https://stackoverflow.com/questions/7661875/how-to-use-share-image-using-sharing-intent-to-share-images-in-android
//    https://droidlytics.wordpress.com/2020/08/04/use-fileprovider-to-share-image-from-recyclerview/
//    https://www.google.com/search?q=RecyclerViewScreenshot&rlz=1C5CHFA_enBR941BR941&oq=RecyclerViewScreenshot&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIHCAEQABiiBDIHCAIQABiiBDIHCAMQABiiBNIBCDEwNTVqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8


object ImageUtils {

    fun createBitmapFromView(view: View, width: Int, height: Int): Bitmap {
        if (width > 0 && height > 0) {
            view.measure(
                View.MeasureSpec.makeMeasureSpec(
                    width, View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                    height, View.MeasureSpec.UNSPECIFIED
                )
            )
        }
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache(true)
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    fun getScreenshotFromRecyclerView(view: RecyclerView): Bitmap {
        val adapter = view.adapter
        var bigBitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 2
            val bitmapCache: LruCache<String, Bitmap> = LruCache(cacheSize)
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                holder.itemView.layout(
                    0,
                    0,
                    holder.itemView.measuredWidth,
                    holder.itemView.measuredHeight
                )
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {
                    bitmapCache.put(i.toString(), drawingCache)
                }

                height += holder.itemView.measuredHeight
            }
            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)

            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)

            for (i in 0 until size) {
                val bitmap: Bitmap = bitmapCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }

        return bigBitmap
    }

    fun getBitmapsFromRecyclerView(view: RecyclerView): List<Bitmap> {
        val adapter = view.adapter
        val bitmaps = mutableListOf<Bitmap>()

        if (adapter != null) {
            val size = adapter.itemCount
            val paint = Paint()

            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)

                val itemView = holder.itemView
                itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                itemView.layout(
                    0,
                    0,
                    itemView.measuredWidth,
                    itemView.measuredHeight
                )

                itemView.isDrawingCacheEnabled = true
                itemView.buildDrawingCache(true)

                val drawingCache = itemView.drawingCache
                if (drawingCache != null) {
                    bitmaps.add(Bitmap.createBitmap(drawingCache))
                }

                itemView.isDrawingCacheEnabled = false
            }
        }

        return bitmaps
    }

    fun saveBitmapsToStorage(context: Context, bitmaps: List<Bitmap>): List<Uri> {
        val uriList = mutableListOf<Uri>()

        for ((index, bitmap) in bitmaps.withIndex()) {
            val imagefolder = File(context?.cacheDir, "images")
            imagefolder.mkdirs()

            val file = File(imagefolder, "shared_image_$index.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                context,
                "com.anni.shareimage.fileprovider",
                file
            )
            uriList.add(uri)
        }

        return uriList
    }

    fun saveBitmapsToGallery(context: Context, bitmaps: List<Bitmap>, filePrefixName: String = UUID.randomUUID().toString()): List<Uri> {
        val uriList = mutableListOf<Uri>()

        for ((index, bitmap) in bitmaps.withIndex()) {
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "shared_image_${filePrefixName}_${index}.png")
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            }

            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                val outputStream = contentResolver.openOutputStream(it)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                uriList.add(uri)
            }
        }

        return uriList
    }

    fun shareImageAndText(context: Context, bitmap: Bitmap) {
        val uri: Uri? = getImageToShare(context, bitmap)
        val intent = Intent(Intent.ACTION_SEND)

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")

        // setting type to image
        intent.type = "image/png"

        context?.grantUriPermission(
            "com.package.name.of.target.app",  // Substitua pelo pacote do aplicativo de destino
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        // calling startactivity() to share
        context.startActivity(Intent.createChooser(intent, "Share Via"))
    }

    fun shareBitmapList(context: Context, bitmaps: List<Bitmap>) {
        val uris = saveBitmapsToGallery(context, bitmaps)
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
            type = "image/*"
            putExtra(Intent.EXTRA_TEXT, "Sharing Images")
            putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        }

        context.grantUriPermission(
            "com.package.name.of.target.app", // Substitua pelo pacote do aplicativo de destino
            uris[0], // Use uma das URIs da lista, dependendo do seu requisito
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        context.startActivity(Intent.createChooser(intent, "Share Via"))
    }


    private fun getImageToShare(context: Context, bitmap: Bitmap): Uri? {
        val imageFolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imageFolder.mkdirs()
            val file = File(imageFolder, "shared_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(
                context,
                "com.anni.shareimage.fileprovider",
                file
            )
        } catch (e: Exception) {
            Toast.makeText(context, "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }
}
