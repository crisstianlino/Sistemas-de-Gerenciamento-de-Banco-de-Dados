package ordenacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EMS
{
    int tamanhobuffer = 125;
    Pagina buffer[] = new Pagina[tamanhobuffer];
    
    public Registro[] ordenarbuffer(int atributo)
    {
        if (atributo == 1){ // ordenar por idade
            Registro[] temp = new Registro[tamanhobuffer*16];
            for(int j = 0 ; j < tamanhobuffer ; j++)
            {
                    for(int k = 0 ; k < 16 ;k++)
                    {
                        temp[16*j+k] = this.buffer[j].getRegistro(k);
                    } 
            }
            ordena_idade(temp);
            return temp;
        }
        
        if (atributo == 2){ // ordenar por id
            Registro[] temp = new Registro[tamanhobuffer*16];
            for(int j = 0 ; j < tamanhobuffer ; j++)
            {
                    for(int k = 0 ; k < 16 ;k++)
                    {
                        temp[16*j+k] = this.buffer[j].getRegistro(k);
                    } 
            }
            ordena_id(temp);
            return temp;
        }
        return null;
    }
    
    public void preencherbuffer(int i, Tabela tabela){
       int qtd_arq = 625/tamanhobuffer;
       int count = 0;
       int comeco = i*tamanhobuffer;
       while (count < tamanhobuffer){
           this.buffer[count] = tabela.getPagina(comeco);
           comeco++;
           count++;
       }
    }
    
    public void ordenar(int atributo, Tabela tabela) throws IOException
    {
        if (atributo == 1) // o atributo escolhido para ordenar foi idade
        {
            for(int i=0;i<625/tamanhobuffer;i++){
                Registro[] temp = new Registro[tamanhobuffer*16];
                preencherbuffer(i,tabela);
                temp = ordenarbuffer(atributo);
                FileWriter arquivo = new FileWriter(new File("C:\\Users\\crist\\Downloads\\diretorio\\temp"+i+".txt"));
                for(int j=0;j<tamanhobuffer*16;j++){
                    String linha = ""+temp[j].getId_func()+","+temp[j].getNome()+","+temp[j].getSobrenome()+","+temp[j].getIdade()+"\n";
                    arquivo.write(linha);
                }
                arquivo.close();
            }
        }
    }
    
    public void ordena_idade(Registro elementos[]){  
         int cont1, cont2;
         Registro aux = new Registro();
             for(cont1 =0; cont1<tamanhobuffer*16; cont1++){  
                 for(cont2 =0; cont2 <tamanhobuffer*16-1; cont2++){  
                    if(elementos[cont2].getIdade()> elementos[cont2+1].getIdade()){  
                         aux = elementos[cont2];  
                         elementos[cont2] = elementos[cont2+1];  
                         elementos[cont2+1] = aux;  
                     }  
                 }  
             }  
    }
    
    public void ordena_id(Registro elementos[]){  
         int cont1, cont2;
         Registro aux = new Registro();
             for(cont1 =0; cont1<this.tamanhobuffer*16; cont1++){  
                 for(cont2 =0; cont2 <this.tamanhobuffer*16-1; cont2++){  
                    if(elementos[cont2].getId_func()> elementos[cont2+1].getId_func()){  
                         aux = elementos[cont2];  
                         elementos[cont2] = elementos[cont2+1];  
                         elementos[cont2+1] = aux;  
                     }  
                 }  
             }  
    }
}
