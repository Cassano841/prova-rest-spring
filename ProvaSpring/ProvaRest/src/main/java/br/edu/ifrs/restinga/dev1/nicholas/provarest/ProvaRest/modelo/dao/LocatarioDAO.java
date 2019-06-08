package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Locatario;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author 10070217
 */
public interface LocatarioDAO extends CrudRepository<Locatario, Integer> {
    List<Locatario> findByNomeContaining(String nome);
}
