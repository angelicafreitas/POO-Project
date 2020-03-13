import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomEvents {
    public static int randomWeather(){
        Random r = new Random();
        int choice = ThreadLocalRandom.current().nextInt(1, 6); // 1 - Trovoada | 2 - Chuva | 3 - Nublado | 4 - Algumas Nuvens | 5 - Sol
        return choice;
    }

    public static String intToStringMeteorologia(int meteorologia){
        switch(meteorologia){
            case 1:
                return "Trovoada";
            case 2:
                return "Chuva";
            case 3:
                return "Nublado";
            case 4:
                return "Algumas nuvens";
            case 5:
                return "Sol";
            default:
                return "Não disponível";
        }
    }

    public static double randomDistreza(){
        return ThreadLocalRandom.current().nextDouble(1);
    }

    public static double travelTimeCalculator(double distancia, double vmedia, int weather, double distrezaCondutor, double transito){
        double tempoBase = distancia/vmedia;
        return tempoBase*((1.0/(weather*0.2 + 0.6)*0.4 + (1-(distrezaCondutor) + 1-(transito/100))));
    }
}
