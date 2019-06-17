package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Vaga;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaDAO extends CrudRepository<Vaga, Integer>{
    String queryLocal = "SELECT local FROM vaga";
    String queryAndar = "SELECT andar FROM vaga";
    
    List <Vaga> findByAndar(int andar);
    List <Vaga> findByLocal(String local);

}
