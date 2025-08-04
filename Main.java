package BancoSemDb;

import java.util.Scanner;

public class Main {

    public static boolean containsOnlyDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static User criarConta() {
        Scanner read = new Scanner(System.in);

        System.out.println("Vamos criar sua conta:");

        System.out.println("Insira seu nome: ");
        String name = read.nextLine();

        System.out.println("Insira seu cpf: ");
        String cpf = read.nextLine();
        while(cpf.length()<11 || !containsOnlyDigits(cpf)){
            System.out.println("CPF Inválido, insira novamente");
            cpf = read.nextLine();
        }

        System.out.println("Insira sua senha: ");
        String password = read.nextLine();
        while(password.length()<8){
            System.out.println("Senha muito curta, insira novamente");
            password = read.nextLine();
        }

        return new User(name, cpf, password);
    }

    public static void acessarConta(User[] users) {
        Scanner read = new Scanner(System.in);
        int index = -1;

        System.out.println("Informe seu CPF de acesso: ");
        String cpf = read.nextLine();

        for (int i = 0; i < users.length; i++) {
            if (users[i].cpf != null && users[i].cpf.equals(cpf)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("CPF não encontrado");
            return;
        }

        System.out.println("Informe sua senha: ");
        String password = read.nextLine();

        if (!users[index].password.equals(password)) {
            System.out.println("Senha incorreta");
            return;
        }

        System.out.println("Acesso Liberado");

        while (true) {
            System.out.println("\nOlá " + users[index].name + ", o que deseja realizar?");
            System.out.println("1 - Ver saldo\n2 - Fazer Depósito\n3 - Fazer Saque\n4 - Fazer Transferência\n5 - Extrato\n0 - Sair");

            int option;
            while (!read.hasNextInt()) {
                System.out.println("Digite uma opção válida (0 a 3): ");
                read.nextLine();
            }

            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    if (users[index].account != null) {
                        users[index].account.showBalance();
                    } else {
                        System.out.println("Nenhuma conta associada.");
                    }
                    break;

                case 2:
                    System.out.print("Insira o valor de depósito: R$");
                    double depositValue = read.nextDouble();
                    read.nextLine();
                    if (users[index].account != null) {
                        users[index].account.deposit(depositValue);
                    } else {
                        System.out.println("Nenhuma conta associada.");
                    }
                    break;

                case 3:
                    System.out.print("Insira o valor de saque: R$");
                    double withdrawValue = read.nextDouble();
                    read.nextLine();
                    if (users[index].account != null) {
                        users[index].account.withdraw(withdrawValue);
                        users[index].account.showBalance();
                    } else {
                        System.out.println("Nenhuma conta associada.");
                    }
                    break;

                case 4:
                    System.out.print("Insira o CPF do destinatário:");
                    String targetCpf = read.nextLine();
                    while(targetCpf.length()<11 || !containsOnlyDigits(targetCpf)){
                        System.out.println("CPF mal informado, insira novamente");
                        targetCpf = read.nextLine();
                    }

                    System.out.print("Insira o valor da trasnferência: R$");
                    double value = read.nextDouble();

                    users[index].account.transfer(targetCpf, users[index].cpf, value, users);

                    break;
                case 5:
                    users[index].account.exportToTxt();
                    break;

                case 0:
                    System.out.println("Saindo da conta...");
                    return;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static boolean cpfExists(String cpf, User[] users, int size) {
        for (int i = 0; i < size; i++) {
            if (users[i].cpf != null && users[i].cpf.equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        User[] users = new User[99];
        int arrayIndex = 0;

        while (true) {
            System.out.println("\nOlá Cliente, o que deseja realizar?");
            System.out.println("1 - Criar Conta\n2 - Acessar Conta\n0 - Sair");

            int option;
            while (!read.hasNextInt()) {
                System.out.println("Digite uma opção válida (0 a 2): ");
                read.nextLine();
            }

            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    User user = criarConta();
                    if (cpfExists(user.cpf, users, arrayIndex)) {
                        System.out.println("Já existe uma conta com esse CPF");
                    } else {
                        users[arrayIndex] = user;
                        user.account = new Account(arrayIndex, user);
                        arrayIndex++;
                        System.out.println("Conta criada com sucesso!");
                    }
                    break;

                case 2:
                    acessarConta(users);
                    break;

                case 0:
                    System.out.println("Obrigado pela visita ao banco");
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
