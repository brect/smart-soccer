package com.blimas.smartsoccer.core.domain.model

enum class PosicaoJogador(val id: Int, val funcao: String, val abreviacao: String, zona: String) {
    GOLEIRO(1, "Goleiro", "GK", "GOL"),
    LATERAL(2, "Lateral", "LAT", "DEFESA"),
    LATERAL_DIREITO(3, "Lateral Direito", "LD", "DEFESA"),
    LATERAL_ESQUERDO(4, "Lateral Esquerdo", "LE", "DEFESA"),
    ZAGUEIRO(5, "Zagueiro", "ZAG", "DEFESA"),
    LIBERO(6, "Líbero", "LIB", "DEFESA"),
    ZAGUEIRO_CENTRAL(7, "Zagueiro Central", "ZC", "DEFESA"),
    ZAGUEIRO_DIREITO(8, "Zagueiro Direito", "ZD", "DEFESA"),
    ZAGUEIRO_ESQUERDO(9, "Zagueiro Esquerdo", "ZE", "DEFESA"),
    VOLANTE(10, "Volante", "VOL", "MEIO_CAMPO"),
    VOLANTE_DEFENSIVO(11, "Volante Defensivo", "VOL_DEF", "MEIO_CAMPO"),
    PRIMEIRO_VOLANTE(12, "Primeiro Volante", "1º VOL", "MEIO_CAMPO"),
    SEGUNDO_VOLANTE(13, "Segundo Volante", "2º VOL", "MEIO_CAMPO"),
    MEIA(14, "Meio campo", "MEIA", "MEIO_CAMPO"),
    MEIA_DEFENSIVO(15, "Meia Defensivo", "MEIA_DEF", "MEIO_CAMPO"),
    MEIA_DEFENSIVO_CENTRAL(16, "Meia Defensivo Central", "MEIA_DEF_C", "MEIO_CAMPO"),
    MEIA_DEFENSIVO_DIREITO(17, "Meia Defensivo Direito", "MEIA_DEF_DIR", "MEIO_CAMPO"),
    MEIA_DEFENSIVO_ESQUERDO(18, "Meia Defensivo Esquerdo", "MEIA_DEF_ESQ", "MEIO_CAMPO"),
    MEIA_CENTRAL(19, "Meia Central", "MEIA_C", "MEIO_CAMPO"),
    MEIA_DIREITO(20, "Meia Direito", "MEIA_DIR", "MEIO_CAMPO"),
    MEIA_ESQUERDO(21, "Meia Esquerdo", "MEIA_ESQ", "MEIO_CAMPO"),
    MEIA_OFENSIVO(22, "Meia Ofensivo", "MEIA_OF", "MEIO_CAMPO"),
    MEIA_OFENSIVO_CENTRAL(23, "Meia Ofensivo Central", "MEIA_OF_C", "MEIO_CAMPO"),
    MEIA_OFENSIVO_DIREITO(24, "Meia Ofensivo Direito", "MEIA_OF_DIR", "MEIO_CAMPO"),
    MEIA_OFENSIVO_ESQUERDO(25, "Meia Ofensivo Esquerdo", "MEIA_OF_ESQ", "MEIO_CAMPO"),
    PONTA(26, "Ponta", "PONTA", "ATAQUE"),
    PONTA_DIREITO(27, "Ponta Direito", "PONTA_DIR", "ATAQUE"),
    PONTA_ESQUERDO(28, "Ponta Esquerdo", "PONTA_ESQ", "ATAQUE"),
    SEGUNDO_ATACANTE(29, "Segundo Atacante", "2º ATA", "ATAQUE"),
    CENTROAVANTE_REFERENCIA(30, "Centroavante de Referência", "CA", "ATAQUE"),
    MEIA_ATACANTE(31, "Meia-Atacante", "MEIA-ATA", "ATAQUE"),
    MEIA_ATACANTE_CENTRAL(32, "Meia-Atacante Central", "MEIA-ATA_C", "ATAQUE"),
    MEIA_ATACANTE_DIREITO(33, "Meia-Atacante Direito", "MEIA-ATA_DIR", "ATAQUE"),
    MEIA_ATACANTE_ESQUERDO(34, "Meia-Atacante Esquerdo", "MEIA-ATA_ESQ", "ATAQUE"),
    ATACANTE(35, "Atacante", "ATA", "ATAQUE"),
    ATACANTE_CENTRAL(36, "Atacante Central", "ATA_C", "ATAQUE"),
    ATACANTE_DIREITO(37, "Atacante Direito", "ATA_DIR", "ATAQUE"),
    ATACANTE_ESQUERDO(38, "Atacante Esquerdo", "ATA_ESQ", "ATAQUE");

    companion object {
        fun fromString(posicao: String?): PosicaoJogador? {
            return values().find { "${it.funcao} (${it.abreviacao})" == posicao }
        }

        fun fromAbreviacaoString(abreviacao: String?): PosicaoJogador? {
            return values().find { "(${it.abreviacao})" == abreviacao }
        }

        fun compararAbreviacao(abreviacao: String?): PosicaoJogador? {
            return values().find { "${it.abreviacao}" == abreviacao }
        }
    }
}
