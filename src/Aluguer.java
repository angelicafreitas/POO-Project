import java.io.Serializable;
import java.time.LocalDateTime;

public class Aluguer implements Serializable {
    private String proprietario;
    private String cliente;
    private String idCarro;
    private double distancia;
    private double custo;
    private double tempo;
    private String meteorologia;
    private Ponto inicio;
    private Ponto destino;
    private double transito;
    private LocalDateTime data;

    public Aluguer(String proprietario, String cliente, String idCarro, double distancia, double custo, double tempo, String meteorologia, Ponto inicio, Ponto destino, double transito, LocalDateTime data) {
        this.proprietario = proprietario;
        this.cliente = cliente;
        this.idCarro = idCarro;
        this.distancia = distancia;
        this.custo = custo;
        this.tempo = tempo;
        this.meteorologia = meteorologia;
        this.meteorologia = meteorologia;
        this.inicio = inicio.clone();
        this.destino = destino.clone();
        this.transito = transito;
        this.data = data;
    }

    public Aluguer(){
        this.proprietario = "N/A";
        this.cliente = "N/A";
        this.idCarro = "N/A";
        this.distancia = 0;
        this.custo = 0;
        this.tempo = 0;
        this.meteorologia = "N/A";
        this.inicio = new Ponto();
        this.destino = new Ponto();
        this.transito = 0;
        this.data = LocalDateTime.now();
    }

    public Aluguer(Aluguer a){
        this.proprietario = a.getProprietario();
        this.cliente = a.getCliente();
        this.idCarro = a.getIdCarro();
        this.distancia = a.getDistancia();
        this.custo = a.getCusto();
        this.tempo = a.getTempo();
        this.meteorologia = a.getMeteorologia();
        this.inicio = a.getInicio();
        this.destino = a.getDestino();
        this.transito = a.getTransito();
        this.data = a.getData();
    }

    public String getProprietario() {
        return proprietario;
    }
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getIdCarro() {
        return idCarro;
    }
    public void setIdCarro(String idCarro) {
        this.idCarro = idCarro;
    }
    public double getDistancia() {
        return distancia;
    }
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    public double getCusto() {
        return custo;
    }
    public void setCusto(double custo) {
        this.custo = custo;
    }
    public Ponto getInicio() {
        return inicio.clone();
    }
    public void setInicio(Ponto inicio) {
        this.inicio = inicio.clone();
    }
    public Ponto getDestino() {
        return destino.clone();
    }
    public void setDestino(Ponto destino) {
        this.destino = destino.clone();
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public double getTempo() {
        return tempo;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
    public String getMeteorologia() {
        return meteorologia;
    }
    public void setMeteorologia(String meteorologia) {
        this.meteorologia = meteorologia;
    }
    public double getTransito() {
        return transito;
    }
    public void setTransito(double transito) {
        this.transito = transito;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Aluguer a = (Aluguer) o;
        return this.proprietario.equals(a.getProprietario()) &&
                this.cliente.equals(a.getCliente()) &&
                this.idCarro.equals(a.getIdCarro()) &&
                this.distancia == a.getDistancia() &&
                this.custo == a.getCusto() &&
                this.tempo == a.getTempo() &&
                this.meteorologia.equals(a.getMeteorologia()) &&
                this.inicio.equals(a.getInicio()) &&
                this.destino.equals(a.getDestino()) &&
                this.transito == a.getTransito() &&
                this.data.equals(a.getData());
    }
    public Aluguer clone(){
        return new Aluguer(this);
    }
    public String toString() {
        return "Aluguer{" +
                "proprietario='" + proprietario + '\'' +
                ", cliente='" + cliente + '\'' +
                ", idCarro='" + idCarro + '\'' +
                ", distancia=" + distancia +
                ", custo=" + custo +
                ", tempo=" + tempo +
                ", Meteorologia=" + meteorologia +
                ", inicio=" + inicio +
                ", destino=" + destino +
                ", transito=" + transito +
                ", data=" + data +
                '}';
    }
}
