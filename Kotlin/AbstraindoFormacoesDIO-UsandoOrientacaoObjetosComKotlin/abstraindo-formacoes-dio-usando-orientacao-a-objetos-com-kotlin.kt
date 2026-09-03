enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

data class Usuario (val nome: String) {
    val matriculas = mutableListOf<Formacao>()
}

data class ConteudoEducacional(var nome: String, val duracao: Int = 60, val nivel: Nivel)
data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>) {

    val inscritos = mutableListOf<Usuario>()
    
    fun matricular(usuario: Usuario) {
        //TODO("Utilize o parâmetro $usuario para simular uma matrícula (usar a lista de $inscritos).")
        inscritos.add(usuario)
        usuario.matriculas.add(this)
    }
}

fun main() {
    //TODO("Analise as classes modeladas para este domínio de aplicação e pense em formas de evoluí-las.")
    //TODO("Simule alguns cenários de teste. Para isso, crie alguns objetos usando as classes em questão.")
    
    val john: Usuario = Usuario("John")
    val frank: Usuario = Usuario("Frank")
    
    val cursoKotlinBasico: ConteudoEducacional = ConteudoEducacional("Kotlin Básico", 60, Nivel.BASICO)
    val cursoKotlinIntermediario: ConteudoEducacional = ConteudoEducacional("Kotlin Intermediário", 60, Nivel.INTERMEDIARIO)
    val cursoKotlinAvancado: ConteudoEducacional = ConteudoEducacional("Kotlin Avançado", 120, Nivel.DIFICIL)
    
    val formacaoKotlin: Formacao = Formacao("Formação Kotlin", conteudos = listOf(cursoKotlinBasico, cursoKotlinIntermediario, cursoKotlinAvancado))
    
    val cursoAndroidBasico: ConteudoEducacional = ConteudoEducacional("Android Básico", 60, Nivel.BASICO)
    val cursoAndroidIntermediario: ConteudoEducacional = ConteudoEducacional("Android Intermediário", 60, Nivel.INTERMEDIARIO)
    val cursoAndroidAvancado: ConteudoEducacional = ConteudoEducacional("Android Avançado", 120, Nivel.DIFICIL)
    
    val formacaoAndroid: Formacao = Formacao("Formação Android", conteudos = listOf(cursoAndroidBasico, cursoAndroidIntermediario, cursoAndroidAvancado))
        
    formacaoKotlin.matricular(john)
    formacaoAndroid.matricular(frank)
    formacaoAndroid.matricular(john)
        
    println("Inscritos na Formação Kotlin")
    formacaoKotlin.inscritos.forEach {
        println(it)
    }
    
    println("===============================================")
    
    println("Inscritos na Formação Android")
    formacaoAndroid.inscritos.forEach {
        println(it)
    }
    
    println("===============================================")
    
    println("John (matriculas)")
    john.matriculas.forEach {
        println(it)
    }
    
    println("===============================================")
    
    println("Frank (matriculas)")
    frank.matriculas.forEach {
        println(it)
    }
}
