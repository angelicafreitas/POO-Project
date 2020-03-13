import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;

public class UMCModel implements Serializable {
    private Veiculos veics;
    private Utilizadores users;
    private Map<String, List<Pedido>> pedidos;
    private Transito transito;

    public UMCModel(){
        this.veics = new Veiculos();
        this.users = new Utilizadores();
        this.pedidos = new HashMap<>();
        this.transito = new Transito();
    }

    public void addUtilizador(Utilizador u){
        users.addUser(u);
    }
    public boolean userLogin(String user, String password) throws UserNotFoundException{
        return users.userLogin(user, password);
    }
    public List<Pedido> showPedidos(String user){
        List<Pedido> l = new ArrayList<>();
        try{
            pedidos.get(user).forEach(p -> l.add(p.clone()));
            return l;
        }catch(Exception e){
            return l;
        }
    }
    public List<String> checkCombustivelVeiculos(String dono){
        Set<String> v = users.getVeiculos(dono);
        return veics.veiculosPrecisamAbastecer(v);
    }
    public void abasteceVeiculo(String id, int litros){
        veics.abasteceVeiculo(id, litros);
    }
    public void setDisponibilidade(String id, boolean disponibilidade) throws VeiculoNotFoundException{
        this.veics.setDisponibilidade(id, disponibilidade);
    }
    public void alteraPreco(String id, float preco){veics.alteraPreco(id, preco);}
    public void adicionaVeiculo(Veiculo v){
        users.addVeiculo(v.clone());
        veics.addViaturas(v.clone());
    }
    public void addPedido(Pedido p){
        if(pedidos.containsKey(p.getProprietario())){
            pedidos.get(p.getProprietario()).add(p);
        }
        else{
            pedidos.put(p.getProprietario(), new ArrayList<>());
            pedidos.get(p.getProprietario()).add(p);
        }
    }
    public void addClassificacao(String id, int classificacao, int modo){
        if(modo == 1){
            veics.addClassificacao(id, classificacao);
        }
        else if(modo == 2){
            users.addClassificacao(id, classificacao);
        }
    }
    public double getClassificacao(String id, int modo){
        double classificacao = 0;
        if(modo == 1){
            classificacao = veics.getClassificacao(id);
        }
        else if(modo == 2){
            classificacao = users.getClassificacao(id);
        }
        return classificacao;
    }
    public void rejeitarPedido(Pedido p){
        pedidos.get(p.getProprietario()).remove(p);
    }
    public void aceitarPedido(Pedido p){
        veics.updateVeiculo(p);
        users.updatePosicaoCliente(p.getCliente(), p.getDestino());
        if(pedidos.containsKey(p.getProprietario()) && pedidos.get(p.getProprietario()).contains(p)) {pedidos.get(p.getProprietario()).remove(p);}
        Veiculo auxv = veics.getVeiculo(p.getVeiculo());
        double distancia = p.getInicio().distancia(p.getDestino());
        double custo = auxv.getPrecoKm()*(p.getInicio().distancia(p.getDestino()));
        int meteorologia = RandomEvents.randomWeather();
        double t = 100*transito.percentagemTransito(p.getInicio(), p.getDestino());
        double tempo = RandomEvents.travelTimeCalculator(distancia, auxv.getVMedia(), meteorologia, p.getDistrezaDoCliente(), t);
        Aluguer a = new Aluguer(p.getProprietario(), p.getCliente(), p.getVeiculo(), distancia, custo, tempo, RandomEvents.intToStringMeteorologia(meteorologia), p.getInicio(), p.getDestino(), t,p.getTime());
        veics.addAluguer(p.getVeiculo(), a);
        users.addAluguer(p.getCliente(), a);
        transito.updateTransito();
    }
    public Utilizador getUser(String id){
        return users.getUser(id);
    }
    public Veiculo getVeiculo(String id){
        return this.veics.getVeiculo(id);
    }
    public boolean existeUtilizador(String id){
        return this.users.existeUtilizador(id);
    }
    public boolean existeVeiculo(String id){return this.veics.existeVeiculo(id);}
    public void updatePosicaoCliente(String cliente, Ponto p){
        users.updatePosicaoCliente(cliente, p);
    }
    public List<Veiculo> carroMaisProximo(String tipo, Ponto d, Ponto p){
        return veics.carroMaisProximo(tipo, d, p);
    }
    public List<Veiculo> carroMaisBarato(String tipo, Ponto p){
        return veics.carroMaisBarato(tipo, p);
    }
    public Veiculo carroEspecifico(String id, Ponto p){return this.veics.carroEspecifico(id, p);}
    public List<Veiculo> getVeiculos(String user){
        Set<String> l = new HashSet<>();
        this.users.getVeiculos(user).forEach(v -> l.add(v));
        List<Veiculo> ret = new ArrayList<>();
        for(String s : l){
            ret.add(veics.getVeiculo(s).clone());
        }
        return ret;
    }
    public List<Cliente> clienteMaisAtivos(){return this.users.clienteMaisAtivos();}
    public List<Cliente> clienteMaisAtivosKms(){return this.users.clienteMaisAtivosKms();}
    public String getNif(String email)throws UserNotFoundException{
        return this.users.getNif(email);
    }
    public List<String> updateClassificacoes(String cliente){return users.updateClassificacoes(cliente);}

}
