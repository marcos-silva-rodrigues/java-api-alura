package med.volli.api.domain.consulta.validacoes;

import med.volli.api.domain.consulta.DadosAgendamentoConsulta;
import med.volli.api.domain.consulta.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements  ValidadorAgendamentoConsulta{

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var agora = LocalDateTime.now();

        var diferenciaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferenciaEmMinutos < 30) {
            throw  new ValidacaoException("Consulta deve ser agendada com antecedência mínina de 30 minutos");
        }
    }
}
