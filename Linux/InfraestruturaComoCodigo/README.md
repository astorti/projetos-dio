### Infraestrutura como Código: Script de Criação de Estrutura de Usuários, Diretórios e Permissões

Projeto desenvolvido na **Formação Linux Fundamentals** que teve como objetivo criar um script para gerar automaticamente os usuários, diretórios, grupos e alterar permissões de acesso.

#### Comandos utilizados

`mkdir`: para criar diretórios<br/>
`groupadd`: para criar grupos<br/>
`useradd`: para criar usuários<br/>
`passwd`: para criar senha aos usuários<br/>
`chown`: para alterar o proprietário e grupo dos diretórios<br/>
`chmod`: para alterar as permissões de acessos ao diretórios

#### Estrutura do Script

- **Criar diretórios**: `adm`, `ven`, `sec` e `publico`
- **Criar grupos**: `GRP_ADM`, `GRP_VEN`, `GRP_SEC`
- **Criar usuários para os diferentes grupos**
- **Definir proprietários e permissões aos diretórios**