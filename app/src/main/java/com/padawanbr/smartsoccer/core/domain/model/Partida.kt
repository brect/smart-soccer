package com.padawanbr.smartsoccer.core.domain.model

data class Partida(
    val id: String,
    val timeCasa: Time,
    val timeVisitante: Time,
    val placarCasa: Int?,
    val placarVisitante: Int?
)