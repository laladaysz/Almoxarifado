package br.ETS.almoxarifado;

import br.ETS.almoxarifado.produto.Produto;
import br.ETS.almoxarifado.produto.ProdutoDTO;
import br.ETS.almoxarifado.produto.ProdutoService;

import java.util.Scanner;

public class Main {
    private static ProdutoService produtoService = new ProdutoService();
    private static Scanner scanner = new Scanner(System.in);

    private static int exibirMenu() {
        System.out.println("Almoxarifado ETS");
        System.out.println("""
                           Selecione uma opção:
                           1- Adicionar produto;
                           2- Listar todos os produtos;
                           3- Adicionar quantidade de um produto;
                           4- Retirar quantidade de um produto do estoque;
                           5- Deletar um produto;
                           0- Sair;
                           """);
        return Integer.parseInt(scanner.nextLine()); // le tudo como string e depois retorna um inteiro com a opção escolhida pelo user
    };


    public static void main(String[] args) {
        var opcao = exibirMenu();

        while (opcao!= 6) {
            try {
                switch (opcao) {
                    case 1 -> adicionarNovoProduto();
                    case 2 -> exibirProdutosCadastrados();
                    case 3 -> adicionarQuantidade();
                    case 4 -> removerQuantidade();
                    case 5 -> removerProduto();
                }
            } catch (RegraDaAplicacaoException e) {
                System.out.println(e.getMessage());
                System.out.println("Pressione ENTER para volar ao menu principal");
                scanner.nextLine();
            }
            opcao = exibirMenu();
        }
    }

    private static void adicionarNovoProduto() {
        System.out.println("Insira o ID do produto que deseja cadastrar: ");
        var id = Integer.parseInt(scanner.nextLine());
        System.out.println("Insira o nome do produto que deseja cadastrar: ");
        var produto = scanner.nextLine();
        System.out.println("Insira o PARTNUMBER do produto que deseja cadastrar: ");
        var partnumber = scanner.nextLine();
        System.out.println("Insira a divisão do produto que deseja cadastrar: ");
        var divisao = scanner.nextLine();
        System.out.println("Insira a quantidade desse produto: ");
        var quantidade = Integer.parseInt(scanner.nextLine());

        produtoService.adicionarNovoProduto(new ProdutoDTO(id, produto, partnumber, divisao,quantidade));

        System.out.printf("O produto %s foi cadastrado com sucesso\n", produto);
    }

    private static void exibirProdutosCadastrados(){
        var produtos = produtoService.exibirProdutosDoAlmoxarifado();
        produtos.forEach(System.out::println);
        System.out.println("Pressione ENTER para voltar ao menu principal");
        scanner.nextLine();
    }

    private static void adicionarQuantidade(){
        System.out.println("Insira o ID do produto que deseja adicionar quantidade: ");
        var id = Integer.parseInt(scanner.nextLine());
        System.out.println("Insira a quantidade que deseja adicionar do produto: ");
        var quantidade = Integer.parseInt(scanner.nextLine());

        produtoService.adicionarQuantidadeDeUmProduto(id, quantidade);
        System.out.println("Pressione ENTER para voltar ao menu principal");
        scanner.nextLine();
    }

    private static void removerQuantidade(){
        System.out.println("Insira o ID do produto que deseja retirar quantidade: ");
        var id = Integer.parseInt(scanner.nextLine());
        System.out.println("Insira a quantidade que deseja remover do produto: ");
        var quantidade = Integer.parseInt(scanner.nextLine());

        produtoService.retirarQuantidade(id, quantidade);
        System.out.println("Pressione ENTER para voltar ao menu principal");
        scanner.nextLine();
    }

    private static void removerProduto(){
        System.out.println("Insira o ID do produto que deseja remover do estoque: ");
        var id = Integer.parseInt(scanner.nextLine());

        produtoService.deletarProduto(id);
        System.out.println("Pressione ENTER para voltar ao menu principal");
        scanner.nextLine();
    }
}
