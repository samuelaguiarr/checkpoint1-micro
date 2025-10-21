package br.com.fiap.rm_550212.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.dto.AmbienteRequestUpdate;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;

@Service
public class AmbienteService {

    @Autowired
    private AmbienteRepository ambienteRepository;

    public Ambiente criarAmbiente(AmbienteRequestCreate dto){
        return ambienteRepository.save(dto.toModel());
    }

    public Optional<Ambiente> buscarPorId(Long id){
        return ambienteRepository.findById(id);
    }
    public List<Ambiente> buscarTodos(){
        return ambienteRepository.findAll();
    }
    public Optional<Ambiente> atualizarAmbiente(Long id, AmbienteRequestUpdate dto){
        return ambienteRepository.findById(id)
                .map(ambiente -> ambienteRepository.save(dto.toModel(ambiente)));
    }

    public boolean deletarAmbiente(Long id){
        if(ambienteRepository.existsById(id)){
            ambienteRepository.deleteById(id);
            return true;
        }else return false;
    }

}
