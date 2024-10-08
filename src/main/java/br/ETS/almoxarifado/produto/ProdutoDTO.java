package br.ETS.almoxarifado.produto;

public record ProdutoDTO(
        int id,
        String produto,
        String partNumber,
        String divisao,
        int quantidade
) {
}
