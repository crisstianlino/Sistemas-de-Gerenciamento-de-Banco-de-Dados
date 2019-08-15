package ordenacao;

import java.io.IOException;
import java.util.Scanner;
import static ordenacao.Importar.importador;

public class Main {

    public static void main(String[] args) throws IOException {
        
        Tabela tab = new Tabela();
        EMS teste = new EMS();
        String pathname = "C:\\Users\\crist\\Downloads\\diretorio\\data_Funcionario.txt";
        String a = "0";
        String b = "Nome";
        String c = "Sobrenome";
        String d = "16";
        //String test = ""+a+","+b+","+c+","+d+",";
        //System.out.println(test);
        tab = importador(pathname);
        Scanner ler = new Scanner(System.in);
        System.out.printf("1 - Ordenar por idade\n2 - Ordenar por id\n");
        int n = ler.nextInt();
        teste.ordenar(n,tab);
    }
}
