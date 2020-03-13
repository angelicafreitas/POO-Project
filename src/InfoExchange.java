import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.System.out;

public class InfoExchange {
    private String file;

    InfoExchange(String file){
        this.file = file;
    }

    public UMCModel loadFile(){
        UMCModel model = new UMCModel();
        BufferedReader inFile = null;
        String linha = null;
        try{
            inFile = new BufferedReader(new FileReader(this.file));
            while((linha = inFile.readLine()) != null){
                if(linha.contains("NovoProp:")){
                    String buffer = linha.split(":")[1];
                    String[] parts = buffer.split(",");
                    Proprietario p = new Proprietario(new DadosPessoais(parts[1] ,parts[2], parts[0], "123456", parts[3], LocalDate.now()));
                    model.addUtilizador(p);
                }
                else if(linha.contains("NovoCliente:")){
                    String buffer = linha.split(":")[1];
                    String[] parts = buffer.split(",");
                    Cliente p = new Cliente(new Ponto(Double.parseDouble(parts[4]) ,Double.parseDouble(parts[5])) , new DadosPessoais(parts[1], parts[2], parts[0], "123456", parts[3], LocalDate.now()), RandomEvents.randomDistreza());
                    model.addUtilizador(p);
                }
                else if(linha.contains("NovoCarro:")){
                    String buffer = linha.split(":")[1];
                    String[] parts = buffer.split(",");
                    Veiculo v = new Carro(new Ponto(Double.parseDouble(parts[8]), Double.parseDouble(parts[9])), parts[0].toUpperCase(), Integer.valueOf(parts[4]), Float.valueOf(parts[5]), Float.valueOf(parts[6]), true, parts[1], parts[3], parts[2],Double.valueOf(parts[7]), Double.valueOf(parts[7]));
                    if(model.existeUtilizador(v.getDono())){
                        model.adicionaVeiculo(v);
                    }
                }
                else if(linha.contains("Aluguer:")){
                    String buffer = linha.split(":")[1];
                    String[] parts = buffer.split(",");
                    Pedido p = new Pedido();
                    p.setCliente(parts[0]);
                    p.setDestino(new Ponto(Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                    Cliente c = (Cliente) model.getUser(parts[0]);
                    p.setDistrezaDoCliente(c.getDistrezaCondutor());
                    p.setInicio(c.getPonto());
                    Veiculo v = null;
                    if(parts[4].equals("MaisBarato")){
                         v = model.carroMaisBarato(parts[3], p.getDestino()).get(0);
                         p.setInicio(v.getPonto());
                         p.setVeiculo(v.getIdentificacao());
                         p.setProprietario(v.getDono());
                         p.setTime(LocalDateTime.now());
                         model.aceitarPedido(p);
                    }
                    else if(parts[4].equals("MaisPerto")){
                        v = model.carroMaisProximo(parts[3], p.getInicio(), p.getDestino()).get(0);
                        p.setInicio(v.getPonto());
                        p.setVeiculo(v.getIdentificacao());
                        p.setProprietario(v.getDono());
                        p.setTime(LocalDateTime.now());
                        model.aceitarPedido(p);
                    }
                }
                else if(linha.contains("Classificar:")){
                    String buffer = linha.split(":")[1];
                    String[] parts = buffer.split(",");
                    if(model.existeUtilizador(parts[0])){
                        model.addClassificacao(parts[0], Integer.parseInt(parts[1]), 2);
                    }
                    else if(model.existeVeiculo(parts[0])){
                        model.addClassificacao(parts[0], Integer.parseInt(parts[1]), 1);
                    }
                }
            }
        }
        catch(IOException e){
            out.println(e);
        }
        return model;
    }

    public void saveModel(UMCModel model){
        try{
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("model.data"));
            output.writeObject(model);
            output.close();
        }catch(Exception e){
            out.println("Falha ao guardar ficheiro!");
        }
    }

    public UMCModel loadModel(){
        try{
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("model.data"));
            UMCModel model;
            model = (UMCModel) input.readObject();
            input.close();
            return model;
        }catch(Exception e){
            return this.loadFile();
        }
    }

    public void eraseData(){
        try{
            PrintWriter writer = new PrintWriter("model.data");
            writer.print("");
            writer.close();
        }catch(IOException e){
            out.println("dados não encontrados!");
        }
    }
}
