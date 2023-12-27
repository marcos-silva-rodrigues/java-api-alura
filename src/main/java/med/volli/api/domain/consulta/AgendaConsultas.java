package med.volli.api.domain.consulta;

import med.volli.api.domain.consulta.validacoes.ValidadorAgendamentoConsulta;
import med.volli.api.domain.medico.Medico;
import med.volli.api.domain.medico.MedicoRepository;
import med.volli.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        var paciente = pacienteRepository
                .findById(dados.idPaciente())
                .orElseThrow(() -> new ValidacaoException("Id do paciente informado não existe!"));


        var medico = escolherMedico(dados);

        validadores.forEach(v -> v.validar(dados));

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);
        var dadosConsulta = new DadosDetalhamentoConsulta(consulta.getId(), medico.getId(), paciente.getId(), consulta.getData());
        return  dadosConsulta;
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            var medico = medicoRepository
                    .findById(dados.idMedico())
                    .orElseThrow(() -> new ValidacaoException("Id do medico informado não existe!"));
            return medico;
        } else {
            if (dados.especialidade() == null) {
                throw new ValidacaoException("Especialidade é obrigátorio quando médico não for escolhido!");
            }

            var medico = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

            if (medico.isEmpty())  throw new ValidacaoException("Não existe médico disponivel para essa data");
            return medico.get();
        }

    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
