package br.ETS.almoxarifado.produto;

import br.ETS.almoxarifado.RegraDaAplicacaoException;
import br.ETS.almoxarifado.connection.ConnectionFactory;

import java.sql.Connection;
import java.util.ArrayList;

public class ProdutoService {
    private ArrayList<Produto> produtos = new ArrayList<>();

    private ConnectionFactory connectionFactory= new ConnectionFactory();

    public void adicionarNovoProduto(ProdutoDTO data) {
        var produto = new Produto(data);

        if(produtos.contains(produto)) {
            throw new RegraDaAplicacaoException("Já existe um produto com esse ID.");
        }

        Connection connection = connectionFactory.recuperarConexao();
        new ProdutoDAO(connection).salvar(data);

    }

    public ArrayList<Produto> exibirProdutosDoAlmoxarifado () {
        Connection connection = connectionFactory.recuperarConexao();
        return new ProdutoDAO(connection).listar();
    }

    public Produto encontrarProdutoPeloID(int id){
        for(Produto produto: produtos) {
            if(produto.getId() == id) {
                return produto;
            }
        }
        throw new RegraDaAplicacaoException("Produto com este ID não foi encontrado");
    }

    public Produto encontrarPeloID (int id) {
        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst().orElseThrow( () -> new RegraDaAplicacaoException("Produto com esse ID não foi encontrado."));
    }

    public void adicionarQuantidadeDeUmProduto (int id, int quantidade) {
        var produto = encontrarProdutoPeloID(id);

        if (quantidade <= 0 ){
            throw new RegraDaAplicacaoException("Quantidade a ser adicionada deve ser maior que zero!");
        }

        produto.setQuantidade(produto.getQuantidade() + quantidade);

    }

    public void retirarQuantidade (int id, int quantidade) {
        var produto = encontrarProdutoPeloID(id);

        if (quantidade > produto.getQuantidade()) {
            throw new RegraDaAplicacaoException("Quantidade não disponível no estoque.");
        }

        if (quantidade <= 0) {
            throw new RegraDaAplicacaoException("A quantidade removida deve ser maior do que zero!");
        }

        produto.setQuantidade(produto.getQuantidade()-quantidade);

    }

    public void deletarProduto (int id) {
        var produto = encontrarProdutoPeloID(id);
        produtos.remove(produto);

    }
}
