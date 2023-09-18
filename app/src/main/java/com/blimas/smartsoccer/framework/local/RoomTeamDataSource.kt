package com.blimas.smartsoccer.framework.local

import com.blimas.smartsoccer.core.data.repository.TeamLocalDataSource
import com.blimas.smartsoccer.framework.db.dao.TimeDao
import javax.inject.Inject

class RoomTeamDataSource @Inject constructor(
    private val timeDao: TimeDao
) : TeamLocalDataSource {

}


