package BancoSemDb;

public class User{
    String name;
    String cpf;
    String password;
    Account account;

    public User(String name, String cpf, String password){
        this.name = name;
        this.cpf = cpf;
        this.password = password;
    }
}
