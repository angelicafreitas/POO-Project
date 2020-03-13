import java.io.Serializable;
import java.util.*;

public class Proprietario extends Utilizador implements Serializable {
    private Set<String> viaturas;

    public Proprietario(){
        super();
        this.viaturas = new HashSet<>();
    }
    public Proprietario(DadosPessoais dados, Historico hist, Set<String> viaturas) {
        super(dados, hist);
        this.viaturas= new HashSet<>();
        this.viaturas.addAll(viaturas);
    }
    public Proprietario(DadosPessoais dados) {
        super(dados);
        this.viaturas = new HashSet<>();
    }
    public Proprietario(Proprietario aux) {
        super(aux.getDadosPessoais(), aux.getHistorico());
        this.viaturas = aux.getViaturas();
    }

    public void addVeiculo(String identificacao){
        this.viaturas.add(identificacao);
    }
    public void removeVeiculo(String identificacao){
        this.viaturas.remove(identificacao);
    }
    public Set<String> getViaturas(){
        return new HashSet<>(this.viaturas);
    }

    public boolean equals(Object o){
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Proprietario p = (Proprietario) o;
        return (super.equals(p)
                && this.viaturas.equals(p.getViaturas()));
    }
    public Proprietario clone(){
        Proprietario p = new Proprietario(super.getDadosPessoais());
        p.viaturas = this.getViaturas();
        return p;
    }

}
