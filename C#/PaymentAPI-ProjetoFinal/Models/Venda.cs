using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace PaymentAPI_ProjetoFinal.Models
{
    public class Venda
    {
        public int Id { get; set; }
        public DateTime Data { get; set; }
        public int VendedorId { get; set; }
        public string Cpf { get; set; }
        public string Nome { get; set; }
        public string Email { get; set; }
        public string Telefone { get; set; }
        public int ProdutoId { get; set;  }
        public string Item { get; set; }
        public EnumStatus Status { get; set; }
        public string DescricaoStatus { get; set; }
    }
}