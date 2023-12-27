package med.volli.api.domain.consulta.validacoes;

import med.volli.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoConsulta {
    void validar(DadosAgendamentoConsulta dados);
}
