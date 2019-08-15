package ordenacao;

public class Registro {

    Integer id_func;
    String nome;
    String sobrenome;
    Integer idade;

    public Registro() {
    }

    public Registro(Integer id_func, String nome, String sobrenome, Integer idade) {
        this.id_func = id_func;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
    }

    public Integer getId_func() {
        return id_func;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void display(){ 
        System.out.println(this.getId_func()+" "+this.getNome()+" "+this.getSobrenome()+" "+this.getIdade());
    }
}
