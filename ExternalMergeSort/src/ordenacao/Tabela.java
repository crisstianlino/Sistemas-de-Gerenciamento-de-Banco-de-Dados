package ordenacao;

import java.util.List;

public class Tabela {

    Pagina paginas[];
    int qtd_paginas;

    public Tabela() {
        this.paginas =  new Pagina[1000];
        this.qtd_paginas = 0;
    }

    public void setPaginas(Pagina paginas, int n) {
        this.paginas[n] = paginas;
        this.qtd_paginas++;
    }
     
    public Pagina getPagina(int i){
        return paginas[i];
    }
    

}
