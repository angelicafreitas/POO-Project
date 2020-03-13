import java.io.Serializable;
import java.time.LocalDateTime;

public class Pedido implements Serializable {
    private double distrezaDoCliente;
    private LocalDateTime time;
    private String proprietario;
    private String cliente;
    private String veiculo;
    private Ponto inicio;
    private Ponto destino;
    private Ponto localizacaoCliente;

    public Pedido(){
        this.distrezaDoCliente = -1;
        this.time = LocalDateTime.now();
        this.proprietario = "N/A";
        this.cliente = "N/A";
        this.veiculo = "N/A";
        this.inicio = new Ponto(0,0);
        this.destino = new Ponto(0,0);
        this.localizacaoCliente = new Ponto(0,0);
    }

    public Pedido(String cliente, String proprietario, String veiculo, Ponto destino, Ponto inicio, LocalDateTime data, Ponto localizacaoCliente, double distrezaDoCliente){
        this.distrezaDoCliente = distrezaDoCliente;
        this.time = LocalDateTime.now();
        this.proprietario = proprietario;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.inicio = inicio.clone();
        this.destino = destino.clone();
        this.localizacaoCliente = localizacaoCliente.clone();

    }

    public Pedido(Pedido p){
        this.distrezaDoCliente = p.getDistrezaDoCliente();
        this.time = p.getTime();
        this.proprietario = p.getProprietario();
        this.cliente = p.getCliente();
        this.veiculo = p.getVeiculo();
        this.inicio = p.getInicio();
        this.destino = p.getDestino();
        this.localizacaoCliente = p.getLocalizacaoCliente();
    }

    public String getProprietario(){return this.proprietario;}
    public void setProprietario(String proprietario){this.proprietario = proprietario;}
    public String getCliente() {
        return this.cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getVeiculo() {
        return this.veiculo;
    }
    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
    public Ponto getInicio(){return this.inicio.clone();}
    public void setInicio(Ponto inicio){this.inicio = inicio.clone();}
    public Ponto getDestino() {
        return this.destino.clone();
    }
    public void setDestino(Ponto destino) {
        this.destino = destino.clone();
    }
    public LocalDateTime getTime(){return this.time;}
    public void setTime(LocalDateTime time){this.time = time;}
    public Ponto getLocalizacaoCliente(){return this.localizacaoCliente.clone();}
    public double getDistrezaDoCliente() {
        return distrezaDoCliente;
    }
    public void setDistrezaDoCliente(double distrezaDoCliente) {
        this.distrezaDoCliente = distrezaDoCliente;
    }

    public void setLocalizacaoCliente(Ponto localizacaoCliente){this.localizacaoCliente = localizacaoCliente.clone();}


    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Pedido p = (Pedido) o;
        return this.distrezaDoCliente == p.getDistrezaDoCliente() &&
                this.time.equals(p.getTime()) &&
                this.proprietario.equals(p.getProprietario()) &&
                this.cliente.equals(p.getCliente()) &&
                this.veiculo.equals(p.getVeiculo()) &&
                this.inicio.equals(p.getInicio()) &&
                this.destino.equals(p.getDestino()) &&
                this.localizacaoCliente.equals(p.getLocalizacaoCliente());
    }
    public Pedido clone(){
       return new Pedido(this);
    }
    public String toString(){
        return "Cliente: " + this.cliente +
                "\nDistreza do Cliente: " + this.distrezaDoCliente +
                "\nProprietário: " + this.proprietario +
                "\nVeiculo: " + this.veiculo +
                "\nInicio: " + this.inicio +
                "\nDestino: " + this.destino.toString() +
                "\nLocalizaçãoCliente: " + this.localizacaoCliente;
    }
}
