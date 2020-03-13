public class VeiculoNotFoundException extends Exception {
    String id;

    public VeiculoNotFoundException(String id){
        this.id = id;
    }

    public String toString(){
        return "Veiculo " + this.id + " não encontrado!";
    }
}
