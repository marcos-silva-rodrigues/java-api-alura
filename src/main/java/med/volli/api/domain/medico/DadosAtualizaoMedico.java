package med.volli.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.volli.api.domain.endereco.DadosEndereco;

public record DadosAtualizaoMedico(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
