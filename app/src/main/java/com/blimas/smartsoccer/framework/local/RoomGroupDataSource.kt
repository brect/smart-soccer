package com.blimas.smartsoccer.framework.local

import com.blimas.smartsoccer.core.data.repository.GroupLocalDataSource
import com.blimas.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.blimas.smartsoccer.core.domain.model.Grupo
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadores
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadoresETorneios
import com.blimas.smartsoccer.core.domain.model.toRangeIdadeEntity
import com.blimas.smartsoccer.framework.db.dao.GrupoDao
import com.blimas.smartsoccer.framework.db.entity.ConfiguracaoEsporteEntity
import com.blimas.smartsoccer.framework.db.entity.GrupoEntity
import com.blimas.smartsoccer.framework.db.entity.toGrupoComJogadoresETorneiosModel
import com.blimas.smartsoccer.framework.db.entity.toGrupoComJogadoresModel
import com.blimas.smartsoccer.framework.db.entity.toListGroupModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomGroupDataSource @Inject constructor(
    private val grupoDao: GrupoDao
) : GroupLocalDataSource {

    override fun getAll(): Flow<List<Grupo>> {
        return grupoDao.getAll().map {
            it.toListGroupModel()
        }
    }

    override fun getGrupoComJogadoresById(grupoId: String?): Flow<GrupoComJogadores?> {
        return grupoDao.getGrupoComJogadoresById(grupoId).map {
            it?.toGrupoComJogadoresModel()
        }
    }

    override suspend fun getGrupoComJogadoresETorneiosById(grupoId: String?): GrupoComJogadoresETorneios {
        return grupoDao.getGrupoComJogadoresETorneiosById(grupoId)
            .toGrupoComJogadoresETorneiosModel()
    }

    override suspend fun saveGroup(grupo: Grupo) {
        return grupoDao.insert(grupo.toGrupoEntity())
    }

    override suspend fun deleteGroup(groupId: String) {
        grupoDao.delete(groupId)
    }

    private fun Grupo.toGrupoEntity() = GrupoEntity(
        id,
        nome,
        endereco,
        configuracaoEsporte.toConfiguracaoEsporteEntity(),
        diaDoJogo,
        horarioInicio,
        quantidadeTimes,
        rangeIdade.toRangeIdadeEntity()
    )

    private fun ConfiguracaoEsporte.toConfiguracaoEsporteEntity() = ConfiguracaoEsporteEntity(
        tipoEsporte.toString(),
        quantidadeMinimaPorTime
    )
}

