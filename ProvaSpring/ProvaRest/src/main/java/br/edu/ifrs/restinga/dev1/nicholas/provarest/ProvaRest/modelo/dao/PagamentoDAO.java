package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Pagamento;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 10070217
 */

@Repository
public interface PagamentoDAO extends CrudRepository<Pagamento, Integer>{
    //Iterable<Pagamento> findByValor(float valor);

    //Iterable<Pagamento> findByNomeContaining(String forma);
}
