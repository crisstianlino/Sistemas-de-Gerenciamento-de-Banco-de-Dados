package ordenacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Importar 
{

    public static void displayRegistro(Registro registro) 
    {
        System.out.println(registro.id_func + " " + registro.nome + " " + registro.sobrenome + " " + registro.idade);
    }

    public static Tabela importador(String pathname) throws IOException 
    {
        Registro reg = null;
        int registrosPorPagina = 16;
        Pagina pagina = new  Pagina();
        Tabela tabela = new Tabela();
        Pagina print = new Pagina();
        Registro print2 = new Registro();

        File arquivo = new File(pathname);
        if (arquivo.exists()) 
        {
            Scanner entradaDoArquivo = new Scanner(new BufferedReader(new FileReader(arquivo)));
            String linhaDoArquivo;
            
            int count = 0;
            int i = 0;
            while (entradaDoArquivo.hasNextLine()) 
            {
                linhaDoArquivo = entradaDoArquivo.nextLine();
                String[] InArray = linhaDoArquivo.split(",");
                reg = new Registro(Integer.parseInt(InArray[0]), InArray[1], InArray[2], Integer.parseInt(InArray[3]));
                pagina.setRegistros(count,reg);
                count++;
                if (count == 16) 
                {
                    tabela.setPaginas(pagina,i);
                    Pagina temp = new  Pagina();
                    pagina = temp;
                    count = 0;
                    i++;
                }
            }    
        }
        return tabela;
    }
}


