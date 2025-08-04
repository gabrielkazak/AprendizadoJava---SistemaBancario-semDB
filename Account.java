package BancoSemDb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Account {
    int accountNumber;
    double balance;
    User owner;
    List<Movements> movements = new ArrayList<>();
    int transactionIndex = 0;

    public Account(int accountNumber, User owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0.0;
    }

    public void deposit(double value) {
        if (value > 0) {
            balance += value;
            Movements deposit = new Movements(new Date(), value, owner.name, "Deposito");
            movements.add(deposit);
            transactionIndex++;
            System.out.println("Depósito de R$" + value + " realizado com sucesso.");
        } else {
            System.out.println("Valor inválido para um depósito");
        }
    }

    public void withdraw(double value) {
        if (balance >= value && value > 0) {
            balance -= value;
            Movements withdraw = new Movements(new Date(), value, owner.name, "Saque");
            movements.add(withdraw);
            transactionIndex++;
            System.out.println("Saque de R$" + value + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
    }

    public void showBalance() {
        System.out.println("Saldo atual: R$" + balance);
    }

    public void transfer(String targetCpf, String userCpf, double value, User[] users) {
        if (value < 0) {
            System.out.println("Valor inválido");
            return;
        }

        User user = findUserByCpf(userCpf, users);
        User target = findUserByCpf(targetCpf, users);

        if (user == null) {
            System.out.println("Falha ao realizar a sua movimentação");
            return;
        }

        if (target == null) {
            System.out.println("Conta de destino inexistente. Transferência cancelada.");
            return;
        }

        if (user.account.balance >= value) {
            user.account.balance -= value;
            target.account.balance += value;
            Movements transfer = new Movements(new Date(), value, user.name, target.name, "Transferência");
            target.account.movements.add(new Movements(new Date(), value, target.name,user.name, "Recebido"));
            movements.add(transfer);
            transactionIndex++;
            System.out.println("Transferência realizada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para realizar a transferência.");
        }
    }

    private User findUserByCpf(String cpf, User[] users) {
        for (User user : users) {
            if (user.cpf.equals(cpf)) {
                return user;
            }
        }
        return null;
    }

    public void exportToTxt() {
        String nameFile = owner.cpf + "_movements.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nameFile))) {
            writer.println("Historico de Movimentações do usuario " + owner.name);
            writer.println("CPF: " + owner.cpf);
            writer.println("------------------------------------");

            for (Movements movement : movements) {
                writer.println(movement);
            }

            System.out.println("Movimentações exportadas com sucesso para " + nameFile);
        } catch (IOException e) {
            System.out.println("Erro ao exportar movimentações: " + e.getMessage());
        }
    }
}
