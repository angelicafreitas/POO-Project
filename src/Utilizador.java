import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public abstract class Utilizador implements Serializable {
    private DadosPessoais dados;
    private Historico historico;
    private List<Integer> classificacoes;

    public Utilizador(){
        this.dados = new DadosPessoais();
        this.historico = new Historico();
        this.classificacoes = new ArrayList<>();
    }

    public Utilizador(DadosPessoais dados){
        this.dados = new DadosPessoais(dados);
        this.historico = new Historico();
        this.classificacoes = new ArrayList<>();
    }

    public Utilizador(DadosPessoais dados, Historico hist) {
        this.dados = new DadosPessoais(dados);
        this.historico = new Historico(hist);
        this.classificacoes = new ArrayList<>();
    }

    public boolean checkPassword(String password){
        return this.dados.checkPassword(password);
    }
    public DadosPessoais getDadosPessoais(){
        return this.dados.clone();
    }
    public List<Aluguer> getHistoricoInterval(LocalDateTime inicio, LocalDateTime fim){
        return this.historico.getAlugueres(inicio,fim);
    }
    public Historico getHistorico(){
        return new Historico(this.historico);
    }
    public double getClassificacao(){
        OptionalDouble d = this.classificacoes.stream().mapToDouble(a-> a).average();
        if(d.isPresent()){
            return d.getAsDouble();
        }
        else{
            return -1;
        }
    }
    public void addClassificacao(int classificacao){
        this.classificacoes.add(classificacao);
    }
    public void addAluguer(Aluguer a){
        this.historico.addAluguer(a);
    }


    public boolean equals(Object o){
        if(o==this) return true;
        if(o==null || o.getClass()!=this.getClass()) return false;
        Utilizador d = (Utilizador) o;
        return (d.getHistorico().equals(this.historico) && d.getDadosPessoais().equals(this.dados));
    }
    public abstract Utilizador clone();
    public String toString(){
        return dados.toString();
    }

}
