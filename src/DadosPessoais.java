import java.io.Serializable;
import java.time.LocalDate;

public class DadosPessoais implements Serializable {
    private String nif;
    private String email;
    private String nome;
    private String password;
    private String morada;
    private LocalDate nascimento;

    public DadosPessoais(){
        this.nif = "n/a";
        this.email = "n/a";
        this.nome = "n/a";
        this.password = "n/a";
        this.morada = "n/a";
        this.nascimento = LocalDate.now();
    }
    public DadosPessoais(String nif, String email, String nome, String password, String morada, LocalDate nascimento){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.morada = morada;
        this.nascimento = nascimento;
    }
    public DadosPessoais(DadosPessoais d){
        this.nif = d.getNif();
        this.email = d.getEmail();
        this.nome = d.getNome();
        this.password = d.getPassword();
        this.morada = d.getMorada();
        this.nascimento = d.getNascimento();

    }

    public String getNif(){return this.nif;}
    public String getEmail(){return this.email;}
    public String getNome(){return this.nome;}
    public String getPassword(){return this.password;}
    public String getMorada(){return this.morada;}
    public LocalDate getNascimento(){return this.nascimento;}

    public void setNif(String nif){this.nif = nif;}
    public void setEmail(String email){this.email=email;}
    public void setNome(String nome){this.nome=nome;}
    public void setPassword(String password){this.password=password;}
    public void setMorada(String morada){this.morada=morada;}
    public void setNascimento(LocalDate nascimento){       this.nascimento=nascimento;}


    public boolean checkPassword(String password){
        return (this.password.equals(password));
    }

    public DadosPessoais clone() {
        return new DadosPessoais(this);
    }

    public boolean equals(Object o){
        if(o==this) return true;
        if(o == null || (o.getClass() != this.getClass()) ) return false;
        DadosPessoais aux = (DadosPessoais) o;
        return (this.nif.equals(aux.getNif()) &&
                this.email.equals(aux.getEmail()) &&
                this.nome.equals(aux.getNome()) &&
                this.password.equals(aux.getPassword()) &&
                this.morada.equals(aux.getMorada()) &&
                this.nascimento.equals(aux.getNascimento()));
    }

    public String toString(){
        return "Nif: " + this.nif +
                "\nEmail: " + this.email +
                "\nNome: " + this.nome +
                "\nPassword: " + this.password +
                "\nMorada: " + this.morada +
                "\nNascimento: " + this.nascimento.toString();
    }

}
