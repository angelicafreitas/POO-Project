import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Utilizador implements Serializable, Comparable {
    private Ponto localizacao;
    private int prevHistoricoSize;
    private double distrezaCondutor;

    public Cliente(){
        super();
        this.localizacao = new Ponto();
        this.prevHistoricoSize = 0;
        this.distrezaCondutor = -1;
    }
    public Cliente(Ponto p, DadosPessoais dados, double distrezaCondutor){
        super(dados);
        this.localizacao = p.clone();
        this.prevHistoricoSize = 0;
        this.distrezaCondutor = distrezaCondutor;
    }
    public Cliente(DadosPessoais dados, double distrezaCondutor){
        super(dados);
        this.localizacao = new Ponto(0, 0);
        this.prevHistoricoSize = 0;
        this.distrezaCondutor = distrezaCondutor;
    }
    public Cliente(Cliente c){
        super(c.getDadosPessoais(),c.getHistorico());
        this.localizacao = c.getPonto();
        this.prevHistoricoSize = c.getPrevHistoricoSize();
        this.distrezaCondutor = c.getDistrezaCondutor();
    }

    public int getPrevHistoricoSize() {
        return prevHistoricoSize;
    }
    public void setPrevHistoricoSize(int prevHistoricoSize) {
        this.prevHistoricoSize = prevHistoricoSize;
    }
    public Ponto getPonto(){return this.localizacao.clone();}
    public double getDistrezaCondutor() {
        return distrezaCondutor;
    }
    public void setDistrezaCondutor(double distrezaCondutor) {
        this.distrezaCondutor = distrezaCondutor;
    }
    public void setLocalizacao(Ponto localizacao){this.localizacao = localizacao.clone();}
    public double totalFaturado(){
        return this.getHistorico().faturacaoTotal();
    }
    public double totalKms(){
        return this.getHistorico().getAlugueres().stream().mapToDouble(Aluguer::getDistancia).sum();
    }

    public Cliente clone(){
        return new Cliente(this);
    }
    public boolean equals(Object o){
        if(o==this) return true;
        if(o==null || o.getClass()!=this.getClass()) return false;
        Cliente c = (Cliente) o;
        return (super.equals(c) && this.getPonto().equals(c.getPonto()));
    }
    public int compareTo(Object o){
        Cliente c = (Cliente) o;
        if(this.getHistorico().getSize() > c.getHistorico().getSize()){
            return -1;
        }
        else if(this.getHistorico().getSize() < c.getHistorico().getSize()){
            return 1;
        }
        return this.getDadosPessoais().getNif().compareTo(c.getDadosPessoais().getNif());
    }

}
