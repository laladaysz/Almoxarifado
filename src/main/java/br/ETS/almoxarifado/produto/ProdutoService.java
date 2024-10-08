package br.ETS.almoxarifado.produto;

import br.ETS.almoxarifado.RegraDaAplicacaoException;
import br.ETS.almoxarifado.connection.ConnectionFactory;

import java.sql.Connection;
import java.util.ArrayList;

public class ProdutoService {
    private ArrayList<Produto> produtos = new ArrayList<>();

    private ConnectionFactory connectionFactory;

    public ProdutoService(){
        this.connectionFactory = new ConnectionFactory();
    }

    public void adicionarNovoProduto(ProdutoDTO dadosProduto){
        var produto = new Produto(dadosProduto);

        if(produtos.contains(produto)){
            throw new RegraDaAplicacaoException("Já existe um produto com este ID");
        }
        Connection connection = connectionFactory.recuperarConexao();
        new ProdutoDAO(connection).salvar(dadosProduto);
    }

    public ArrayList<Produto> exibirProdutosDoAlmoxarifado(){
        Connection connection = connectionFactory.recuperarConexao();
        return new ProdutoDAO(connection).listar();
    }

    public Produto encontrarProdutoPeloID(int id){
        Connection connection = connectionFactory.recuperarConexao();
        Produto produto = new ProdutoDAO(connection).listarPorID(id);

        if(produto!=null){
            return produto;
        }
        throw new RegraDaAplicacaoException("Produto não encontrado!");
    }

    public void adicionarQuantidadeDeUmProduto(int id, int quantidade){
        var produto = encontrarProdutoPeloID(id);
        if(quantidade <= 0){
            throw new RegraDaAplicacaoException("Quantidade a ser adicionada deve ser maior que 0");
        }
        Connection connection = connectionFactory.recuperarConexao();
        new ProdutoDAO(connection).alterar(produto.getId(), produto.getQuantidade() + quantidade);
    }

    public void removerQuantidadeDeUmProduto(int id, int quantidade){
        if(quantidade <= 0){
            throw new RegraDaAplicacaoException("Quantidade a ser removida deve ser maior que 0");
        }
        var produto = encontrarProdutoPeloID(id);
        if(produto.getQuantidade() < quantidade) {
            throw new RegraDaAplicacaoException("A quantidade inserida é maior que a quantia de estoque");
        }
        Connection connection = connectionFactory.recuperarConexao();
        new ProdutoDAO(connection).alterar(produto.getId(), produto.getQuantidade() - quantidade);
    }

    public void removerOProdutoDoAlmoxarifado(int id){
        if(encontrarProdutoPeloID(id) != null){
            Connection connection = connectionFactory.recuperarConexao();
            new ProdutoDAO(connection).deletar(id);
        } else {
            throw new RegraDaAplicacaoException("Não foi encontrado produto com esse ID");
        }
    }
}
