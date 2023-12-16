import java.util.Scanner;

public class Usuario {
    private String nome;
    private String senha;
    private String email;

    public Usuario(String nome, String senha, String email) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public boolean verificarCredenciais(String nomeUsuario, String senhaUsuario) {
        return this.nome.equals(nomeUsuario) && this.senha.equals(senhaUsuario);
    }

    // Método estático para cadastrar um novo usuário
    public static Usuario cadastrarNovoUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome de usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        return new Usuario(nome, senha, email);
    }

    // Método estático para fazer login
    public static Usuario fazerLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senhaUsuario = scanner.nextLine();

        // Aqui você precisará verificar se o usuário existe na base de dados
        // e retornar o objeto Usuario correspondente se as credenciais estiverem
        // corretas.

        // Neste exemplo simples, sempre retorna um novo usuário com as credenciais
        // fornecidas.
        return new Usuario(nomeUsuario, senhaUsuario, "");
    }

    // Getters para acessar as informações do usuário, se necessário
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}