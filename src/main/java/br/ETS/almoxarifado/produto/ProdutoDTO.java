package br.ETS.almoxarifado.produto;

public record ProdutoDTO(
        int id,
        String produto,
        String partnumber,
        String divisao,
        int quantidade
) {
}
