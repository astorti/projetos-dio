## DESAFIO OTIMIZANDO O SISTEMA BANCÁRIO COM FUNÇÕES PYTHON

### Objetivos

- Separar as funções existentes de saque, depósito e extrato em funções.
    - função de saque deve receber os argumentos apenas por nome (keyword only)
        - sugestão de argumentos por nome: saldo, valor_saque, extrato, limite, numero_saques, limite_saques
        - sugestão de retorno: saldo e extrato
    - função de depósito deve receber os argumentos apenas por posição (positional only)
        - sugestão de argumentos por posição: saldo, valor_deposito, extrato
        - sugestão de retorno: saldo e extrato
    - função de extrato deve receber os argumentos por posição e nome
        - argumentos por posição: saldo
        - argumentos por nome: extrato

- Criar duas novas funções: cadastrar cliente e cadastrar conta bancária
    - cadastrar cliente
        - o programa deve armazenar os clientes em uma lista
        - um cliente é composto por: nome, data de mascimento, CPF e endereço
        - o endereço é uma string com formato: "logradouro, nro - bairro - cidade/sigla estado"
        - deve ser armazenado somente os números do CPF. 
        - não pode cadastrar 2 clientes com o mesmo CPF.
    - cadastrar conta corrente
        - o programa deve armazenar contas em uma lista
        - uma conta é composta por: agência, numero da conta e cliente
        - o número da conta é sequencial, iniciando em 1
        - o número da agência é fixo: 0001
        - o cliente pode ter mais de uma conta, mas uma conta pertence somente a um cliente.
    - DICA: para vincular um cliente a uma conta, filtre a lista de clientes buscando o CPF informado para cada cliente da lista.
    - opcional: criar função para listar contas