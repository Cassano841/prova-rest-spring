package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Locatario;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Pagamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 10070217
 */
@Repository
public interface LocatarioDAO extends CrudRepository<Locatario, Integer> {
    Iterable<Locatario> findByNomeContaining(String nome);
    Iterable<Locatario> findByNomeStartingWith(String nome);
    //Iterable<Locatario> findByForma(Pagamento formaPagamento);
}
