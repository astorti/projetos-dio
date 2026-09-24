#!/bin/bash

echo "Criando diretórios..."

mkdir /publico
mkdir /adm
mkdir /ven
mkdir /sec

echo "Criando grupos de usuários..."

groupadd GRP_ADM
groupadd GRP_VEN
groupadd GRP_SEC

echo "Criando usuários..."

# GRUPO GRP_ADM
echo "Registrando Charles"
useradd charles -m -s /bin/bash -G GRP_ADM
passwd charles
passwd charles -e

echo "Registrando Mary"
useradd mary -m -s /bin/bash -G GRP_ADM
passwd mary
passwd mary -e

echo "Registrando John"
useradd john -m -s /bin/bash -G GRP_ADM
passwd john
passwd john -e

# GRUPO GRP_VEN
echo "Registrando Paul"
useradd paul -m -s /bin/bash -G GRP_VEN
passwd paul
passwd paul -e

echo "Registrando Evelyn"
useradd evelyn -m -s /bin/bash -G GRP_VEN
passwd evelyn
passwd evelyn -e

echo "Registrando Elizabeth"
useradd elizabeth -m -s /bin/bash -G GRP_VEN
passwd elizabeth
passwd elizabeth -e

# GRUPO GRP_SEC
echo "Registrando Emily"
useradd emily -m -s /bin/bash -G GRP_SEC
passwd emily
passwd emily -e

echo "Registrando Frank"
useradd frank -m -s /bin/bash -G GRP_SEC
passwd frank
passwd frank -e

echo "Registrando Jack"
useradd jack -m -s /bin/bash -G GRP_SEC
passwd jack
passwd jack -e

echo "Especificando permissões dos diretórios...."

chown root:GRP_ADM /adm
chown root:GRP_VEN /ven
chown root:GRP_SEC /sec

chmod 770 /adm
chmod 770 /ven
chmod 770 /sec
chmod 777 /publico

echo "Fim"