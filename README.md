# Consumo de Web Service ViaCEP

Objetivo:
O objetivo deste projeto é consumir o web service ViaCEP para buscar informações de endereço a partir de um CEP fornecido pelo usuário. Caso o CEP já tenha sido consultado anteriormente, os dados são recuperados de um banco de dados local, otimizando o tempo de resposta nas consultas subsequentes.

Fluxo do Progama:
1-O sistema solicita um CEP ao usuário.  
2-O sistema verifica no banco de dados se o CEP já foi consultado anteriormente.  
Se o CEP existir no banco, as informações são exibidas para o usuário.  
Se o CEP não existir no banco, o sistema busca as informações na API ViaCEP.  
Os dados do endereço, juntamente com a data e hora da consulta, são salvos no banco de dados local.  
O sistema informa ao usuário que os dados foram obtidos via API e armazenados para futuras consultas.

Tecnologias utilizadas:
- **Java 23**: Linguagem de programação utilizada.
- **Jakarta Persistence (JPA)**: Para interagir com o banco de dados.
- **Hibernate**: Framework ORM para persistência de dados.
- **JDBC (PostgreSQL)**: Para conexão com o banco de dados.
- **API ViaCEP**: Para obter as informações de endereço a partir do CEP.
- **Jackson**: Para conversão dos dados JSON da API para objetos Java.
