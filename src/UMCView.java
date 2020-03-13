import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class UMCView {
    private UMCModel model;

    public UMCView(UMCModel model){
        this.model = model;
    }

    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void start(){
        String modo;
        while(true){
            clearScreen();
            out.println("--------------------------------------------------");
            out.println("(1) Criar Conta");
            out.println("(2) Iniciar sessão");
            out.println("(3) Estatísticas");
            out.println("(4) Sair");
            out.println("--------------------------------------------------");
            out.print("-> ");
            modo = Input.lerString();
            switch (modo.charAt(0)){
                case '1':
                    this.menuCriarConta();
                    break;
                case '2':
                    this.menuIniciarSessao();
                    break;
                case '3':
                    clearScreen();
                    List<Cliente> list = model.clienteMaisAtivos();
                    List<Cliente> listKms = model.clienteMaisAtivosKms();
                    if(list.size() <= 10){
                        out.println("Não existem dados suficientes!");
                        break;
                    }
                    out.println("Os 10 clientes mais que mais utilizam o sistema\n");
                    out.println("Por número de alugueres: (1)");
                    out.println("Por total de kilómetros: (2)");
                    int choice;
                    if((choice = Input.lerInt()) == 1) {
                        for(int i = 0; i < 10; i++) {
                            out.println("Cliente: " + list.get(i).getDadosPessoais().getNome() + ", Número de acessos: " + list.get(i).getHistorico().getSize());
                            out.println("---------------------------------------------------------");
                        }
                    }
                    else if(choice == 2){
                        for(int i = 0; i < 10; i++) {
                            out.println("Cliente: " + listKms.get(i).getDadosPessoais().getNome() + ", Total de km's: " + listKms.get(i).totalKms());
                            out.println("---------------------------------------------------------");
                        }
                    }
                    out.println("Sair (1)");
                    Input.lerString();
                    break;
                case '4':
                    return;
            }
        }
    }

    private void menuCriarConta(){
        clearScreen();
        Utilizador c;
        out.println("--------------------------------------------------");
        out.println("(1) Cliente");
        out.println("(2) Proprietário");
        out.println("-------------------------------------------------");
        out.print("->");
        String modo = Input.lerString();
        out.print("Insira o seu Nif: ");
        String nif = Input.lerString();
        if(model.existeUtilizador(nif)){
            out.println("Este utilizador já existe! Por favor confira o seu NIF");
            return;
        }
        out.print("Insira o seu email: ");
        String email = Input.lerString();
        out.print("Insira o seu nome: ");
        String nome = Input.lerString();
        out.print("Insira a sua password: ");
        String password = Input.lerString();
        out.print("Insira a sua morada: ");
        String morada = Input.lerString();
        out.print("Insira a sua Data de Nascimento, (yyyy-mm-dd) :");
        LocalDate nascimento = null;
        boolean valida = false;
        while(!valida){
            try{
                nascimento = LocalDate.parse(Input.lerString());
                valida = true;
            }catch(Exception e){
                out.println("Data de nascimento não valida!");
                out.println("Por favor tente novament!");
            }
        }

        if(modo.equals("1")){
            c = new Cliente(new DadosPessoais(nif, email, nome, password, morada, nascimento), RandomEvents.randomDistreza());
        }
        else if(modo.equals("2")){
            c = new Proprietario(new DadosPessoais(nif, email, nome, password, morada, nascimento));
        }
        else{
            return;
        }
        model.addUtilizador(c);
    }

    private void menuIniciarSessao(){
        clearScreen();
        String email;
        String nif;
        String password;
        boolean loggedIn;
        clearScreen();
        out.println("--------------------------------------------------");
        out.println("Insira o seu Email: ");
        email = Input.lerString();
        out.println("Insira a sua Password: ");
        password = Input.lerString();
        out.println("--------------------------------------------------");
        try{
            nif = model.getNif(email);
            loggedIn = model.userLogin(nif, password);
        }catch (UserNotFoundException | IndexOutOfBoundsException e){
            out.println(e.toString());
            return;
        }
        if(loggedIn){
            Utilizador u = model.getUser(nif);
            if(u instanceof Cliente){
                this.menuCliente(nif);
            }
            if(u instanceof Proprietario){
                this.menuProprietario(nif);
            }
        }
        else{
            out.println("Password não está correta!");
        }

    }

    private void navegaHistorico(List<Aluguer> list){
        clearScreen();
        String str = "1986-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        if(list.size() == 0){
            out.println("Não existem Alugueres!");
            return;
        }
        int max = list.size()-1;
        int current = 0;
        int input;
        Aluguer a;
        LocalDateTime bottom = LocalDateTime.MIN;
        LocalDateTime top = LocalDateTime.MAX;
        List<Aluguer> filtered;
        while(current >= 0 && current <= max){
            clearScreen();
            final LocalDateTime bottomAux = bottom;
            final LocalDateTime topAux = top;
            filtered = list.stream().filter(l -> l.getData().isBefore(topAux) && l.getData().isAfter(bottomAux)).collect(Collectors.toList());
            if(filtered.isEmpty()){
                out.println("Não existem alugueres!");
                return;
            }
            max = filtered.size()-1;
            if(current > max) current = max;
            a = filtered.get(current);
            out.println("----------------- " + (current + 1) + " de " + (max+1) + "------------------------------\n");
            out.println("Inicio: " + a.getInicio());
            out.println("Destino: " + a.getDestino());
            out.printf("Custo: %.1f Euros\n", a.getCusto());
            out.printf("Tempo: %.1f Horas\n", a.getTempo());
            out.println("Estado do tempo: " + a.getMeteorologia());
            out.println("Data: " + a.getData().toString());
            out.println("Cliente: " + a.getCliente());
            out.println("Veiculo: " + a.getIdCarro());
            out.printf("Distancia: %.1f Km's\n" , a.getDistancia());
            out.printf("Percentagem de percurso com transito: %.1f%%\n", a.getTransito());
            out.println("--------------------------------------------------");
            out.println("Anterior(1) |  Próximo(2) |  Mudar data Inicial (3) |  Mudar data Final (4) |  Sair(5)");
            input = Input.lerInt();
            switch (input) {
                case 1:
                    current--;
                    if (current < 0) current = 0;
                    break;
                case 2:
                    current++;
                    if (current > max) current = max;
                    break;
                case 3:
                    try {
                        out.println("Insira a data desejada! Exemplo: 1986-04-08 12:30");
                        bottom = LocalDateTime.parse(Input.lerString(), formatter);
                    } catch (DateTimeParseException e) {
                        out.println("Alguma coisa correu mal!");
                    }
                    break;
                case 4:
                    try {
                        out.println("Insira a data desejada! Exemplo: 1986-04-08 12:30");
                        top = LocalDateTime.parse(Input.lerString(), formatter);
                    } catch (DateTimeParseException e) {
                        out.println("Alguma coisa correu mal!");
                    }
                    break;
                case 5:
                    return;

            }
        }
    }

    private void abastecerVeiculo(Veiculo v){
        boolean ok = false;
        if(v.getTipo().equals("GASOLINA") || v.getTipo().equals("HIBRIDO")){
            out.println("Combustível atual: " + v.getAutonomiaAtual()*v.getConsumoKm() + " litros");
            out.println("Capacidade do Carro: " + v.getAutonomiaTotal()*v.getConsumoKm() + " litros");
            while(!ok){
                out.println("Litros pretendidos? (-1 para encher depósito)");
                int litros = Input.lerInt();
                if(litros == -1){
                    ok = true;
                    model.abasteceVeiculo(v.getIdentificacao(), -1);
                }
                else if(litros >= 0 && litros <= v.getAutonomiaTotal()*v.getConsumoKm() - v.getAutonomiaAtual()*v.getConsumoKm()){
                    ok = true;
                    model.abasteceVeiculo(v.getIdentificacao(), litros);
                }
                else{
                    out.println("Valor inválido!");
                }
            }
        }
        else if(v.getTipo().equals("ELECTRICO")){
            out.println("Bateria atual: " + v.getAutonomiaAtual()*v.getConsumoKm() + " Kwh");
            out.println("Capacidade do Carro: " + v.getAutonomiaTotal()*v.getConsumoKm() + " Kwh");
            while(!ok){
                out.println("Kwh pretendidos? ");
                int litros = Input.lerInt();
                if(litros >= 0 && litros <= v.getAutonomiaTotal()*v.getConsumoKm() - v.getAutonomiaAtual()*v.getConsumoKm()){
                    ok = true;
                    model.abasteceVeiculo(v.getIdentificacao(), litros);
                }
                else{
                    out.println("Valor inválido!");
                }
            }
        }
    }

    private void navegaVeiculos(String dono){
        List<Veiculo> list = model.getVeiculos(dono);
        if(list.size() == 0){
            out.println("Não existem Veiculos!");
            return;
        }
        int max = list.size()-1;
        int current = 0;
        int input;
        Veiculo v;
        double percentagemBateria;
        while(current >= 0 && current <= max){
            clearScreen();
            list = model.getVeiculos(dono);
            v = list.get(current);
            percentagemBateria = 100*v.getAutonomiaAtual()/v.getAutonomiaTotal();
            out.println("----------------- " + (current + 1) + " de " + (max+1) + "------------------------------\n");
            out.println("Identificação: " + v.getIdentificacao());
            out.printf("Autonomia Atual %.1f  %.1f%%\n", v.getAutonomiaAtual(), percentagemBateria);
            out.printf("Localização: %.1f, %.1f\n", + v.getPonto().getX(), v.getPonto().getY());
            out.println("Tipo: " + v.getTipo());
            out.printf("Preço por Kilómetro: %.1f \n", v.getPrecoKm());
            out.println("Disponivel de momento: " + v.getDisponibilidade());
            out.println("--------------------------------------------------");
            out.println("Anterior(1)       | Próximo(2)            | Abastecer(3) | Trocar disponibilidade (4)");
            out.println("Alterar Preço (5) | Visitar Histórico (6) | Sair(7)\n");
            input = Input.lerInt();
            switch (input){
                case 1:
                    current--;
                    if(current < 0) current = 0;
                    break;
                case 2:
                    current++;
                    if(current > max) current = max;
                    break;
                case 3:
                    this.abastecerVeiculo(v);
                    out.print("Sucesso!!");
                    break;
                case 4:
                    try{
                        model.setDisponibilidade(v.getIdentificacao(), !v.getDisponibilidade());
                    }catch(VeiculoNotFoundException e){
                        out.println("Veiculo não encontrado!");
                    }
                    break;
                case 5:
                    out.print("Novo preço: ");
                    float preco = Input.lerFloat();
                    model.alteraPreco(v.getIdentificacao(), preco);
                    out.print("Sucesso!!");
                    break;
                case 6:
                    this.navegaHistorico(v.getHistorico().getAlugueres());
                    break;
                case 7:
                    return;
            }
        }


    }

    private Veiculo menuAdicionarVeiculo(String dono){
        clearScreen();
        out.print("--------------------------------------------------\n");
        Ponto localizacao = new Ponto();
        out.print("Insira as coordenadas da viatura X: ");
        localizacao.setX(Input.lerDouble());
        out.print("                                 Y: ");
        localizacao.setY(Input.lerDouble());
        out.print("Qual é o tipo da viatura? GASOLINA (1) | ELECTRICO (2) | HIBRIDO (3) ");
        String tipo = Input.lerString();
        out.print("Qual é a velocidade média da viatura? ");
        int vmedia = Input.lerInt();
        out.print("Qual é o preço por kilómetro que gostaria de cobrar? ");
        float precoKm = Input.lerFloat();
        out.print("Qual é o consumo por kilómetro da viatura? ");
        float consumoKm = Input.lerFloat();
        out.print("Qual é a marca do carro? ");
        String marca = Input.lerString();
        out.print("Qual é a matricula do carro? ");
        String id = Input.lerString();
        out.print("Qual é a autonomia do carro? ");
        double autonomia = Input.lerDouble();
        out.print("O seu carro foi adicionado com sucesso!\n");
        switch (tipo.charAt(0)){
            case 1:
                tipo = "GASOLINA";
                break;
            case 2:
                tipo = "ELECTRICO";
                break;
            case 3:
                tipo = "HIBRIDO";
                break;
        }
        return new Carro(localizacao , tipo, vmedia, precoKm, consumoKm, true, marca, dono, id, autonomia, autonomia);
    }

    private void menuProprietario(String user){
        clearScreen();
        List<Pedido> lpedidos =  model.showPedidos(user);
        int classificacao;
        for(Pedido p : lpedidos){
            out.println();
            out.println(p.toString());
            out.println("Cliente está a " + (p.getLocalizacaoCliente().distancia(p.getInicio()))*15 + " minutos do veículo!");
            out.println("Distancia do Cliente ao Carro: " + p.getLocalizacaoCliente().distancia(p.getInicio()) + " Km's");
            out.println("Classificação do Cliente: " + model.getClassificacao(p.getCliente(), 2));
            out.println("Aceitar Pedido? (1)Sim  (2)Não\n");
            if(Input.lerString().equals("1")) {
                model.aceitarPedido(p);
                out.println("Deseja classificar o cliente? Sim (S) | Não (N)");
                if(Input.lerString().equals("S")){
                    boolean ok = false;
                    while(!ok){
                        out.println("Qual é a classificação que deseja dar?");
                        classificacao = Input.lerInt();
                        if(classificacao <= 100 && classificacao >= 0){
                            ok = true;
                            model.addClassificacao(p.getCliente(), classificacao, 2);
                        }
                        else{
                            out.println("Classificação inválida! Tente novamente");
                            Input.lerString();
                            clearScreen();
                        }
                    }
                }
            }
            else model.rejeitarPedido(p);
        }
        List<String> lveicsSemCombustivel = model.checkCombustivelVeiculos(user);
        for(String s : lveicsSemCombustivel){
            out.printf("O seguinte veículo está sem combustível! %s Deseja abastecer? (1)Sim  (2)Não\n", s);
            if(Input.lerString().equals("1")) this.abastecerVeiculo(model.getVeiculo(s));
        }
        String modo;
        while(true){
            clearScreen();
            out.println("----------------------------------------");
            out.println("Adicionar Veículo: (1)");
            out.println("Veículos: (2)");
            out.println("Ver perfil: (3)");
            out.println("Sair: (4)");
            out.println("----------------------------------------");
            out.print("->");
            modo = Input.lerString();
            if(modo.equals("1")){
                model.adicionaVeiculo(menuAdicionarVeiculo(user));
            }
            if(modo.equals("2")){
                this.navegaVeiculos(user);
            }
            if(modo.equals("3")) this.showPerfil(user);
            if(modo.equals("4")) break;
        }
    }

    private void chooseCarro(List<Veiculo> list, String user, Ponto localizacao, Ponto destino){
        clearScreen();
        if(list.size() == 0){
            out.println("Não existem veiculos disponiveis!");
            return;
        }
        int max = list.size()-1;
        int current = 0;
        int input;
        Cliente c = (Cliente) model.getUser(user);
        Veiculo v;
        double percentagemBateria;
        while(current >= 0 && current <= max){
            v = list.get(current);
            percentagemBateria = 100*v.getAutonomiaAtual()/v.getAutonomiaTotal();
            out.println("----------------- " + (current + 1) + " de " + (max+1) + "------------------------------\n");
            out.println("Identificação: " + v.getIdentificacao());
            out.println("Marca: " + v.getMarca());
            out.printf("Autonomia Atual %.1f  %.1f%%\n", v.getAutonomiaAtual(), percentagemBateria);
            out.printf("Localização: %.1f, %.1f\n", + v.getPonto().getX(), v.getPonto().getY());
            out.printf("Distancia a que o carro se situa: %.1f Km's\n" ,c.getPonto().distancia(v.getPonto()));
            out.println("Tipo: " + v.getTipo());
            out.printf("Preço por Kilómetro: %.1f \n", v.getPrecoKm());
            out.println("--------------------------------------------------");
            out.println("Anterior(1) |  Próximo(2) |  Requisitar (3) | Sair(4)\n");
            input = Input.lerInt();
            switch (input){
                case 1:
                    current--;
                    if(current < 0) current = 0;
                    break;
                case 2:
                    current++;
                    if(current > max) current = max;
                    break;
                case 3:
                    Pedido p = new Pedido(user, v.getDono(), v.getIdentificacao(), destino, localizacao, LocalDateTime.now(), c.getPonto(), c.getDistrezaCondutor());
                    model.addPedido(p);
                    return;
                case 4:
                    return;
            }
        }
    }

    private void showPerfil(String user){
        Utilizador u = model.getUser(user);
        DadosPessoais dados = u.getDadosPessoais();
        out.println("---------------------------------------------------");
        out.println("Nome: " + dados.getNome());
        out.println("Email: " + dados.getEmail());
        out.println("NIF " + dados.getNif());
        out.println("Morada: " + dados.getMorada());
        out.println("Data de Nascimento: " + dados.getNascimento());
        out.println("Minha Classificação: " + model.getClassificacao(user, 2));
        out.println("--------------------------------------------------");
        out.println("Sair(1)\n");
        Input.lerInt();
    }

    private void checkClassificado(String user){
        List<String> classificacoes = model.updateClassificacoes(user);
        String choice;
        int classificacao;
        for(String s : classificacoes){
            clearScreen();
            out.println("Pretende classificar o veículo " + s + " ?");
            out.println("Sim (S) | Não (N)");
            choice = Input.lerString();
            if(choice.equals("S")){
                boolean ok = false;
                while(!ok){
                    out.println("Qual é a classificação que deseja dar?");
                    classificacao = Input.lerInt();
                    if(classificacao <= 100 && classificacao >= 0){
                        ok = true;
                        Veiculo v = model.getVeiculo(s);
                        model.addClassificacao(s, classificacao, 1);
                        model.addClassificacao(v.getDono(), classificacao, 2);
                    }
                    else{
                        out.println("Classificação inválida! Tente novamente");
                        Input.lerString();
                        clearScreen();
                    }
                }
            }
        }
        clearScreen();
    }

    private void menuCliente(String user){
        clearScreen();
        Cliente c = (Cliente) model.getUser(user);
        this.checkClassificado(user);
        Ponto localizacao = new Ponto();
        out.print("Qual é a sua posicão? X: ");
        localizacao.setX(Input.lerDouble());
        out.print("\n                      Y: ");
        localizacao.setY(Input.lerDouble());
        model.updatePosicaoCliente(user, localizacao);
        Ponto destino = new Ponto();
        while(true){
            clearScreen();
            out.println("---------------------------------------------");
            out.println("Requesitar carro mais próximo (1)");
            out.println("Requisitar carro mais barato (2)");
            out.println("Requisitar um carro específico (3)");
            out.println("Visitar historico (4)");
            out.println("Meu Perfil (5)");
            out.println("Sair (6)");
            out.println("---------------------------------------------");
            out.print("-> ");
            String mode = Input.lerString();
            if(mode.equals("6")) return;
            String tipo;
            List<Veiculo> v;
            Pedido p;
            switch (mode.charAt(0)){
                case '1':
                    out.print("\nPara onde deseja ir? X: ");
                    destino.setX(Input.lerDouble());
                    out.print("                       Y: ");
                    destino.setY(Input.lerDouble());
                    out.println("Qual é o tipo de automóvel que prefere? ELECTRICO (1) | GASOLINA (2) | HIBRIDO (3)");
                    tipo = Input.lerString();
                    switch (tipo.charAt(0)){
                        case 1:
                            tipo = "GASOLINA";
                            break;
                        case 2:
                            tipo = "ELECTRICO";
                            break;
                        case 3:
                            tipo = "HIBRIDO";
                            break;
                    }
                    v = model.carroMaisProximo(tipo, localizacao, destino);
                    this.chooseCarro(v, user, localizacao, destino);
                    break;
                case '2':
                    out.print("\nPara onde deseja ir? X: ");
                    destino.setX(Input.lerDouble());
                    out.print("\n                     Y: ");
                    destino.setY(Input.lerDouble());
                    out.println("Qual é o tipo de automóvel que prefere? ELECTRICO (1) | GASOLINA (2) | HIBRIDO (3)");
                    tipo = Input.lerString();
                    switch (tipo.charAt(0)){
                        case 1:
                            tipo = "GASOLINA";
                            break;
                        case 2:
                            tipo = "ELECTRICO";
                            break;
                        case 3:
                            tipo = "HIBRIDO";
                            break;
                    }
                    v = model.carroMaisBarato(tipo, destino);
                    this.chooseCarro(v, user, localizacao, destino);
                    break;
                case '3':
                    out.print("\nPara onde deseja ir? X: ");
                    destino.setX(Input.lerDouble());
                    out.print("\n                     Y: ");
                    destino.setY(Input.lerDouble());
                    out.println("Que carro deseja alugar: ");
                    String carro = Input.lerString();
                    Veiculo aux = model.carroEspecifico(carro, destino);
                    if(aux == null) out.println("Não disponível");
                    else{
                        p = new Pedido(user, aux.getDono(), aux.getIdentificacao(), destino, aux.getPonto(), LocalDateTime.now(), localizacao, c.getDistrezaCondutor());
                        model.addPedido(p);
                        out.println("Sucesso!!");
                    }
                    Input.lerString();
                    break;
                case '4':
                    this.navegaHistorico(c.getHistorico().getAlugueres());
                    break;
                case '5':
                    this.showPerfil(user);
                    break;
                default:
                    return;
            }
        }
    }
}
