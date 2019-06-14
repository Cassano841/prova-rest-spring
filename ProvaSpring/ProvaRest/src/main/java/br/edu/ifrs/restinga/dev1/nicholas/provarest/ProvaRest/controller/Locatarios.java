package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.controller;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.LocatarioDAO;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.PagamentoDAO;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.VagaDAO;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Locatario;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Pagamento;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.entidade.Vaga;
import java.util.List;
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
    
    /************************ 
     *  PESQUISA LOCATARIO  *    
     ************************/
    
    @RequestMapping(path = "/locatarios/pesquisar/nome", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Locatario> pesquisaNome(@RequestParam(required = false) String contem){ 
        if(contem!=null){
            return locatarioDAO.findByNomeContaining(contem);
        } else {
            throw new RequisicaoInvalida("Indicar contem ou comeca");
        }
    }
    
    /*
    @RequestMapping(path = "/locatarios/pesquisar/formapagamento", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Locatario> pesquisarFormaPagamento(@RequestParam(required = false) String pagamento){
        
        if(pagamento != null){
            return pagamentoDAO.findByFormaPagamento(pagamento);
        } else {
            throw new RequisicaoInvalida("Pagamento não encontrado");
        }
    }
    */
    
    /******************** 
     *  CRUD LOCATARIO  *    
     *******************/
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
        
        if(locatario.getNome() == null){
            throw new RequisicaoInvalida("Você deve informar o nome do locatário!");
        } else if (locatario.getCpf() == null){
            throw new RequisicaoInvalida("Você deve informar o CPF do locatário!");
        } else if (locatario.getCpf().length() < 11 || locatario.getCpf().length() > 11){
            throw new RequisicaoInvalida("CPF deve ser igual a 11 caracteres");
        } else {  
            Locatario locatarioBanco = locatarioDAO.save(locatario);

            return locatarioBanco;
        }
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarLocatario(@PathVariable int idLocatario, @RequestBody Locatario locatario){
        final Locatario locatarioBanco = this.buscar(idLocatario);
        
        if(locatario.getNome() == null){
            throw new RequisicaoInvalida("Você deve informar o nome do locatário!");
        } else if (locatario.getCpf() == null){
            throw new RequisicaoInvalida("Você deve informar o CPF do locatário!");
        } else if (locatario.getCpf().length() < 11 || locatario.getCpf().length() > 11){
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
    
    /******************************** 
     *  CRUD LOCATARIO > PAGEMENTO  *    
     ********************************/
        
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Pagamento> listarPagamentos(@PathVariable int idLocatario) {
        return this.buscar(idLocatario).getPagamentos();
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/{idPagamento}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Pagamento buscarPagamento(@PathVariable int idLocatario, @PathVariable int idPagamento){
        final Optional<Pagamento> findById = pagamentoDAO.findById(idPagamento);
        
        if(findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Pagamento não localizado!");
        }
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento cadastrarPagamento(@PathVariable int idLocatario, @RequestBody Pagamento pagamento){
        final Locatario locatarioBanco = this.buscar(idLocatario);
        
        if(pagamento.getForma() == null){
            throw new RequisicaoInvalida("Você deve informar a forma de pagamento!");
        }
        if(pagamento.getData() == null || pagamento.getData().equals("")){
            throw new RequisicaoInvalida("Data deve ser preenchido!");
        }
        if(pagamento.getValor() <= 0){
            throw new RequisicaoInvalida("Valor deve ser maior que 0");
        }
        
        if(!pagamento.getForma().equals("cheque") && 
           !pagamento.getForma().equals("dinheiro") && 
           !pagamento.getForma().equals("débito") &&
           !pagamento.getForma().equals("crédito")) {
            throw new RequisicaoInvalida("Forma de pagamento deve ser dinheiro, cheque, débito ou crédito!");
        }
        
       
        Pagamento pagamentoBanco = pagamentoDAO.save(pagamento);
        
        locatarioBanco.getPagamentos().add(pagamentoBanco);
        
        locatarioDAO.save(locatarioBanco);
        
        return pagamentoBanco;
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/{idPagamento}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPagamento(@PathVariable int idLocatario, @RequestBody Pagamento pagamento){
        final Locatario locatarioBanco = this.buscar(idLocatario);
        
        if(pagamento.getForma() == null){
            throw new RequisicaoInvalida("Você deve informar a forma de pagamento!");
        }
        if(pagamento.getData() == null || pagamento.getData().equals("")){
            throw new RequisicaoInvalida("Data deve ser preenchido!");
        }
        if(pagamento.getValor() <= 0){
            throw new RequisicaoInvalida("Valor deve ser maior que 0");
        }
               
        Pagamento pagamentoBanco = pagamentoDAO.save(pagamento);
        
        locatarioBanco.getPagamentos().add(pagamentoBanco);
        
        locatarioDAO.save(locatarioBanco);
                
    }
    
    @RequestMapping(path = "/locatarios/{idLocatario}/pagamentos/{idPagamento}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarPagamento(@PathVariable int idLocatario, @PathVariable int idPagamento){
        Locatario locatario = this.buscar(idLocatario);
        Pagamento pagamentoEncontrado = null;
        
        List<Pagamento> pagamentos = locatario.getPagamentos();
        
        for(Pagamento pagamento : pagamentos){
            if(pagamento.getId() == idPagamento){
                pagamentoEncontrado = pagamento;
            }
        }
        
        if(pagamentoEncontrado != null){
            locatario.getPagamentos().remove(pagamentoEncontrado);
        } else {
            throw new NaoEncontrado("Pagamento não encontrado");
        }
        
        locatarioDAO.save(locatario);
    }

}

