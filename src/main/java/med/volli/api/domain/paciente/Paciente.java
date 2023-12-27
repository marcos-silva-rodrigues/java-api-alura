package med.volli.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.volli.api.domain.endereco.Endereco;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "pacientes")
@Entity(name = "Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    private Boolean ativo;

    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.email = dados.email();
        this.telefone= dados.telefone();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizaoPaciente dados) {
        if (dados.nome() != null)
            this.nome = dados.nome();

        if (dados.telefone() != null)
            this.telefone = dados.telefone();

        if (dados.endereco() != null)
            endereco.atualizaEndereco(dados.endereco());
    }

    public void excluir() {
        this.ativo = false;
    }
}
