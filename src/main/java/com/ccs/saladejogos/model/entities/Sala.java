package com.ccs.saladejogos.model.entities;

import com.ccs.saladejogos.exceptions.SalaFechadaException;
import com.ccs.saladejogos.exceptions.SalaLotadaException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.LinkedHashSet;

@Getter
@Setter
@SuperBuilder
public class Sala extends SalaJogosEntity {

    private static final Short MAX_JOGADORES = 4;
    private Collection<Jogador> jogadores;
    private String descricao;
    private boolean aberta;
    private boolean encerrada;

    public Boolean isLotada() {
        return jogadores.size() >= MAX_JOGADORES;
    }

    public void abrirSala() {
        aberta = true;
    }

    public void encerrarSala() {
        aberta = false;
        encerrada = true;

       jogadores.forEach(Jogador::removerSala);
    }

    public void addJogador(Jogador jogador) {
        if (!aberta) {
            throw new SalaFechadaException("Não é possível adicionar jogadores a uma Sala fechada.");
        }

        if (jogadores == null) {
            jogadores = new LinkedHashSet<>();
        }

        if (isLotada()) {
            throw new SalaLotadaException("Sala atingiu o limite de jogadores. Limite = " + MAX_JOGADORES);
        }

        if (jogadores.add(jogador)) {
            jogador.setSala(this);
        }
    }

    public void removerJogador(Jogador jogador) {
        if (jogadores != null) {
            if (jogadores.remove(jogador)) {
                jogador.removerSala();
            }
        }
    }
}


