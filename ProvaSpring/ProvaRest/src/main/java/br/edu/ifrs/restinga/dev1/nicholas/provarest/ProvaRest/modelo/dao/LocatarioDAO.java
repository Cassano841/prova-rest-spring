package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Locatario;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Pagamento;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 10070217
 */
@Repository
public interface LocatarioDAO extends CrudRepository<Locatario, Integer> {
    List<Locatario> findByNomeContaining(String nome);
    List<Locatario> findByNomeStartingWith(String nome);
    //List<Locatario> findByFormaPagamento(Pagamento pagamento);
}
