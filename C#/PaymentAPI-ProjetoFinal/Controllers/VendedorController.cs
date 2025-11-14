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
    public class VendedorController : ControllerBase
    {
        private readonly VendaContext _context;

        public VendedorController(VendaContext context)
        {
            _context = context;
        }

        [HttpGet("BuscarVendedorPorId")]
        public IActionResult ObterPorId(int id)
        {
            var vendedor = _context.Vendedores.Find(id);
            {
                if (vendedor == null)
                {
                    return NotFound();
                }
            }
            
            return Ok(vendedor);
        }

        [HttpPut("AtualizarVendedorPorId")]
        public IActionResult Atualizar(int id, Vendedor vendedor)
        {
            var vendedorBanco = _context.Vendedores.Find(id);

            if (vendedorBanco == null)
                return NotFound();

            vendedorBanco.Cpf = vendedor.Cpf;
            vendedorBanco.Nome = vendedor.Nome;
            vendedorBanco.Email = vendedor.Email;
            vendedorBanco.Telefone = vendedor.Telefone;
           
            _context.Vendedores.Update(vendedorBanco);
            _context.SaveChanges();

            return Ok(vendedorBanco);
        }

        [HttpPost("CriarNovoVendedor")]
        public IActionResult Criar(Vendedor vendedor)
        {
            _context.Add(vendedor);
            _context.SaveChanges();
            
            return CreatedAtAction(nameof(ObterPorId), new { id = vendedor.Id }, vendedor);
        }

        [HttpDelete("ExcluirVendedorPorId")]
        public IActionResult Deletar(int id)
        {
            var vendedorBanco = _context.Vendedores.Find(id);

            if (vendedorBanco == null)
                return NotFound();

            _context.Vendedores.Remove(vendedorBanco);
            _context.SaveChanges();
            
            return NoContent();
        }


    }
}