package med.volli.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.volli.api.domain.endereco.DadosEndereco;

public record DadosAtualizaoPaciente(

        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid DadosEndereco endereco) {


}