package com.blimas.smartsoccer.framework.db

import android.util.Log
import androidx.room.RoomDatabase

class LoggingQueryCallback : RoomDatabase.QueryCallback {

    override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
        Log.i("Executed SQL Query:", " $sqlQuery with bindArgs: $bindArgs")
    }
}