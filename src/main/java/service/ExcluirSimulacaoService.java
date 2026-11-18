package service;

import model.DeclaracaoSimplificada;
import persistence.dao.DeclaracaoDAO;
import persistence.dto.DeclaracaoDTO;
import persistence.mapper.DeclaracaoMapper;

import java.util.NoSuchElementException;

public class ExcluirSimulacaoService {
    private final DeclaracaoDAO declaracaoDAO;

    public ExcluirSimulacaoService(DeclaracaoDAO declaracaoDAO) {
        this.declaracaoDAO = declaracaoDAO;
    }

    public void excluir() {
        DeclaracaoDTO completa = declaracaoDAO.findById(1)
                .orElseThrow(() -> new NoSuchElementException("Não há simulação vigente"));
        DeclaracaoDTO simplificada = declaracaoDAO.findById(2)
                .orElseThrow(() -> new NoSuchElementException("Não há simulação vigente"));

        declaracaoDAO.delete(completa);
        declaracaoDAO.delete(simplificada);
    }
}
