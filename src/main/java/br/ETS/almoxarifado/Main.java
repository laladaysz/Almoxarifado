package br.ETS.almoxarifado;

import br.ETS.almoxarifado.produto.Produto;
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


    }
