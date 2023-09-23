package com.padawanbr.smartsoccer

import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.domain.model.calcularMediaHabilidades
import com.padawanbr.smartsoccer.core.domain.model.sortearTimes
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val jogadores = createMockJogadores()
        println("----- Jogadores Criados -----")
        jogadores.forEachIndexed { index, jogador ->
            println("Jogador ${index + 1}")
            println("Nome: ${jogador.nome}")
            println("Idade: ${jogador.idade}")
            println("Posição: ${jogador.posicao}")
            println("Habilidades: ${jogador.habilidades}")
            println("Departamento Médico: ${jogador.estaNoDepartamentoMedico}")
            println("-----------------------------")
        }

        val numeroTimes = 2
        val considerarPosicoes = true
        val considerarOveralls = true
        val considerarDepartamentoMedico = false

        val timesSorteados = sortearTimes(
            jogadores,
            numeroTimes,
            considerarPosicoes,
            considerarOveralls,
            considerarDepartamentoMedico
        )

        println("----- Times Sorteados -----")
        timesSorteados.forEachIndexed { index, time ->
            println("Time ${index + 1}")
            println("Nome: ${time.nome}")
            println("Media time: ${time.mediaHabilidades}")
            println("Jogadores:")
            time.jogadores.forEach { jogador ->
                println(" - ${jogador.nome} (Media Habilidades: ${jogador.calcularMediaHabilidades()}) (Posição: ${jogador.posicao}) (DM: ${jogador.estaNoDepartamentoMedico})")
            }
            println("--------------------------")
        }
    }

    fun createMockJogadores(): MutableList<Jogador> {
        val jogadores = mutableListOf<Jogador>()

        val posicoesQuantidades = listOf(
            PosicaoJogador.GOLEIRO to 2,
            PosicaoJogador.ZAGUEIRO to 6,
            PosicaoJogador.LATERAL to 4,
            PosicaoJogador.ATACANTE to 4,
            PosicaoJogador.MEIA_DEFENSIVO to 5,
            PosicaoJogador.MEIA_OFENSIVO to 4,
            PosicaoJogador.MEIA_ATACANTE to 5
        )

        var jogadorCount = 0

        for ((posicao, quantidade) in posicoesQuantidades) {
            for (i in 0 until quantidade) {
                val jogador = Jogador(
                    nome = "Jogador ${jogadorCount + 1}",
                    idade = 20 + jogadorCount % 5,
                    posicao = posicao,
                    habilidades = mapOf(
                        "Chute" to (60 + jogadorCount % 40).toFloat(),
                        "Drible" to (50 + jogadorCount % 40).toFloat(),
                        "Velocidade" to (70 + jogadorCount % 40).toFloat()
                    ),
                    grupoId = "GrupoA"
                )
                jogadores.add(jogador)
                jogadorCount++
            }
        }

        // Completar com outras posições não especificadas
        while (jogadorCount < 30) {
            val posicao = when (jogadorCount % PosicaoJogador.values().size) {
                0 -> PosicaoJogador.ATACANTE
                1 -> PosicaoJogador.MEIA_DEFENSIVO
                2 -> PosicaoJogador.MEIA_OFENSIVO
                3 -> PosicaoJogador.MEIA_ATACANTE
                4 -> PosicaoJogador.ZAGUEIRO
                5 -> PosicaoJogador.GOLEIRO
                else -> PosicaoJogador.LATERAL
            }

            val jogador = Jogador(
                nome = "Jogador ${jogadorCount + 1}",
                idade = 20 + jogadorCount % 5,
                posicao = posicao,
                habilidades = mapOf(
                    "Chute" to (60 + jogadorCount % 40).toFloat(),
                    "Drible" to (50 + jogadorCount % 40).toFloat(),
                    "Velocidade" to (70 + jogadorCount % 40).toFloat()
                ),
                estaNoDepartamentoMedico = jogadorCount == 5 || jogadorCount == 15,
                grupoId = "GrupoA"
            )
            jogadores.add(jogador)
            jogadorCount++
        }

        return jogadores
    }

}


