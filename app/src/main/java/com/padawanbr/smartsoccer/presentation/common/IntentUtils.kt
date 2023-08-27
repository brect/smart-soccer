import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment

object ImagePickerUtils {

    const val PICK_IMAGE_REQUEST = 1

    fun openGallery(fragment: Fragment) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        fragment.startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    fun saveImageUri(context: Context, uri: Uri, keyToCache: String) {
        val sharedPreferences = context.getSharedPreferences("ImagePref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("imageUri:$keyToCache", uri.toString())
        editor.apply()
    }

    fun loadImageFromPreferences(context: Context, keyToCache: String, imageView: ImageView) {
        val sharedPreferences = context.getSharedPreferences("ImagePref", Context.MODE_PRIVATE)
        val imageUriString = sharedPreferences.getString("imageUri:$keyToCache", null)
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri)
        }
    }
}
