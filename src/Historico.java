import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import static java.lang.System.out;

public class Historico implements Serializable {
    private List<Aluguer> historico;

    public Historico(){
        this.historico = new ArrayList<>();
    }
    public Historico(Aluguer a){
        this.historico = new ArrayList<>();
        this.historico.add(a.clone());
    }
    public Historico(Historico h){
        this.historico = new ArrayList<>();
        h.getAlugueres().forEach(a -> this.historico.add(a.clone()));
    }

    public List<Aluguer> getAlugueres(){
        List<Aluguer> l = new ArrayList<>();
        this.historico.forEach(a -> l.add(a.clone()));
        return l;
    }
    public List<Aluguer> getAlugueres(LocalDateTime start, LocalDateTime finish){
        List<Aluguer> l = new ArrayList<>();
        this.historico.stream()
                .filter(a -> a.getData().isAfter(start) && a.getData().isBefore(finish))
                .forEach(b -> l.add(b.clone()));
        return l;
    }
    public void addAluguer(Aluguer a){
        this.historico.add(a.clone());
    }
    public double faturacaoTotal(){
        double total = 0;
        for(Aluguer a : this.historico){
            total += a.getCusto();
        }
        return total;
    }
    public double kmsPercorridos(){
        double total = 0;
        for(Aluguer a : this.historico){
            total += a.getDistancia();
        }
        return total;
    }
    public int getSize(){
        return this.historico.size();
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Historico c = (Historico) o;
        if(c.getAlugueres().size() != this.historico.size()) return false;
        for(Aluguer a : c.getAlugueres()){
            if(!this.historico.contains(a)) return false;
        }
        return true;
    }
    public Historico clone(){
        return new Historico(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Aluguer a : this.historico){
            sb.append("->").append(a.toString());
        }
        return sb.toString();
    }
}
