package br.ETS.almoxarifado.produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection){
        this.connection = connection;
    }

    public void salvar(ProdutoDTO ProdutoDTO){
        String sql = "INSERT INTO tbmateriaisdiretos(ID, PRODUTO, PARTNUMBER, DIVISAO, QUANTIDADE) VALUES (?, ?, ?, ?, ?)";
        var produto = new Produto(ProdutoDTO);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,ProdutoDTO.id());
            preparedStatement.setString(2,ProdutoDTO.produto());
            preparedStatement.setString(3,ProdutoDTO.partNumber());
            preparedStatement.setString(4,ProdutoDTO.divisao());
            preparedStatement.setInt(5,ProdutoDTO.quantidade());

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Produto> listar(){
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM tbmateriaisdiretos";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String nomeProduto = resultSet.getNString(2);
                String partNumber = resultSet.getNString(3);
                String divisao = resultSet.getNString(4);
                int quantidade = resultSet.getInt(5);

                ProdutoDTO ProdutoDTO = new ProdutoDTO(id, nomeProduto, partNumber, divisao, quantidade);
                Produto produto = new Produto(ProdutoDTO);
                produtos.add(produto);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public Produto listarPorID(int id){
        String sql = "SELECT * FROM tbmateriaisdiretos WHERE id = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Produto produto = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int numeroID = resultSet.getInt(1);
                String nomeProduto = resultSet.getNString(2);
                String partNumber = resultSet.getNString(3);
                String divisao = resultSet.getNString(4);
                int quantidade = resultSet.getInt(5);

                ProdutoDTO ProdutoDTO = new ProdutoDTO(numeroID, nomeProduto, partNumber, divisao, quantidade);
                produto = new Produto(ProdutoDTO);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return produto;
    }

    public void alterar(int id, int quantidade){
        PreparedStatement preparedStatement;
        String sql = "UPDATE tbmateriaisdiretos SET quantidade = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, quantidade);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void deletar(int id){
        String sql = "DELETE FROM tbmateriaisdiretos WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
