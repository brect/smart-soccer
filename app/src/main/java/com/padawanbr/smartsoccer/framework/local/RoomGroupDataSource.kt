package com.padawanbr.smartsoccer.framework.local

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.framework.db.dao.GrupoDao
import com.padawanbr.smartsoccer.framework.db.entity.ConfiguracaoEsporteEntity
import com.padawanbr.smartsoccer.framework.db.entity.GrupoComJogadores
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import com.padawanbr.smartsoccer.framework.db.entity.toGroupModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomGroupDataSource @Inject constructor(
    private val grupoDao: GrupoDao
) : GroupLocalDataSource {

    override fun getAll(): Flow<List<Grupo>> {
        return grupoDao.getAll().map {
            it.toGroupModel()
        }
    }

    override fun getGrupoComJogadoresById(grupoId: Int?): Flow<GrupoComJogadores?> {
        return grupoDao.getGrupoComJogadoresById(grupoId)
    }

    override suspend fun saveGroup(grupo: Grupo) {
        return grupoDao.insert(grupo.toGrupoEntity())
    }

    override suspend fun deleteGroup(groupId: Int) {
        grupoDao.delete(groupId)
    }

    private fun Grupo.toGrupoEntity() = GrupoEntity(
        id,
        nome,
        quantidadeTimes,
        configuracaoEsporte.toConfiguracaoEsporteEntity() // Utilize a função de conversão aqui
    )

    private fun ConfiguracaoEsporte.toConfiguracaoEsporteEntity() = ConfiguracaoEsporteEntity(
        tipoEsporte.toString(),
        quantidadeMinimaPorTime
    )
}

