package br.com.fiap.rm_550212.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.rm_550212.model.Ambiente;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {


}
