package com.padawanbr.smartsoccer.presentation.common

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionsUtil {

    const val WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE"
    const val REQUEST_EXTERNAL_STORAGE_CODE = 1
    const val REQUEST_PICK_IMAGE = 1

    val permissionsExternalStorage = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.CAMERA",

    )

    fun checkSelfPermission(context: Activity, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_DENIED

    fun checkPermissions(context: Activity, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    fun requestPermissionIfDanied(
        context: Activity,
        permission: String,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(context, arrayOf(permission), requestCode)
    }

    fun requestPermissionsIfDanied(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

}
