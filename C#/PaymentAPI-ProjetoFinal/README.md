## PaymentAPI-ProjetoFinal

Trabalho final do Bootcamp Pottencial .NET Developer, no qual foi desenvolvido uma API de venda, em que seria necessário registrar uma venda informando um produto e um vendedor, conforme orientações no [repositório](https://gitlab.com/Pottencial/tech-test-payment-api) da Pottencial.

 **Objetivo:**
 Construir uma API REST utilizando .Net Core, Java ou NodeJs (com Typescript);
 A API deve expor uma rota com documentação swagger (http://.../api-docs).
 A API deve possuir 3 operações:
 Registrar venda: Recebe os dados do vendedor + itens vendidos. Registra venda com status "Aguardando pagamento";
 Buscar venda: Busca pelo Id da venda;
 Atualizar venda: Permite que seja atualizado o status da venda.

 OBS.: Possíveis status: Pagamento aprovado | Enviado para transportadora | Entregue | Cancelada.

 Uma venda contém informação sobre o vendedor que a efetivou, data, identificador do pedido e os itens que foram vendidos;
 O vendedor deve possuir id, cpf, nome, e-mail e telefone;
 A inclusão de uma venda deve possuir pelo menos 1 item;
 
 A atualização de status deve permitir somente as seguintes transições:
 > De: Aguardando pagamento<br>
 > Para: Pagamento Aprovado
 
 > De: Aguardando pagamento <br> 
 > Para: Cancelada

 > De: Pagamento Aprovado <br>
 > Para: Enviado para Transportadora

 > De: Pagamento Aprovado <br>
 > Para: Cancelada

 > De: Enviado para Transportador <br>
 > Para: Entregue

 A API não precisa ter mecanismos de autenticação/autorização;

 A aplicação não precisa implementar os mecanismos de persistência em um banco de dados, eles podem ser persistidos "em memória".
