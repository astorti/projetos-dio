# Documentando e Testando sua API REST com Kotlin

Projeto final desenvolvido na Formação Kotlin, utilizando a API de crédito desenvolvida anteriormente durante o curso de Spring Boot como base para a implementação de documentação da API e testes automatizados.

O objetivo deste desafio foi aplicar, sobre a API existente, conceitos de documentação com OpenAPI/Swagger e testes unitários e de integração.

### Tecnologias

* **API**
  * Kotlin 2.3.21
  * Java 17
  * Gradle 9.7.1
  * Spring Boot 4.1.1
  * Spring Data JPA
  * Hibernate ORM 7.4.5.Final
  * Hibernate Validator 9.1.3.Final
  * Flyway 12.4.0
  * H2 Database 2.4.240

* **Documentação**
  * springdoc-openapi 3.1.1
  * OpenAPI / Swagger UI

* **Testes**
  * JUnit 5
  * AssertJ
  * MockK 1.14.11
  * MockMvc

### Documentações oficiais

[Kotlin](https://kotlinlang.org/docs/home.html) | [Spring Boot](https://docs.spring.io/spring-boot/index.html) | [springdoc-openapi](https://springdoc.org/) | [OpenAPI Specification](https://spec.openapis.org/oas/latest.html) | [Swagger](https://swagger.io/docs/) | [JUnit 5](https://junit.org/) | [AssertJ](https://assertj.github.io/doc/) | [MockK](https://mockk.io/) | [Gradle](https://docs.gradle.org/current/userguide/userguide.html)

### Diferenças em relação ao curso

O projeto foi desenvolvido acompanhando o conteúdo do curso, porém utilizando versões mais atuais das tecnologias disponíveis no momento do estudo, de modo que foram realizadas algumas adaptações, quando necessário, para manter o projeto funcional com as versões atualizadas.

Principais diferenças:

* Spring Boot: curso baseado em Spring Boot 3.x; projeto desenvolvido com Spring Boot 4.1.1.
* springdoc-openapi: curso utiliza 2.0.2; projeto utiliza 3.1.1, versão compatível com Spring Boot 4.
* Jackson: o curso utiliza `com.fasterxml.jackson.databind.ObjectMapper`; no Spring Boot 4 foi utilizado `tools.jackson.databind.ObjectMapper`, correspondente ao Jackson 3 adotado na configuração atual.
* Spring SQL initialization: `spring.datasource.initialization-mode` utilizado em versões anteriores foi substituído por `spring.sql.init.mode`.

### Desafios Sugeridos

Além da documentação e testes implementados durante o curso do projeto, foram sugeridas algumas implementações extras como desafios, sendo adicionadas as seguintes implementações:

**Documentação Swagger**

  * `OpenAPI`
  * `Info`
  * `@Operation`
  * `@Parameter`

**Testes automatizados**

Foram desenvolvidos testes unitários para `CreditService`, utilizando MockK para isolar as dependências e verificar comportamentos, exceções e interações.

Cenários implementados:

1. Criação de crédito.
2. Rejeição de crédito com data inválida para a primeira parcela.
3. Busca de créditos por ID do cliente.
4. Busca de crédito por código.
5. Exceção quando o código do crédito não existe.
6. Exceção quando o crédito pertence a outro cliente.

Também foi desenvolvido, como desafio da formação, o `CreditResourceTest`, para testes de integração do `CreditResource` da API.

Cenários implementados:

1. Salvar crédito e retornar `201 Created`.
2. Rejeitar crédito com data inválida e retornar `400 Bad Request`.
3. Buscar créditos por ID do cliente e retornar `200 OK`.
4. Buscar crédito por código e retornar `200 OK`.
5. Rejeitar busca de código inexistente e retornar `400 Bad Request`.
6. Rejeitar acesso a crédito pertencente a outro cliente e retornar `400 Bad Request`.

Os testes de integração utilizam o banco H2 configurado para o ambiente de testes.

### Testar a API

Após rodar o projeto, consultar os seguintes endereços no navegador para verificar o funcionamento:

**Documentação Swagger**: <br/>
Endereço: `http://localhost:8080/swagger-ui/index.html`

**Banco de Dados**:<br/>
Endereço: `http://localhost:8080/h2-console`<br/>
No H2 Console informar a seguinte JDBC URL: `jdbc:h2:mem:credit-application-system-db`