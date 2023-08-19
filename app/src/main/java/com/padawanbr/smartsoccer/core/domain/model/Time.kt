package com.padawanbr.smartsoccer.core.domain.model

data class Time(
    val id: String,
    val nome: String,
    val jogadores: MutableList<Jogador>,
) {
    val mediaHabilidades: Float
        get() = jogadores.map { it.calcularMediaHabilidades() }.average().toFloat()

    val mediaIdades: Int
        get() = calcularMediaIdades()

    val forcaTime: Int
        get() = calcularForcaTime()
}


fun Time.calcularForcaTime(): Int {
    // Verifica se o time possui jogadores
    if (jogadores.isEmpty()) return 0

    return jogadores.map { it -> it.calcularMediaHabilidades() }.sum().toInt()
}

fun Time.calcularMediaIdades(): Int {
    // Verifica se o time possui jogadores
    if (jogadores.isEmpty()) return 0

    return jogadores.map { it -> it.idade }.average().toInt()
}
