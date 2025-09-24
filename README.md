# API-Guilherme

Este projeto foi construído com **Quarkus**, o *framework* Java Subatômico Supersônico.
Para saber mais sobre o Quarkus, visite o [site oficial](https://quarkus.io/).

---

## Modo de Desenvolvimento (Dev)

Para executar a aplicação em **modo de desenvolvimento**, que permite o *live coding*, utilize o comando:

```shell script
./mvnw quarkus:dev
NOTA: O Quarkus inclui uma Dev UI, disponível apenas em modo dev, acessível em http://localhost:8080/q/dev/.

Empacotando e Executando a Aplicação
Para empacotar a aplicação, execute:

Shell

./mvnw package
Este comando gera o arquivo quarkus-run.jar no diretório target/quarkus-app/. As dependências são copiadas para target/quarkus-app/lib/, portanto, não é um über-jar.

Para executar o arquivo gerado, use o comando:

Shell

java -jar target/quarkus-app/quarkus-run.jar
Se precisar de um über-jar (um único arquivo JAR com todas as dependências), utilize:

Shell

./mvnw package -Dquarkus.package.jar.type=uber-jar
O über-jar gerado pode ser executado com:

Shell

java -jar target/*-runner.jar
Executável Nativo
Para criar um executável nativo, utilize:

Shell

./mvnw package -Dnative
Se não tiver o GraalVM instalado, você pode usar um container para a build com o seguinte comando:

Shell

./mvnw package -Dnative -Dquarkus.native.container-build=true
O executável nativo pode ser executado com:

Shell

./target/api-guilherme-1.0.0-SNAPSHOT-runner
Para mais informações, consulte o guia de executáveis nativos.