package com.padawanbr.smartsoccer.framework.repository

import com.padawanbr.smartsoccer.core.data.repository.TeamRepository
import com.padawanbr.smartsoccer.core.data.repository.TorneioRepository
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.framework.local.RoomTeamDataSource
import com.padawanbr.smartsoccer.framework.local.RoomTournamentDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDataSource: RoomTeamDataSource,
) : TeamRepository {

}