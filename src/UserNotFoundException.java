public class UserNotFoundException extends Exception {
    String id;

    public UserNotFoundException(String user){
        this.id = user;
    }

    public String toString(){
        return "Utilizador " + this.id + " não encontrado!!";
    }
}
