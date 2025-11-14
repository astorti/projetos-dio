namespace PaymentAPI_ProjetoFinal.Models
{
    public enum EnumStatus : int
    {
        AguardandoPagamento = 0,
        PagamentoAprovado = 1,
        EnviadoParaTransportadora = 2,
        Entregue = 3,
        Cancelada = 4
    }
}