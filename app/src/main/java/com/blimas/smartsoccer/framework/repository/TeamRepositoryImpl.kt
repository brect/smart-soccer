package com.blimas.smartsoccer.framework.repository

import com.blimas.smartsoccer.core.data.repository.TeamRepository
import com.blimas.smartsoccer.framework.local.RoomTeamDataSource
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDataSource: RoomTeamDataSource,
) : TeamRepository {

}