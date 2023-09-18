package com.blimas.smartsoccer.core.usecase

import com.blimas.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.blimas.smartsoccer.core.domain.model.Jogador
import com.blimas.smartsoccer.core.usecase.base.CoroutinesDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetSoccerPlayersByGroupUseCase {
    suspend operator fun invoke(grupoId: String): Flow<List<Jogador>>
}

class GetSoccerPlayersByGroupUseCaseImpl @Inject constructor(
    private val repository: SoccerPlayerRepository,
    private val dispatchers: CoroutinesDispatchers,
) : GetSoccerPlayersByGroupUseCase {

    override suspend fun invoke(grupoId: String): Flow<List<Jogador>> {
        return withContext(dispatchers.io()) {
            repository.getAllSoccerPlayers(grupoId)
        }
    }
}
