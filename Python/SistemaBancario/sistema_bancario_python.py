def main():
    
    LIMITE_SAQUES = 3
    AGENCIA = "0001"

    saldo = 0
    limite = 500
    extrato = ""
    numero_saques = 0
    clientes = []
    contas = []
    numero_conta = 1

    menu = """
    [1] Novo Cliente
    [2] Listar Clientes
    [3] Nova Conta Corrente
    [4] Listar Contas Correntes
    [5] Depositar
    [6] Sacar
    [7] Extrato
    [0] Sair

    => """

    while True:

        opcao = input(menu)

        if opcao == "1":
            cliente = novo_cliente(clientes)
            if cliente:
                clientes.append(cliente)
                print("=== Cadastrado do cliente realizado com sucesso! ===")
        
        elif opcao == "2":
            listar_clientes(clientes)
        
        elif opcao == "3":
            cpf = input("Informe o CPF do cliente: ")
            conta = nova_conta_corrente(
                agencia=AGENCIA, 
                numero_conta=numero_conta, 
                cpf=cpf, 
                clientes=clientes
            )
            if conta:
                contas.append(conta)
                numero_conta += 1
                print ("Cadastro da conta realizada com sucesso!")

        elif opcao == "4":
            listar_contas_correntes(contas)

        elif opcao == "5":
            saldo, extrato = depositar(saldo, extrato)

        elif opcao == "6":
            valor = float(input("Informe o valor do saque: "))
            saldo, extrato, numero_saques = saque(
                saldo=saldo, 
                valor=valor, 
                extrato=extrato, 
                limite=limite, 
                numero_saques=numero_saques, 
                limite_saques=LIMITE_SAQUES)

        elif opcao == "7":
            exibir_extrato(saldo, extrato=extrato)

        elif opcao == "0":
            break

        else:
            print("Operação inválida, por favor selecione novamente a operação desejada.")


def novo_cliente(clientes):
    cpf = input("Informe o CPF (somente números): ")
    consulta_cliente = consultar_cliente(cpf, clientes)
    if consulta_cliente:
        print(f"Cliente com CPF: {cpf} já cadastrado")
    else:
        nome = input("Informe o nome completo: ")
        data_nascimento = input("Informe a data de nascimento (dd-mm-aaaa): ")
        endereco = input("Informe o endereço (logradouro, número - bairro - cidade/sigla estado): ")

        cliente = {
            "nome": nome,
            "data_nascimento": data_nascimento,
            "cpf": cpf,
            "endereco": endereco,
        }
        return cliente
        

def listar_clientes(clientes):
    print(" LISTA DE CLIENTES ".center(80, "="))
    for cliente in clientes:
        print(cliente)
        print("-" * 100)

def consultar_cliente(cpf, clientes):
    for cliente in clientes:
        if cliente["cpf"] == cpf:
            return cliente
        
def nova_conta_corrente(agencia, numero_conta, cpf, clientes):
    cliente = consultar_cliente(cpf, clientes)
    if cliente:   
        conta = {
            "agencia": agencia, 
            "numero_conta": numero_conta, 
            "cliente": cliente["cpf"],
        }
        return conta, cliente
    else:
        print("Cliente não encontrado. Não é possível efetuar o cadastro da conta.")
         
def listar_contas_correntes(contas):
    print(" LISTA DE CONTAS ".center(80, "="))
    for conta in contas:
        print(conta)
        print("-" * 100)

def saque(*, saldo, valor, extrato, limite, numero_saques, limite_saques):

    excedeu_saldo = valor > saldo
    excedeu_limite = valor > limite
    excedeu_saques = numero_saques >= limite_saques

    if excedeu_saldo:
        print("Operação falhou! Saldo insuficiente.")

    elif excedeu_limite:
        print("Operação falhou! O valor do saque excede o limite.")

    elif excedeu_saques:
        print("Operação falhou! Número máximo de saques excedido.")

    elif valor > 0:
        saldo -= valor
        extrato += f"Saque: R$ {valor:.2f}\n"
        numero_saques += 1
        
    else:
        print("Operação falhou! O valor informado é inválido.")

    return saldo, extrato, numero_saques

def depositar(saldo, extrato, /):
    valor = float(input("Informe o valor do depósito: "))
    if valor > 0:
        saldo += valor
        extrato += f"Depósito: R$ {valor:.2f}\n"
        return saldo, extrato

    else:
        print("Operação falhou! O valor informado é inválido.")

def exibir_extrato(saldo, /, *, extrato):
    print("\n================ EXTRATO ================")
    print("Não foram realizadas movimentações." if not extrato else extrato)
    print(f"\nSaldo: R$ {saldo:.2f}")
    print("==========================================")

main()