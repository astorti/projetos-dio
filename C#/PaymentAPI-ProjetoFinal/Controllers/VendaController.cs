using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using PaymentAPI_ProjetoFinal.Context;
using PaymentAPI_ProjetoFinal.Models;

namespace PaymentAPI_ProjetoFinal.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class VendaController : ControllerBase
    {
        private readonly VendaContext _context;
        
        public VendaController(VendaContext context)
        {
            _context = context;
        }

        [HttpGet("BuscarVendaPorId")]
        public IActionResult ObterPorId(int id)
        {
            var venda = _context.Vendas.Find(id);
            {
                if (venda == null)
                {
                    return NotFound();
                }
            }
            
            return Ok(venda);
        }

        [HttpPut("AtualizarStatusPorId")]
        public IActionResult Atualizar(int id, Venda venda)
        {
            var vendaBanco = _context.Vendas.Find(id);

            if (vendaBanco == null)
                return NotFound();

            /*
                AguardandoPagamento == 0,
                PagamentoAprovado == 1,
                EnviadoParaTransportadora == 2,
                Entregue == 3,
                Cancelada == 4
            */
            if (vendaBanco.Status == EnumStatus.AguardandoPagamento) 
            {
                int vendaStatus = (int)venda.Status;

                if (vendaStatus == (int)EnumStatus.Entregue || vendaStatus == (int)EnumStatus.EnviadoParaTransportadora ) 
                {
                    return BadRequest("Status inválido. O pedido está aguardando pagamento.");
                }
                else if ( vendaStatus < 0 || vendaStatus > 4)
                {
                    return BadRequest("Status inexistente");
                }
                else
                {
                    vendaBanco.Status = venda.Status;
                }         
            } 
            else if (vendaBanco.Status == EnumStatus.PagamentoAprovado)
            {
                int vendaStatus = (int)venda.Status;
                if (vendaStatus == (int)EnumStatus.Entregue || vendaStatus == (int)EnumStatus.AguardandoPagamento ) 
                {
                    return BadRequest("Status inválido. O pedido já foi pago e será enviado para a transportadora.");
                }
                else if ( vendaStatus < 0 || vendaStatus > 4)
                {
                    return BadRequest("Status inexistente");
                }
                else
                {
                    vendaBanco.Status = venda.Status;
                }         
            } 
            else if (vendaBanco.Status == EnumStatus.EnviadoParaTransportadora)
            {
                int vendaStatus = (int)venda.Status;
                if (vendaStatus != (int)EnumStatus.Entregue ) 
                {
                    return BadRequest("Status inválido. Seu pedido já saiu para entrega.");
                }
                else
                {
                    vendaBanco.Status = venda.Status;
                }         
            } 
            else if (vendaBanco.Status == EnumStatus.Entregue)
            {
                return BadRequest("O pedido já foi entregue. O pedido não pode mais ser cancelado.");
            }
            else
            {
                return BadRequest("O pedido já está cancelado. Não é possível alterar o status. Realize uma nova venda.");
            }

            if (vendaBanco.Status == EnumStatus.AguardandoPagamento)
            {
                vendaBanco.DescricaoStatus = "Aguardando Pagamento";
            }
            else if (vendaBanco.Status == EnumStatus.PagamentoAprovado)
            {
                vendaBanco.DescricaoStatus = "Pagamento Aprovado";
            }
            else if (vendaBanco.Status == EnumStatus.EnviadoParaTransportadora)
            {
                vendaBanco.DescricaoStatus = "Enviado para a transportadora";
            }
            else if (vendaBanco.Status == EnumStatus.Entregue)
            {
                vendaBanco.DescricaoStatus = "Entregue";
            }
            else 
            {
                vendaBanco.DescricaoStatus = "Cancelado";
            }
    
            _context.Vendas.Update(vendaBanco);
            _context.SaveChanges();

            return Ok(vendaBanco);
        }

        [HttpPost("EfetuarUmaVenda")]
        public IActionResult Create(int vendedorId, int produtoId, Venda venda)
        {
            var produto = _context.Produtos.Find(produtoId);
            var vendedor = _context.Vendedores.Find(vendedorId);

            venda.VendedorId = vendedor.Id;
            venda.Nome = vendedor.Nome;
            venda.Cpf = vendedor.Cpf;
            venda.Email = vendedor.Email;
            venda.Telefone = vendedor.Telefone;

            venda.ProdutoId = produto.Id;
            venda.Item = produto.Item;

            venda.Status = EnumStatus.AguardandoPagamento;
            venda.DescricaoStatus = "Aguardando Pagamento";
            venda.Data = DateTime.Now;

            _context.Add(venda);
            _context.SaveChanges();
            
            return CreatedAtAction(nameof(ObterPorId), new { id = venda.Id }, venda);
        }
    }
}