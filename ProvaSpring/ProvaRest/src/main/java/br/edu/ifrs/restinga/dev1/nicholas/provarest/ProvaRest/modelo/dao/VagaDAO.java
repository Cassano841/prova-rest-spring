package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Vaga;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaDAO extends CrudRepository<Vaga, Integer>{
    List<Vaga> findByNomeContaining(String nome);
    List<Vaga> findByNomeStartingWith(String nome);
    List<Vaga> findByValorBetween(float inicio, float fim);

}
