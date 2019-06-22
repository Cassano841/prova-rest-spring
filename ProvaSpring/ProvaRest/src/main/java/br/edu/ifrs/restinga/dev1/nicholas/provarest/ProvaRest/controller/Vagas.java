package br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.controller;

import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.dev1.nicholas.provarest.ProvaRest.modelo.dao.VagaDAO;
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

@RestController
@RequestMapping("/api")
public class Vagas {
    
    @Autowired
    VagaDAO vagaDAO;
    
    /*Pesquisas Vagas*/
    
    @RequestMapping(path = "/vagas/pesquisar/andar", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Vaga> pesquisaAndar(@RequestParam int andar) {        
        return vagaDAO.findByAndar(andar);
    }
    
    @RequestMapping(path = "/vagas/pesquisar/localVaga", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Vaga> pesquisaAndar(@RequestParam String localVaga) {        
        return vagaDAO.findByLocal(localVaga);
    }
    
    /* CRUD VAGAS */
    
    @RequestMapping(path = "/vagas/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Vaga> listarVagas(){
        return vagaDAO.findAll();
    }
    
    @RequestMapping(path = "/vagas/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Vaga buscarVaga(@PathVariable int id) {
        final Optional<Vaga> findById = vagaDAO.findById(id);
        if(findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Vaga não encontrada!");
        }
    }
    
    @RequestMapping(path = "/vagas/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Vaga cadastrarVaga(@RequestBody Vaga vaga) {
        
        if (vaga.getLocal() == "" || vaga.getLocal() == null){
            throw new RequisicaoInvalida("Campo Local não pode ser vazio");
        }
        if (!vaga.getLocal().matches("[0-9]{2}[A-Za-z]{1}")){
            throw new RequisicaoInvalida("Padrão de Local deve ser 2 números e 1 letra!");
        }
        if (vaga.getAndar() < 1 || vaga.getAndar() > 10){
            throw new RequisicaoInvalida("Campo Andar deve ser entre 1 e 10");
        }
        if(!vagaDAO.findByAndar(vaga.getAndar()).isEmpty()&&
           !vagaDAO.findByLocal(vaga.getLocal()).isEmpty()){
            throw new RequisicaoInvalida("Local e andar já cadastrados!");
        }
        
        Vaga vagaBanco = vagaDAO.save(vaga);
        
        return vagaBanco;
    }
    
    @RequestMapping(path = "/vagas/{idVaga}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarVaga(@PathVariable int idVaga, @RequestBody Vaga vaga) {
        final Vaga vagaBanco = this.buscarVaga(idVaga);
        
        if (vaga.getLocal() == "" || vaga.getLocal() == null){
            throw new RequisicaoInvalida("Campo Local não pode ser vazio");
        }
        if (!vaga.getLocal().matches("[0-9]{2}[A-Za-z]{1}")){
            throw new RequisicaoInvalida("Padrão de Local deve ser 2 números e 1 letra!");
        }
        if (vaga.getAndar() < 1 || vaga.getAndar() > 10){
            throw new RequisicaoInvalida("Campo Andar deve ser entre 1 e 10");
        }
        if(!vagaDAO.findByAndar(vaga.getAndar()).isEmpty()&&
           !vagaDAO.findByLocal(vaga.getLocal()).isEmpty()){
            throw new RequisicaoInvalida("Local e andar já cadastrados!");
        }
        
        vagaBanco.setLocal(vaga.getLocal());
        vagaBanco.setAndar(vaga.getAndar());
        
        vagaDAO.save(vagaBanco);

    }
    
    @RequestMapping(path="/vagas/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarVaga(@PathVariable int id) {
        if(vagaDAO.existsById(id)) {
            vagaDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("Vaga não encontrada!");
        }
    }
}
