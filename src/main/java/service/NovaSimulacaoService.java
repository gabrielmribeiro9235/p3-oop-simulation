package service;

import exception.EntityAlreadyExists;
import model.Declaracao;
import model.DeclaracaoCompleta;
import model.DeclaracaoSimplificada;
import persistence.dao.DeclaracaoDAO;
import persistence.mapper.DeclaracaoMapper;

public class NovaSimulacaoService {
    private final DeclaracaoDAO declaracaoDAO;

    public NovaSimulacaoService(DeclaracaoDAO declaracaoDAO) {
        this.declaracaoDAO = declaracaoDAO;
    }

    public void iniciar(double ganhoTributavel, double valorJaPago) {
        if (declaracaoDAO.findById(1).isPresent() || declaracaoDAO.findById(2).isPresent()) {
            throw new EntityAlreadyExists("Já existe uma simulação vigente");
        }

        declaracaoDAO.save(DeclaracaoMapper.toDto(new DeclaracaoCompleta(1,  ganhoTributavel, valorJaPago)));
        declaracaoDAO.save(DeclaracaoMapper.toDto(new DeclaracaoSimplificada(2, ganhoTributavel, valorJaPago)));
    }
}
