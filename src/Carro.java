import java.io.Serializable;

public class Carro extends Veiculo implements Serializable {
    public TiposDeCarro tipo;

    public Carro(){
        super();
        this.tipo=TiposDeCarro.NOT_DEFINED;
    }
    public Carro(Ponto ponto,String tipo, int vMedia, float precoKm, float consumoKm, boolean disponibilidade, String marca, String dono,String identificacao,double autonomiaAtual, double autonomiaTotal){
        super(ponto, vMedia, precoKm, consumoKm, new Historico(), disponibilidade, marca, dono, identificacao, autonomiaAtual, autonomiaTotal);
        this.setTipo(tipo);
    }
    public Carro(Carro c){
        super(c.getPonto(),c.getVMedia(), c.getPrecoKm(), c.getConsumoKm(), c.getHistorico(), c.getDisponibilidade(), c.getMarca(), c.getDono(),c.getIdentificacao(),c.getAutonomiaAtual(), c.getAutonomiaTotal());
        this.setTipo(c.getTipo());
    }

    public String getTipo(){return this.tipo.toString();}
    public void setTipo(String tipo) {
        try{
            this.tipo = TiposDeCarro.valueOf(tipo);
        } catch(Exception e) {
            this.tipo = TiposDeCarro.NOT_DEFINED;
        }
    }


    public Carro clone(){
        return new Carro(this);
    }
    public boolean equals(Object c){
        if(c==this) return true;
        if (c==null || (this.getClass()!=c.getClass())) return false;
        Carro aux = (Carro) c;
        return (super.equals(aux)
                && this.getTipo().equals(aux.getTipo())
                && this.getAutonomiaAtual() == aux.getAutonomiaAtual());
    }
    public String toString(){
        return super.toString() + "Tipo :" + this.tipo;
    }
}
