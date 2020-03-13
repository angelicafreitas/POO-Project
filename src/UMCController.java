import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static java.lang.System.out;

public class UMCController {
    public static void main(String[] args){

        String str = "logsPOO_carregamentoInicial.bak";
        try{
            BufferedReader in = new BufferedReader(new FileReader("settings.txt"));
        }catch(IOException e){
            out.println("Não foi possível ler o ficheiro settings.txt");
        }
        InfoExchange i = new InfoExchange(str);
        UMCModel model = i.loadModel();
        UMCView view = new UMCView(model);
        view.start();
        i.saveModel(model);
    }
}
