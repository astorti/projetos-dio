class Personagem {
    constructor(nome, idade, tipo) {
        this.nome = nome,
        this.idade = idade,
        this.tipo = tipo
    }

    atacar() {
        let personagens = {
            tipos: {
                0: ["mago","magia"],
                1: ["guerreiro","espada"],
                2: ["monge","artes marciais"],
                3: ["ninja","shuriken"],
            }
        }

        for(let index in personagens.tipos) {
            let [tipo, ataque] = personagens.tipos[index]
            if (this.tipo === tipo) {
                console.log(`o ${tipo} atacou usando ${ataque}`)
            }
        }
    }
}

let personagem1 = new Personagem("Ninja", 25, "ninja")
let personagem2 = new Personagem("Mago", 58, "mago")
let personagem3 = new Personagem("Guerreiro", 38, "guerreiro")
let personagem4 = new Personagem("Monge", 42, "monge")

let personagens = [personagem1, personagem2, personagem3, personagem4]
for (let index in personagens) {
    personagens[index].atacar()
}
