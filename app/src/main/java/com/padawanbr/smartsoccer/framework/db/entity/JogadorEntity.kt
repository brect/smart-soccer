package com.padawanbr.smartsoccer.framework.db.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import java.util.UUID

@Entity(tableName = "jogador")
data class JogadorEntity(
    @PrimaryKey
    val jogadorId: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val nome: String,
    val idade: Int,
    @TypeConverters(PosicaoJogador::class)
    val posicao: PosicaoJogador? = null,
    @TypeConverters(ClassificacoesConverter::class)
    val habilidades: Map<String, Float> = emptyMap(),
    var estaNoDepartamentoMedico: Boolean = false,
    @ColumnInfo(name = "grupoId")
    val grupoId: String // Utilizando UUID como ID para referenciar o grupo
)


fun List<JogadorEntity>.toListSoccerPlayerModel() = map {
    Jogador(
        it.jogadorId,
        it.nome,
        it.idade,
        it.posicao,
        it.habilidades,
        it.estaNoDepartamentoMedico,
        it.grupoId
    )
}

// Na extensão para JogadorEntity
fun JogadorEntity.toJogadorModel(): Jogador {
    return Jogador(
        id = jogadorId,
        nome = nome,
        idade = idade,
        posicao = posicao,
        habilidades = habilidades,
        estaNoDepartamentoMedico = estaNoDepartamentoMedico,
        grupoId = grupoId
    )
}


fun List<Jogador>.toListJogadorEntity() = map {
    JogadorEntity(
        jogadorId = it.id,
        nome = it.nome,
        idade = it.idade,
        posicao = it.posicao,
        habilidades = it.habilidades,
        estaNoDepartamentoMedico = it.estaNoDepartamentoMedico,
        grupoId = it.grupoId
    )
}

