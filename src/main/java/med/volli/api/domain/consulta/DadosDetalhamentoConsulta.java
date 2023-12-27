package med.volli.api.domain.consulta;

import med.volli.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long MedicoId, Long PacienteId, LocalDateTime data) {
}
