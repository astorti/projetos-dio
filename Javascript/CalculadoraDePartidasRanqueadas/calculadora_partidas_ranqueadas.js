let vitorias = 0
let derrotas = 0
let nivel = ""

function calcularSaldoDeVitorias(vitorias, derrotas) {
    while(derrotas >= vitorias) {
        vitorias = Math.floor(Math.random() * 100) + 10
        derrotas = Math.floor(Math.random() * 100) + 5
    }
    let saldo = vitorias - derrotas
    return saldo
}

let saldoVitorias = calcularSaldoDeVitorias(vitorias, derrotas)

switch(true) {
    case saldoVitorias <= 10:
        nivel = "Ferro"
        break
    case saldoVitorias <= 20:
        nivel = "Bronze"
        break
    case saldoVitorias <= 50:
        nivel = "Prata"
        break
    case saldoVitorias <= 80:
        nivel = "Ouro"
        break
    case saldoVitorias <= 90:
        nivel = "Diamante"
        break
    case saldoVitorias <= 100:
        nivel = "Lendário"
        break
    case saldoVitorias >= 101:
        nivel = "Imortal"
        break
}

console.log(`O Herói tem saldo de ${saldoVitorias} e está no nível de ${nivel}`)
