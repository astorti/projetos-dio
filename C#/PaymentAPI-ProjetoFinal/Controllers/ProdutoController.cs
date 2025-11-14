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
    public class ProdutoController : ControllerBase
    {
        private readonly VendaContext _context;

        public ProdutoController(VendaContext context)
        {
            _context = context;
        }

        [HttpGet("BuscarProdutoPorId")]
        public IActionResult ObterPorId(int id)
        {
            var produto = _context.Produtos.Find(id);
            {
                if (produto == null)
                {
                    return NotFound();
                }
            }
            
            return Ok(produto);
        }

        [HttpPut("AtualizarProdutoPorId")]
        public IActionResult Atualizar(int id, Produto produto)
        {
            var produtoBanco = _context.Produtos.Find(id);

            if (produtoBanco == null)
                return NotFound();

            produtoBanco.Item = produto.Item;
            
            _context.Produtos.Update(produtoBanco);
            _context.SaveChanges();

            return Ok(produtoBanco);
        }

        [HttpPost("CriarNovoProduto")]
        public IActionResult Criar(Produto produto)
        {
            _context.Add(produto);
            _context.SaveChanges();
            
            return CreatedAtAction(nameof(ObterPorId), new { id = produto.Id }, produto);
        }

        [HttpDelete("ExcluirUmProdutoPorId")]
        public IActionResult Deletar(int id)
        {
            var produtoBanco = _context.Produtos.Find(id);

            if (produtoBanco == null)
                return NotFound();

            _context.Produtos.Remove(produtoBanco);
            _context.SaveChanges();
            
            return NoContent();
        }
    }
}