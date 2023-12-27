package med.volli.api.domain.consulta;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.volli.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

        @JsonAlias("medico_id")
        Long idMedico,

        @JsonAlias("paciente_id")
        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        @JsonAlias("data_consulta")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,

        Especialidade especialidade) {
}