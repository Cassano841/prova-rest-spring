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
    
    @RequestMapping(path = "/vagas/pesquisar/andar", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Vaga> pesquisaLocal(@RequestParam(required = false) Integer andar) {        
        return vagaDAO.findByAndar(andar);
    }
    
    
    @RequestMapping(path = "/vagas/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable <Vaga> listar(){
        return vagaDAO.findAll();
    }
    
    @RequestMapping(path = "/vagas/{idVaga}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Vaga buscarVaga(@PathVariable int idVaga){
        final Optional<Vaga> findById = vagaDAO.findById(idVaga);
        if(findById.isPresent()){
            return findById.get();
        } else {
            throw new NaoEncontrado("Id não encontrado!");
        }
    }
    
    @RequestMapping(path = "/vagas/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Vaga cadastrarVaga(@RequestBody Vaga vaga){
        
        /*
        Vaga validadorAndar = vagaDAO.findByAndar(vaga.getAndar());
        
        if(validadorAndar == null){
            throw new RequisicaoInvalida("Deu ruim parça!");
        }
        */           
        if (vaga.getLocal() == "" || vaga.getLocal() == null){
            throw new RequisicaoInvalida("Campo Local não pode ser vazio");
        }
        if (!vaga.getLocal().matches("[0-9]{2}[A-Za-z]{1}")){
            throw new RequisicaoInvalida("Padrão de Local deve ser 2 números e 1 letra!");
        }
        if (vaga.getAndar() < 1 || vaga.getAndar() > 10){
            throw new RequisicaoInvalida("Campo Andar deve ser entre 1 e 10");
        }
                
        Vaga vagaBanco = vagaDAO.save(vaga);
        
        return vagaBanco;
    }
    
    @RequestMapping(path = "/vagas/{idVaga}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarVaga(@PathVariable int idVaga, @RequestBody Vaga vaga){
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
        
        vagaBanco.setLocal(vaga.getLocal());
        vagaBanco.setAndar(vaga.getAndar());
        
        vagaDAO.save(vagaBanco);
    }
    
    @RequestMapping(path = "/vagas/{idVaga}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarVaga(@PathVariable int idVaga){
        if(vagaDAO.existsById(idVaga)){
            vagaDAO.deleteById(idVaga);
        } else  {
            throw new NaoEncontrado("Id não encontrado para deleção");
        }
    }
}
