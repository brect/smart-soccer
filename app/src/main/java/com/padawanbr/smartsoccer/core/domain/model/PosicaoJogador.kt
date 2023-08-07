package com.padawanbr.smartsoccer.core.domain.model

enum class PosicaoJogador(val funcao: String, val abreviacao: String) {
    DEFAULT("Selecione uma posição", ""),
    GOLEIRO("Goleiro", "GK"),
    LATERAL("Lateral", "LAT"),
    LATERAL_DIREITO("Lateral Direito", "LD"),
    LATERAL_ESQUERDO("Lateral Esquerdo", "LE"),
    ZAGUEIRO("Zagueiro", "ZAG"),
    LIBERO("Líbero", "LIB"),
    ZAGUEIRO_CENTRAL("Zagueiro Central", "ZC"),
    ZAGUEIRO_DIREITO("Zagueiro Direito", "ZD"),
    ZAGUEIRO_ESQUERDO("Zagueiro Esquerdo", "ZE"),
    VOLANTE("Volante", "VOL"),
    VOLANTE_DEFENSIVO("Volante Defensivo", "VOL_DEF"),
    PRIMEIRO_VOLANTE("Primeiro Volante", "1º VOL"),
    SEGUNDO_VOLANTE("Segundo Volante", "2º VOL"),
    MEIA_DEFENSIVO("Meia Defensivo", "MEIA_DEF"),
    MEIA_DEFENSIVO_CENTRAL("Meia Defensivo Central", "MEIA_DEF_C"),
    MEIA_DEFENSIVO_DIREITO("Meia Defensivo Direito", "MEIA_DEF_DIR"),
    MEIA_DEFENSIVO_ESQUERDO("Meia Defensivo Esquerdo", "MEIA_DEF_ESQ"),
    MEIA_CENTRAL("Meia Central", "MEIA_C"),
    MEIA_DIREITO("Meia Direito", "MEIA_DIR"),
    MEIA_ESQUERDO("Meia Esquerdo", "MEIA_ESQ"),
    MEIA_OFENSIVO("Meia Ofensivo", "MEIA_OF"),
    MEIA_OFENSIVO_CENTRAL("Meia Ofensivo Central", "MEIA_OF_C"),
    MEIA_OFENSIVO_DIREITO("Meia Ofensivo Direito", "MEIA_OF_DIR"),
    MEIA_OFENSIVO_ESQUERDO("Meia Ofensivo Esquerdo", "MEIA_OF_ESQ"),
    PONTA("Ponta", "PONTA"),
    PONTA_DIREITO("Ponta Direito", "PONTA_DIR"),
    PONTA_ESQUERDO("Ponta Esquerdo", "PONTA_ESQ"),
    SEGUNDO_ATACANTE("Segundo Atacante", "2º ATA"),
    CENTROAVANTE_REFERENCIA("Centroavante de Referência", "CA"),
    MEIA_ATACANTE("Meia-Atacante", "MEIA-ATA"),
    MEIA_ATACANTE_CENTRAL("Meia-Atacante Central", "MEIA-ATA_C"),
    MEIA_ATACANTE_DIREITO("Meia-Atacante Direito", "MEIA-ATA_DIR"),
    MEIA_ATACANTE_ESQUERDO("Meia-Atacante Esquerdo", "MEIA-ATA_ESQ"),
    ATACANTE("Atacante", "ATA"),
    ATACANTE_CENTRAL("Atacante Central", "ATA_C"),
    ATACANTE_DIREITO("Atacante Direito", "ATA_DIR"),
    ATACANTE_ESQUERDO("Atacante Esquerdo", "ATA_ESQ");

    companion object {
        fun fromString(posicao: String?): PosicaoJogador? {
            return values().find { "${it.funcao} (${it.abreviacao})" == posicao }
        }

        fun fromAbreviacaoString(abreviacao: String?): PosicaoJogador? {
            return values().find { "(${it.abreviacao})" == abreviacao }
        }

        fun compararAbreviacao(abreviacao: String?):  PosicaoJogador? {
            return values().find { "${it.abreviacao}" == abreviacao }
        }
    }
}
