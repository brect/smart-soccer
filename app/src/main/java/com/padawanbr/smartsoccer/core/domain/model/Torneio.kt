package com.padawanbr.smartsoccer.core.domain.model

data class Torneio(
    val id: String,
    val nome: String,
    val tipoTorneio: TipoTorneio,
    val criteriosDesempate: List<CriterioDesempateItem>,
    val times: List<Time>,
    val partidas: List<Partida>
)