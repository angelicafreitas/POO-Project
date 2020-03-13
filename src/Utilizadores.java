import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Utilizadores implements Serializable {
    private Map<String, Utilizador> users;

    public Utilizadores(){
        this.users = new HashMap<>();
    }
    public Utilizadores(Utilizador u){
        this.users = new HashMap<>();
        this.users.put(u.getDadosPessoais().getEmail(), u.clone());
    }
    public Utilizadores(Utilizadores u){
        this.users = new HashMap<>();
        u.getUsers().forEach(l -> this.users.put(l.getDadosPessoais().getEmail(), l.clone()));
    }

    public Set<String> getVeiculos(String id){
        try{
            Proprietario p = (Proprietario) users.get(id);
            return p.getViaturas();
        }catch(Exception e){
            return new HashSet<>();
        }
    }
    public void addVeiculo(Veiculo v){
        Proprietario p = (Proprietario) users.get(v.getDono());
        p.addVeiculo(v.getIdentificacao());
    }
    public void addUser(Utilizador u){
        this.users.put(u.getDadosPessoais().getNif(), u.clone());
    }
    public boolean userLogin(String user, String password) throws UserNotFoundException{
        try{
            return users.get(user).checkPassword(password);

        }catch(Exception e){
            throw new UserNotFoundException(user);
        }
    }
    public String getNif(String email)throws UserNotFoundException{
        try{
            Utilizador u = this.users.values().stream().filter(l -> l.getDadosPessoais().getEmail().equals(email)).collect(Collectors.toList()).get(0);
            return u.getDadosPessoais().getNif();
        }catch(NullPointerException e){
            throw new UserNotFoundException(email);
        }
    }
    public boolean existeUtilizador(String id){
        return this.users.containsKey(id);
    }
    public Utilizador getUser(String id) {
            return users.get(id).clone();
    }
    public List<Utilizador> getUsers(){
        return this.users.values().stream().map(Utilizador::clone).collect(Collectors.toList());
    }
    public void updatePosicaoCliente(String cliente, Ponto p){
        Cliente c = (Cliente) this.users.get(cliente);
        c.setLocalizacao(p.clone());
    }
    public void addAluguer(String id, Aluguer a){
        try{
            this.users.get(id).addAluguer(a);
        }catch(Exception e){
            return;
        }
    }
    public void addClassificacao(String id, int classificacao){
        this.users.get(id).addClassificacao(classificacao);
    }
    public double getClassificacao(String id){
        return this.users.get(id).getClassificacao();
    }
    public List<String> updateClassificacoes(String cliente){
        Cliente c = (Cliente) this.users.get(cliente);
        List<Aluguer> list = c.getHistorico().getAlugueres();
        List<String> ret = new ArrayList<>();
        for(int i = c.getPrevHistoricoSize(); i < list.size(); i++){
            ret.add(list.get(i).getIdCarro());
        }
        c.setPrevHistoricoSize(list.size());
        return ret;
    }
    public List<Cliente> clienteMaisAtivos(){
        Set<Cliente> ret = new TreeSet<>();
        this.users.values().stream().filter(l -> l instanceof Cliente).forEach(c -> ret.add((Cliente) c));
        return ret.stream().map(Cliente::clone).collect(Collectors.toList());
    }
    public List<Cliente> clienteMaisAtivosKms(){
        Set<Cliente> ret = new TreeSet<>((a1, a2) -> {
            if(a1.totalKms() > a2.totalKms()) return -1;
            else if(a2.totalKms() > a1.totalKms()) return 1;
            else return a1.getDadosPessoais().getNif().compareTo(a2.getDadosPessoais().getNif());
        });
        this.users.values().stream().filter(l -> l instanceof Cliente).forEach(c -> ret.add((Cliente) c));
        return ret.stream().map(Cliente::clone).collect(Collectors.toList());
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Utilizadores u = (Utilizadores) o;
        return this.getUsers().size() == u.getUsers().size()
                && this.getUsers().stream().filter(l -> u.getUsers().contains(l)).count() == this.getUsers().stream().count();
    }
    public Utilizadores clone(){
        return new Utilizadores(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Utilizador a: this.users.values()) {
            sb.append(a.toString());
        }
        return sb.toString();
    }

}
