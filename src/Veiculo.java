import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import static java.lang.System.out;

public abstract class Veiculo implements Comparable, Serializable {
    private double autonomiaTotal;
    private double autonomiaAtual;
    private int vMedia;
    private float precoKm;
    private float consumoKm;
    private Historico historico;
    private Ponto ponto;
    private boolean disponibilidade;
    private String marca;
    private String dono;
    private String identificacao;
    private List<Integer> classificacao;

    public Veiculo(){
        this.historico = new Historico();
        this.ponto = new Ponto();
        this.vMedia=0;
        this.precoKm=0;
        this.consumoKm=0;
        this.disponibilidade=false;
        this.marca= "n/a";
        this.dono= "n/a";
        this.identificacao = "n/a";
        this.autonomiaAtual = 0;
        this.autonomiaTotal = 0;
        this.classificacao = new ArrayList<>();
    }

    public Veiculo(Ponto ponto, int vMedia, float precoKm, float consumoKm, Historico historico, boolean disponibilidade, String marca, String dono, String identificacao, double autonomiaAtual, double autonomiaTotal){
        this.ponto = new Ponto(ponto);
        this.vMedia=vMedia;
        this.precoKm=precoKm;
        this.consumoKm=consumoKm;
        this.historico = new Historico(historico);
        this.disponibilidade = disponibilidade;
        this.marca = marca;
        this.dono = dono;
        this.identificacao = identificacao;
        this.autonomiaTotal = autonomiaTotal;
        this.autonomiaAtual = autonomiaAtual;
        this.classificacao = new ArrayList<>();
    }

    public Veiculo(Veiculo x){
        this.ponto = x.getPonto();
        this.vMedia= x.getVMedia();
        this.precoKm= x.getPrecoKm();
        this.consumoKm= x.getConsumoKm();
        this.historico = new Historico();
        this.disponibilidade = x.getDisponibilidade();
        this.marca = x.getMarca();
        this.dono = x.getDono();
        this.identificacao = x.getIdentificacao();
        this.autonomiaTotal = x.getAutonomiaTotal();
        this.autonomiaAtual = x.getAutonomiaAtual();
    }

    //Gets
    public Ponto getPonto(){return new Ponto(this.ponto);}
    public int  getVMedia(){return this.vMedia;}
    public float  getPrecoKm(){return this.precoKm;}
    public float  getConsumoKm(){return this.consumoKm;}
    public Historico getHistorico(){return this.historico.clone();}
    public boolean getDisponibilidade(){return this.disponibilidade;}
    public String getMarca(){return this.marca;}
    public String getDono(){return this.dono;}
    public String getIdentificacao(){return this.identificacao;}
    public double getAutonomiaTotal(){return this.autonomiaTotal;}
    public double getAutonomiaAtual(){return this.autonomiaAtual;}
    public double getClassificacao(){
        OptionalDouble d = this.classificacao.stream().mapToDouble(a-> a).average();
        if(d.isPresent()){
            return d.getAsDouble();
        }
        else{
            return -1;
        }
    }
    public double getCombustivel(){
        return this.autonomiaAtual*this.consumoKm;
    }
    public abstract String getTipo();

    public void setPonto(Ponto p){this.ponto = p.clone();}
    public void setVmedia(int vMedia){this.vMedia = vMedia;}
    public void setPrecoKm(float precoKm){this.precoKm = precoKm;}
    public void setConsumoKm(float consumoKm){this.consumoKm = consumoKm;}
    public void setMarca(String marca){this.marca=marca;}
    public void setProprietario(String proprietario){this.dono=proprietario;}
    public void setDisponibilidade(boolean disponibilidade){
        this.disponibilidade=disponibilidade;
        this.checkCombustivel();
    }
    private void setAutonomiaTotal(double autonomiaTotal){this.autonomiaTotal = autonomiaTotal;}
    public void setAutonomiaAtual(double autonomia){
//        double aux= this.autonomiaTotal-this.autonomiaAtual;
//        this.autonomiaAtual = (aux<autonomia)?autonomia:this.autonomiaTotal;
        this.autonomiaAtual = autonomia;
    }
    public void addClassificacao(int classificacao){
        this.classificacao.add(classificacao);
    }
    public void addHistorico(Aluguer a){
        this.historico.addAluguer(a);
    }
    public boolean checkCombustivel(){
        if(this.autonomiaAtual/this.autonomiaTotal < 0.1) {this.disponibilidade = false; return true;}
        return false;
    }
    public boolean verificaDistanciaAutonomia(Ponto p){
        this.checkCombustivel();
        double dist;
        dist = this.ponto.distancia(p);
        return (dist<=this.autonomiaAtual);
    }
    public double totalFaturado(){
        return this.historico.faturacaoTotal();
    }
    public double totalDistancia(){return this.historico.kmsPercorridos();}

    public boolean equals(Object o){
        if(o==this) return true;
        if(o==null || o.getClass()!=this.getClass()) return false;
        Veiculo aux = (Veiculo) o;
        return(aux.getVMedia()==this.getVMedia()
                && aux.getPrecoKm()==this.getPrecoKm()
                && aux.getConsumoKm()==this.getConsumoKm()
                && aux.getClassificacao()==this.getClassificacao()
                && aux.getPonto().equals(this.ponto)
                && aux.getHistorico().equals(this.historico)
                && aux.getDisponibilidade() == this.disponibilidade
                && aux.getMarca().equals(this.marca)
                && aux.getDono().equals(this.dono)
                && aux.getAutonomiaAtual()==this.autonomiaAtual)
                && aux.getAutonomiaTotal()==this.getAutonomiaTotal();
        // falta testar as classificações
    }
    public abstract Veiculo clone();
    public String toString() {
        return "Veiculo{" +
                "autonomiaTotal=" + autonomiaTotal +
                ", autonomiaAtual=" + autonomiaAtual +
                ", vMedia=" + vMedia +
                ", precoKm=" + precoKm +
                ", Historico=" + historico +
                ", consumoKm=" + consumoKm +
                ", ponto=" + ponto +
                ", disponibilidade=" + disponibilidade +
                ", marca='" + marca + '\'' +
                ", dono='" + dono + '\'' +
                ", identificacao='" + identificacao + '\'' +
                '}';
    }
    public int compareTo(Object o) {
        if(o == null || o.getClass()!= this.getClass()) return -1;
        Veiculo u = (Veiculo) o;
        if(this.precoKm < u.getPrecoKm()) return -1;
        if(this.precoKm > u.getPrecoKm()) return 1;
        return this.identificacao.compareTo(u.getIdentificacao());
    }
}
