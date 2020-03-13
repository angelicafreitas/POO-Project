import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Circulo implements Serializable {
    private Ponto centro;
    private double raio;

    public Circulo(Ponto p, double raio) {
        this.centro = p.clone();
        this.raio = raio;
    }

    public Circulo() {
        int x = ThreadLocalRandom.current().nextInt(-150, 150);
        int y = ThreadLocalRandom.current().nextInt(-150, 150);
        this.centro = new Ponto(x, y);
        this.raio = ThreadLocalRandom.current().nextInt(25, 75);
    }

    public Circulo(double x, double y, double raio) {
        this.centro = new Ponto(x, y);
        this.raio = raio;
    }

    public Circulo(Circulo c) {
        this.centro = c.getCentro();
        this.raio = c.getRaio();
    }

    public Ponto getCentro() {
        return centro.clone();
    }

    public void setCentro(Ponto centro) {
        this.centro = centro.clone();
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public double perimetro() {
        return 2 * Math.PI * this.raio;
    }

    public double area() {
        return Math.PI * this.raio * this.raio;
    }

    private boolean dentroCirculo(Ponto p) {
        return p.distancia(centro) < raio;
    }

    public double percentagemTransito(Ponto inicio, Ponto fim) {
        double x1 = inicio.getX();
        double x2 = fim.getX();
        double y1 = inicio.getY();
        double y2 = fim.getY();
        Ponto vetor = new Ponto(x2-x1, y2-y1);
        List<Ponto> checkpoints = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            checkpoints.add(new Ponto(inicio.getX() + (i/10.0)*vetor.getX(),inicio.getY() + (i/10.0)*vetor.getY()));
        }
        int conta = 0;
        for(Ponto p : checkpoints){
            if(this.dentroCirculo(p)) conta++;
        }
        return conta / 10.0;
    }

    public void update(){
        int xOffset = ThreadLocalRandom.current().nextInt(-3,3);
        int yOffset = ThreadLocalRandom.current().nextInt(-3,3);
        this.centro.add(xOffset, yOffset);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circulo circulo = (Circulo) o;
        return Double.compare(circulo.raio, raio) == 0 &&
                this.centro.equals(circulo.centro);
    }

    public int hashCode() {
        return Objects.hash(centro.getX(), centro.getY(), raio);
    }

    public String toString() {
        return "Circulo{" +
                "centro=" + centro +
                ", raio=" + raio +
                '}';
    }
}

