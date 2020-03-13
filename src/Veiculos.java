import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Veiculos implements Serializable {
    private Map<String, Veiculo> viaturas;

    public Veiculos(){
        this.viaturas = new HashMap<>();
    }
    public Veiculos(Veiculo u){
        this.viaturas = new HashMap<>();
        this.viaturas.put(u.getIdentificacao(),u.clone());
    }
    public Veiculos(Veiculos u){
        this.viaturas = new HashMap<>();
        u.getVeiculos().stream().map(l -> this.viaturas.put(l.getIdentificacao(), l.clone()));
    }

    public Veiculo getVeiculo(String id){return this.viaturas.get(id).clone();}
    public List<Veiculo> getVeiculos(){
        return this.viaturas.values().stream().map(Veiculo::clone).collect(Collectors.toList());
    }
    public void addViaturas(Veiculo u){
        this.viaturas.put(u.getIdentificacao(), u.clone());
    }
    public boolean existeVeiculo(String id){
        return this.viaturas.containsKey(id);
    }
    public void abasteceVeiculo(String identificacao, int litros){
        Veiculo v = this.viaturas.get(identificacao);
        if(v == null) return;
        if(litros == -1){
            v.setAutonomiaAtual(v.getAutonomiaTotal());
        }
        else v.setAutonomiaAtual(v.getAutonomiaAtual() + (litros / v.getConsumoKm()));
    }
    public void alteraPreco(String id, float preco){
        this.viaturas.get(id).setPrecoKm(preco);
    }
    public void setDisponibilidade(String id, boolean disponibilidade) throws VeiculoNotFoundException{
        try{
            this.viaturas.get(id).setDisponibilidade(disponibilidade);
        }catch(NullPointerException e){
            throw new VeiculoNotFoundException(id);
        }
    }
    public List<String> veiculosPrecisamAbastecer(Set<String> veiculos){
        try{
            HashSet<Veiculo> hs = new HashSet<>();
            for(String s : veiculos){
                hs.add(this.viaturas.get(s));
            }
            return hs.stream().filter(Veiculo::checkCombustivel).map(l -> l.getIdentificacao()).collect(Collectors.toList());
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    public void updateVeiculo(Pedido p){
        Veiculo v = this.viaturas.get(p.getVeiculo());
        v.setAutonomiaAtual(v.getAutonomiaAtual() - p.getDestino().distancia(p.getInicio()));
        v.setPonto(p.getDestino());
        v.checkCombustivel();
    }
    public void addAluguer(String id, Aluguer a){
        try{
            this.viaturas.get(id).addHistorico(a);
        }catch(Exception e){
            return;
        }
    }
    public void addClassificacao(String id, int classificacao){
        this.viaturas.get(id).addClassificacao(classificacao);
    }
    public double getClassificacao(String id){
        return this.viaturas.get(id).getClassificacao();
    }
    public List<Veiculo> carroMaisProximo(String tipo, Ponto p, Ponto destino){
        double r=Double.MAX_VALUE,aux;
        Set<Veiculo> ret = new TreeSet<>((l1 ,l2) -> {
            if(l1.getPonto().distancia(p) < l2.getPonto().distancia(p))
                return -1;
            if(l2.getPonto().distancia(p) < l1.getPonto().distancia(p))
                return 1;
            return l1.getIdentificacao().compareTo(l2.getIdentificacao());
        });
        for(Veiculo v: this.viaturas.values()){
            aux=v.getPonto().distancia(p);
            if(aux<r && v.getDisponibilidade() && v.verificaDistanciaAutonomia(destino) && (v.getTipo().equals(tipo.toUpperCase()))){
                r=aux;
                ret.add(v.clone());
            }
        }
        return new ArrayList<>(ret);
    }
    public List<Veiculo> carroMaisBarato(String tipo, Ponto p){
        double r=Double.MAX_VALUE;
        Set<Veiculo> ret = new TreeSet<>();
        for(Veiculo v: this.viaturas.values()){
            if(v.getPrecoKm()<r && v.getDisponibilidade() && v.verificaDistanciaAutonomia(p) && (v.getTipo().equals(tipo.toUpperCase()))){
                ret.add(v.clone());
            }
        }
        return new ArrayList<>(ret);
    }
/*    public Veiculo carroMaisBarato(Ponto p,double distancia){
        double r=Double.MAX_VALUE,aux;
        Veiculo resultado = null;
        for(Veiculo v: this.viaturas.values()){
            aux=v.getPonto().distancia(p);
            if(aux<=distancia && v.getPrecoKm()<r && v.getDisponibilidade() && v.verificaDistanciaAutonomia(p)){
                r=v.getPrecoKm();
                resultado = v.clone();
            }
        }
        return resultado;
    }*/
    public Veiculo carroEspecifico(String id, Ponto destino){
        Veiculo v = this.viaturas.get(id).clone();
        if(v.getDisponibilidade() && v.verificaDistanciaAutonomia(destino)) return v.clone();
        else return null;
    }
/*    public String carroAutonomia(double autonomia){
        double aux;
        Veiculo resultado = new Carro();
        for(Veiculo v: this.viaturas.values()){
            if(v.getDisponibilidade()==true && v.getAutonomiaAtual() == autonomia){
                resultado = v.clone();
                break;
            }
        }
        return resultado.getIdentificacao();
    }*/

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Veiculos u = (Veiculos) o;
        return this.getVeiculos().size() == u.getVeiculos().size()
                && this.getVeiculos().stream().filter(l -> u.getVeiculos().contains(l)).count() == this.getVeiculos().stream().count();
    }
    public Veiculos clone(){
        return new Veiculos(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Veiculo a: this.viaturas.values()) {
            sb.append(a.toString());
        }
        return sb.toString();
    }

}
