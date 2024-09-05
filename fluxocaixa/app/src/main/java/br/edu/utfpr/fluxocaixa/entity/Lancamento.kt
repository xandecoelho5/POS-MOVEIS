package br.edu.utfpr.fluxocaixa.entity

data class Lancamento(
    var _id: Int,
    var tipo: String,
    var detalhe: String,
    var valor: Double,
    var data_lancamento: String
) {
    override fun toString() = "${tipo[0]} $data_lancamento - $detalhe - R$ ${"%.2f".format(valor)}"
}