package com.padawanbr.smartsoccer.core.domain.model

enum class TipoEsporte(
    val id: Int,
    val modalidade: String,
    val tipo: String,
    val quantidadeMinimaPorTime: Int
) {
    FUTSAL(1, "Futsal - Quadra", "Quadra", 5),
    FUTEBOL_CAMPO(2, "Futebol de Campo - Gramado", "Gramado", 11),
    FUTEBOL_SOCIETY(3, "Futebol de Campo - Society", "Society", 11),
    FUTEBOL_7_SOCIETY(4, "Futebol 7 - Society", "Society", 7);
}
