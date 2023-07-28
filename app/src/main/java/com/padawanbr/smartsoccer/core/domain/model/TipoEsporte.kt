package com.padawanbr.smartsoccer.core.domain.model

enum class TipoEsporte(val tipo: String, val quantidadeMinimaPorTime: Int) {
    DEFAULT("Selecione uma modalidade", 0),
    FUTSAL("Futsal", 5),
    FUTEBOL("Futebol", 11),
    FUTEBOL_7("Futebol 7", 7),
    UNDEFINED("Undefined", 0)
}
