package com.padawanbr.smartsoccer.framework.local

import com.padawanbr.smartsoccer.core.data.repository.TeamLocalDataSource
import com.padawanbr.smartsoccer.framework.db.dao.TimeDao
import javax.inject.Inject

class RoomTeamDataSource @Inject constructor(
    private val timeDao: TimeDao
) : TeamLocalDataSource {

}


