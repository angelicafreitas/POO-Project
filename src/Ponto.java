import java.io.Serializable;

public class Ponto implements Serializable {
    private double x,y;

    public Ponto(){
        this.x=0;
        this.y=0;
    }
    public Ponto(double x, double y){
        this.x=x;
        this.y=y;
    }

    public Ponto(Ponto p){
        this.x=p.getX();
        this.y=p.getY();
    }

    public double getX(){return this.x;}
    public double getY(){return this.y;}

    public void setX(double x){this.x=x;}
    public void setY(double y){this.y=y;}

    public double distancia(Ponto p){
        return Math.sqrt(Math.pow(this.getX()-p.getX(),2)+Math.pow(this.getY()-p.getY(),2));
    }

    public void add(int x, int y){
        this.x += x;
        this.y += y;
    }

    public Ponto clone(){
        return new Ponto(this);
    }
    public boolean equals(Object p){
        if(p==this) return true;
        if (p==null || (this.getClass()!=p.getClass())) return false;
        Ponto aux = (Ponto)p;
        return (this.getX()==aux.getX() && this.getY()==aux.getY());
    }
    public String toString(){
        return "Abcissa: "+ this.x + ", Ordenada: " + this.y;
    }


}
