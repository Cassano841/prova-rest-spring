package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Pagamento;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author 10070217
 */

public interface PagamentoDAO extends CrudRepository<Pagamento, Integer>{
    //List<Pagamento> findByFormaPagamento(String forma);
}
