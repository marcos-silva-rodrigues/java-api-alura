package med.volli.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.volli.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente json, UriComponentsBuilder uriComponentsBuilder) {
        Paciente save = repository.save(new Paciente(json));

        var uri = uriComponentsBuilder
                .path("/pacientes/{id}")
                .buildAndExpand(save.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new DadosDetalhePaciente(save));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(Pageable paginacao) {
         var page = repository
                .findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhePaciente> detalhar(@PathVariable Long id) {
        var paciente = repository
                .getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhePaciente((paciente)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhePaciente> atualizar(@RequestBody @Valid DadosAtualizaoPaciente dados) {
        Paciente paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhePaciente(paciente));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

}
