package br.ETS.almoxarifado.produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutoDAO {
    private Connection connection;
    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar (ProdutoDTO dadosProduto){
        String sql = "INSERT INTO tbmateriaisdiretos(ID, PRODUTO, PARTNUMBER, DIVISAO, QUANTIDADE) VALUES (?, ?, ?, ?, ?)";
        var produto = new Produto(dadosProduto);

        try {
            PreparedStatement preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setInt(1, dadosProduto.id());
            preparedStatement.setString(2, dadosProduto.produto());
            preparedStatement.setString(3, dadosProduto.partnumber());
            preparedStatement.setString(4, dadosProduto.divisao());
            preparedStatement.setInt(5, dadosProduto.quantidade());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    public ArrayList<Produto> listar(){
        ArrayList<Produto> produtos = new ArrayList<>();

        String sql = "SELECT * FROM tbmateriaisdiretos";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);

                String nomeProduto = resultSet.getNString(2);
                String partNumber = resultSet.getNString(3);
                String divisao = resultSet.getNString(4);
                int quantidade = resultSet.getInt(5);

                ProdutoDTO dadosProduto = new ProdutoDTO(id, nomeProduto, partNumber, divisao, quantidade);
                Produto produto = new Produto(dadosProduto);
                produtos.add(produto);
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return produtos;
    }
}
