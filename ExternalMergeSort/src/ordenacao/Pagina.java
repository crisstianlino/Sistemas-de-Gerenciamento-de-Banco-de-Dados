package ordenacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Pagina {

    int registrosPorPagina = 16;
    private Registro registros[];
   
    
    public void setRegistros(int n ,Registro registro) {
        this.registros[n] = registro;
    }

    public Pagina() {
        this.registros = new Registro[16];
    }

    public void setRegistrosPorPagina(int registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }

    public Registro getRegistro(int i){
        return registros[i];
    }

}
