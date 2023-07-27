package com.padawanbr.smartsoccer.framework.db.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador

@Entity(tableName = "jogador")
data class JogadorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val idade: Int,
    @TypeConverters(PosicaoJogador::class)
    val posicao: PosicaoJogador? = null,
    @TypeConverters(ClassificacoesConverter::class)
    val habilidades: Map<String, Float> = emptyMap(),
    var estaNoDepartamentoMedico: Boolean = false,
    @ColumnInfo(name = "grupoId")
    val grupoId: Int // Relacionamento reverso apontando para o grupo ao qual o jogador pertence
)

fun List<JogadorEntity>.toSoccerPlayerModel() = map {
    Jogador(
        it.id,
        it.nome,
        it.idade,
        it.posicao,
        it.habilidades,
        it.estaNoDepartamentoMedico
    )
}
