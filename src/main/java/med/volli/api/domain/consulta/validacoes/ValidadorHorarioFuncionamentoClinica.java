package med.volli.api.domain.consulta.validacoes;

import med.volli.api.domain.consulta.DadosAgendamentoConsulta;
import med.volli.api.domain.consulta.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica ||depoisDoEncerramentoDaClinica) {
            throw  new ValidacaoException("Consulta foraa do hórario de funcionamento da clínica");
        }
    }
}
