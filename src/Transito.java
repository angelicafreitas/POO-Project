import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Transito implements Serializable {
    Set<Circulo> nodos;
    public static final double numNodos = 200;

    public Transito(){
        this.nodos = new HashSet<>();
        for(int i = 0; i < numNodos; i++){
            this.nodos.add(new Circulo());
        }
    }

    public void updateTransito(){
        for(Circulo c : this.nodos){
            c.update();
        }
    }

    public double percentagemTransito(Ponto inicio, Ponto fim){
        double transito = 0;
        for(Circulo c : nodos){
            transito = c.percentagemTransito(inicio, fim);
            if(transito != 0) return transito;
        }
        return transito;
    }
}
