package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros;

/**
 *
 * @author 10070217
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NaoEncontrado extends RuntimeException {
    public NaoEncontrado(String erro) {
        super(erro);
    }
}
