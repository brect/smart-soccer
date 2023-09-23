package com.padawanbr.smartsoccer.framework.repository

import com.padawanbr.smartsoccer.core.data.repository.TeamRepository
import com.padawanbr.smartsoccer.framework.local.RoomTeamDataSource
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDataSource: RoomTeamDataSource,
) : TeamRepository {

}