package com.blimas.smartsoccer.core.domain.model

enum class TipoTorneio (val tipo: String, val descricao: String) {
    JOGO_UNICO("JOGO_UNICO", "Jogo Único"),
    ELIMINATORIAS("ELIMINATORIAS", "Eliminatórias"),
    PONTOS_CORRIDOS("PONTOS_CORRIDOS", "Pontos corridos"),
    GRUPOS_E_ELIMINATORIAS("GRUPOS_E_ELIMINATORIAS", "Grupos e Eliminatórias"),
    PONTOS_CORRIDOS_E_ELIMINATORIAS("PONTOS_CORRIDOS_E_ELIMINATORIAS", "Pontos corridos e Eliminatórias"),
    GRUPOS_CRUZADOS_E_ELIMINATORIAS("GRUPOS_CRUZADOS_E_ELIMINATORIAS", "Grupos cruzados e Eliminatórias")
}