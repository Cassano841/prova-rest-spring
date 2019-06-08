package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.controller;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.LocatarioDAO;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.PagamentoDAO;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.VagaDAO;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Locatario;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Pagamento;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Vaga;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 10070217
 */

@RestController
@RequestMapping("/api")
public class Locatarios {
    
    @Autowired
    LocatarioDAO locatarioDAO;
    
    @Autowired
    PagamentoDAO pagamentoDAO;
    
    @RequestMapping(path = "/locatarios/pequisar/nome", method = RequestMethod.GET)
    public Iterable <Locatario> pesquisarNome(@RequestParam String contem){
        if(contem != null) {
            return locatarioDAO.findByNomeContaining(contem); 
        } else{
            throw new RequisicaoInvalida("Deu ruim!");
        }
    }
    
    @RequestMapping(path = "/locatarios/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Locatario> listar() {
        return locatarioDAO.findAll();
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Locatario buscar(@PathVariable int idLocatario){
        final Optional<Locatario> findById = locatarioDAO.findById(idLocatario);
        if(findById.isPresent()){
            return findById.get();
        } else {
            throw new NaoEncontrado("Não foi encontrado o ID informado para busca!");
        }
    }
    
    @RequestMapping(path = "/locatarios/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Locatario cadastrarLocatario(@RequestBody Locatario locatario){
        
        Locatario locatarioBanco = locatarioDAO.save(locatario);
        return locatarioBanco;
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarLocatario(@PathVariable int idLocatario, @RequestBody Locatario locatario){
        final Locatario locatarioBanco = this.buscar(idLocatario);
        
        if(locatario.getNome() == null){
            throw new RequisicaoInvalida("Você deve informar o nome do locatário!");
        } else if (locatario.getCpf() == null){
            throw new RequisicaoInvalida("Você deve informar o CPF do locatário!");
        } else if (locatario.getCpf() == "123123"){
            throw new RequisicaoInvalida("CPF deve ser igual a 11 caracteres");
        } else {
            locatarioBanco.setNome(locatario.getNome());
            locatarioBanco.setCpf(locatario.getCpf());
            locatarioBanco.setVagas(locatario.getVagas());
            
            locatarioDAO.save(locatarioBanco);
        }
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int idLocatario) {
        if(locatarioDAO.existsById(idLocatario)){
            locatarioDAO.deleteById(idLocatario);
        } else {
            throw new NaoEncontrado("Não foi encontrado o ID informado para remoção!");
        }
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento cadastrarPagamento(@PathVariable int idLocatario, @RequestBody Pagamento pagamento){
        final Locatario locatarioBanco = this.buscar(idLocatario);
        Pagamento pagamentoBanco = pagamentoDAO.save(pagamento);
        locatarioBanco.getPagamentos().add(pagamentoBanco);
     
        
        locatarioDAO.save(locatarioBanco);
        
        return pagamentoBanco;
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Pagamento buscarPagamento(@PathVariable int idLocatario){
        final Optional<Pagamento> findById = pagamentoDAO.findById(idLocatario);
        if(findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Pagamento não localizado!");
        }
    }
    
    
}

