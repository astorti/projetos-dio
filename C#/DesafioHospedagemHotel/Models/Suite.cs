using System;
using System.Linq;
using System.Threading.Tasks;
using System.Collections.Generic;
using DesafioHospedagemHotel.Models;

namespace DesafioHospedagemHotel.Models
{
    public class Suite
    {
        public Suite() { }

        public Suite(string tipoSuite, int capacidade, decimal valorDiaria)
        {
            TipoSuite = tipoSuite;
            Capacidade = capacidade;
            ValorDiaria = valorDiaria;
        }

        public string TipoSuite { get; set; }
        public int Capacidade { get; set; }
        public decimal ValorDiaria { get; set; }
    }
}