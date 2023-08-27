package com.padawanbr.smartsoccer.presentation.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


open class PermissionsUtil {

    companion object {
        const val WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE"
        const val REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1
        const val REQUEST_PICK_IMAGE = 1
    }

    fun checkSelfPermission(context: Activity, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_DENIED

    fun requestPermissionIfDanied(
        context: Activity,
        permission: String,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(context, arrayOf(permission), requestCode)
    }
}
