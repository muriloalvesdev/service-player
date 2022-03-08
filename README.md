[![Build Status](https://app.travis-ci.com/muriloalvesdev/service-player.svg?branch=main)](https://app.travis-ci.com/github/muriloalvesdev/service-player)

# Service Player
## Tecnologias 

- [Spring Boot](https://spring.io/projects/spring-boot) - Framework de Desenvolvimento para a Linguagem Java.

- [Lombok](https://projectlombok.org/) - Biblioteca Java focada em produtividade e redução de código boilerplate que, por meio de anotações adicionadas ao nosso código, ensinamos o compilador (maven ou gradle) durante o processo de compilação a criar código Java.

- [JUnit5](https://junit.org/junit5/) - Framework facilita a criação e manutenção do código para a automação de testes com apresentação dos resultados.

- [Mockito](https://site.mockito.org/) - Estrutura de teste de código aberto para Java liberada sob a licença MIT. A estrutura permite a criação de objetos duplos de teste em testes de unidade automatizados com o objetivo de desenvolvimento orientado a teste ou desenvolvimento orientado a comportamento.

- [H2](https://www.h2database.com/html/main.html) - Banco de dados.

- [Docker](https://www.docker.com/) - Plataforma open source que facilita a criação e administração de ambientes isolados. Ele possibilita o empacotamento de uma aplicação ou ambiente dentro de um container, se tornando portátil para qualquer outro host que contenha o Docker instalado.

- [Swagger](https://swagger.io/) - Essencialmente uma linguagem de descrição de interface para descrever APIs RESTful expressas usando JSON.

## Pré requisitos - Configuração
- Docker.
- Maven.

## Utitilização da aplicação
- Clone o repositório: `git clone https://github.com/muriloalvesdev/service-player.git`
- Acesse o diretório da aplicação: `cd service-player`
- Execute o script docker para fazer um build da imagem e subir o container: `chmod u+x docker-run.sh && ./docker-run.sh`
- Comando `chmod` [Mais informações](https://guialinux.uniriotec.br/chmod/)
- Realize o cadastro de seu usuário através do curl abaixo:
```curl --location --request POST 'http://localhost:8080/register' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DD95E3D59EFAC569C34C66771C626EA8' \
--data-raw '{
    "firstName": "seu primeiro nome",
    "lastName": "seu ultimo nome",
    "email": "seuemail@teste.com.br",
    "password": "sua senha"
}'
```
- Após cadastrar seu usuário, realize login para obter seu `access_token`, utilize o comando curl abaixo:
```curl --location --request POST 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DD95E3D59EFAC569C34C66771C626EA8' \
--data-raw '{
    "email": "seuemail@teste.com.br",
    "password": "sua senha"
}'
```

- Utilize para seu access_token para as próximas requisições, utilize autenticação do tipo `Bearer Token`

- Inicie o jogo através do endpoint start:
```
curl --location --request GET 'localhost:8080/game/start/seuemail@teste.com.br' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtdXJpbG9oZW5yaXF1ZS50aUBvdXRsb29rLmNvbS5iciIsImlhdCI6MTY0NjcwODM1MiwidXNlcm5hbWUiOiJNdXJpbG8iLCJleHAiOjE2NDY3MTAxNTJ9.zaMglKwApCN0KFM0D3ClQFGYon5Asu9VneGJIVv0RvBgJuwC__KNHO6C_FBaJ89E8WsBIqPaf7BND0OY75UYYw' \
--header 'Cookie: JSESSIONID=23FBB6A6E4096E27D674D96364F5ADB6'
```

- Você terá uma lista de filmes como resposta, exemplo:
```
[
    {
        "imdbRating": 0.0,
        "imdbTotalVotes": 0.0,
        "title": "The Shawshank Redemption",
        "image": "https://imdb-api.com/images/original/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6716_AL_.jpg"
    },
    {
        "imdbRating": 0.0,
        "imdbTotalVotes": 0.0,
        "title": "The Godfather",
        "image": "https://imdb-api.com/images/original/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_Ratio0.7015_AL_.jpg"
    }
]
```

- Envie essa mesma lista para o endpoint abaixo: no campo {nome do filme escolhido, você deve passar o nome do filme que você acredita ter um rating maior}
```
curl --location --request POST 'http://localhost:8080/game/{nome do filme escolhido}/seuemail@teste.com.br' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtdXJpbG9oZW5yaXF1ZS50aUBvdXRsb29rLmNvbS5iciIsImlhdCI6MTY0NjcwODM1MiwidXNlcm5hbWUiOiJNdXJpbG8iLCJleHAiOjE2NDY3MTAxNTJ9.zaMglKwApCN0KFM0D3ClQFGYon5Asu9VneGJIVv0RvBgJuwC__KNHO6C_FBaJ89E8WsBIqPaf7BND0OY75UYYw' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=23FBB6A6E4096E27D674D96364F5ADB6' \
--data-raw '[
    {
        "imdbRating": 0.0,
        "imdbTotalVotes": 0.0,
        "title": "The Shawshank Redemption",
        "image": "https://imdb-api.com/images/original/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6716_AL_.jpg"
    },
    {
        "imdbRating": 0.0,
        "imdbTotalVotes": 0.0,
        "title": "The Godfather",
        "image": "https://imdb-api.com/images/original/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_Ratio0.7015_AL_.jpg"
    }
]'
```

- Agora, você deve realizar uma requisição em:
```
curl --location --request GET 'http://localhost:8080/game/playing/seuemail@teste.com.br' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtdXJpbG9oZW5yaXF1ZS50aUBvdXRsb29rLmNvbS5iciIsImlhdCI6MTY0NjcwODM1MiwidXNlcm5hbWUiOiJNdXJpbG8iLCJleHAiOjE2NDY3MTAxNTJ9.zaMglKwApCN0KFM0D3ClQFGYon5Asu9VneGJIVv0RvBgJuwC__KNHO6C_FBaJ89E8WsBIqPaf7BND0OY75UYYw' \
--header 'Cookie: JSESSIONID=23FBB6A6E4096E27D674D96364F5ADB6'
```

Para obter mais dois filmes e assim realiar um novo request no endpoint `match`.

- O fluxo é esse: start > match > playing > match > playing > match (até que você acerte 3 filmes ou erre 3 filmes). 
- Caso não tenha interesse em baixar o projeto e realizar esses procedimentos, você pode baixar a imagem pronta [aqui](https://hub.docker.com/repository/docker/muriloalvesdev/serviceplayer).
