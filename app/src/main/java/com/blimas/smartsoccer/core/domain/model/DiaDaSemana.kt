package com.blimas.smartsoccer.core.domain.model

enum class DiaDaSemana(
    val id: Int,
    val dia: String,
) {
    SEGUNDA_FEIRA(1, "Segunda-feira"),
    TERCA_FEIRA(2, "Terça-feira"),
    QUARTA_FEIRA(3, "Quarta-feira"),
    QUINTA_FEIRA(4, "Quinta-feira"),
    SEXTA_FEIRA(5, "Sexta-feira"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");
}
