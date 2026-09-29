#!/bin/bash

echo "atualizando o servidor..."

apt-get update
apt-get upgrade -y
apt-get install apache2 -y
apt-get install unzip -y

echo "Baixando e copiando o arquivo da aplicação..."

cd /tmp
wget https://github.com/astorti/formacao-linux-fundamentals-DIO/archive/refs/heads/main.zip
unzip main.zip
cd formacao-linux-fundamentals-DIO-main
cp html/readme.html /var/www/html/