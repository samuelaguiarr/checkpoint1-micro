package br.com.fiap.rm_550212.controller;

// Teste do workflow de CI - branch develop

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.dto.AmbienteRequestUpdate;
import br.com.fiap.rm_550212.dto.AmbienteResponse;
import br.com.fiap.rm_550212.service.AmbienteService;

@RestController
@RequestMapping("/api/ambientes")
public class AmbienteController {
    @Autowired
    private AmbienteService ambienteService;

    @PostMapping
    public ResponseEntity<AmbienteResponse> criar(
            @RequestBody AmbienteRequestCreate dto) {
        return ResponseEntity
                .status(201)
                .body(new AmbienteResponse().toDto(ambienteService.criarAmbiente(dto)));
    }

    @GetMapping("{id}")
    public ResponseEntity<AmbienteResponse> buscarPorId(@PathVariable Long id){
        return ambienteService.buscarPorId(id)
                .map(ambiente -> ResponseEntity.ok(new AmbienteResponse().toDto(ambiente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<AmbienteResponse>> buscarTodos(){
        return ResponseEntity.ok(ambienteService.buscarTodos().stream()
                .map(item -> new AmbienteResponse().toDto(item))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmbienteResponse> atualizar(@PathVariable Long id, @RequestBody AmbienteRequestUpdate dto){
        return ambienteService.atualizarAmbiente(id,dto)
                .map(ambiente -> new AmbienteResponse().toDto(ambiente))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if(ambienteService.deletarAmbiente(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
