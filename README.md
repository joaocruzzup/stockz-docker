<h1 align="center">
💻📊<br>StockZ API - Gerenciamento de estoques
</h1>

## Sumário
1. [Executando o Projeto com Docker](#-executando-o-projeto-com-docker)
2. [O que é a Stockz API?](#-o-que-é-a-stockz-api)
3. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
4. [Funcionalidades da StockZ API](#-funcionalidades-da-stockz-api)
5. [Tutorial de uso da StockZ API](#-tutorial-de-uso-da-stockz-api)
6. [Acessando a documentação da API](#-acessando-a-documentação-da-api)
7. [Acessando o banco de dados (H2) ](#-acessando-o-banco-de-dados-h2-)
8. [Alterando o banco de dados (H2) para dados serem persistidos](#-alterando-o-banco-de-dados-h2-para-dados-serem-persistidos)
9. [Alterando o banco de dados para o Postgres](#-alterando-o-banco-de-dados-para-o-postgres)
10. [Melhorias para futuras atualizações](#-melhorias-para-futuras-atualizações)
11. [Autor](#-autor)

## 🐋 Executando o Projeto com Docker

Passo 1: Clonando o Repositório
Primeiro, você precisa clonar o repositório do projeto para o seu computador local. Use o seguinte comando para clonar o repositório a partir do GitHub:
`git clone https://github.com/joaocruzzup/stockz-docker.git`

Passo 2: Navegando para o Diretório do Projeto

Após a conclusão da clonagem, navegue para o diretório do projeto:

`cd stockz-docker`

Passo 3: Construindo a Imagem Docker
Certifique-se de que você está no diretório raiz do stockz-docker, onde o arquivo Dockerfile está localizado. Use o seguinte comando para construir a imagem Docker:

`docker build -t stockz:latest .`

Passo 4: Executando o Contêiner Docker
Após a construção bem-sucedida da imagem Docker, você pode executar o projeto como um contêiner Docker com o seguinte comando:

`docker run -p 8080:8080 stockz:latest`

Passo 5: Acessando o Projeto
Após a execução do contêiner, o seu projeto estará disponível na porta especificada do host (neste caso, a porta 8080). Você pode acessá-lo em um navegador da web ou por meio de chamadas de API, dependendo do tipo de projeto que você está executando.


## 📚 O que é a Stockz API?
O StockZ API é ma API REST para o gerenciamento eficiente de estoques de produtos, oferecendo recursos para categorização, controle de produtos, interações com compradores e fornecedores, registro de transações de entrada e saída, além da geração de relatórios detalhados para a gestão dos estoques.

## 🖥️ Tecnologias Utilizadas

* `Java 11` - Linguagem de programação
* `Spring Boot (2.7.15)` - Framework para criação de aplicativos Java 
* `Spring Boot Data JPA` - Facilita o acesso a bancos de dados relacionais.
* `Spring Boot Validation` - Biblioteca que ajuda na validação de entrada de dados em aplicativos Spring Boot.
* `Spring Boot Web` - Facilita o desenvolvimento de aplicativos da web usando o Spring Boot.
* `H2 Database (Runtime)` - Um banco de dados SQL leve e embutido que é executado em tempo de execução.
* `Lombok` - Uma biblioteca Java que ajuda a reduzir a verbosidade do código
* `TextPDF (5.5.13.3)` - Uma biblioteca para a geração de documentos PDF em Java.
* `Springdoc OpenAPI UI (1.7.0)` -  Uma ferramenta que gera automaticamente a documentação da API com base nas anotações do Spring.

---

## 🧩 Funcionalidades da StockZ API

📔 `Gerenciamento de Categorias`: 

Crie, liste, atualize e exclua categorias de produtos.
Categorize seus produtos de acordo com diferentes critérios, tornando a organização mais eficiente.


🪪 `Gestão de Compradores e Fornecedores`

Registre detalhes de compradores e fornecedores, como nome, endereço, informações de contato e outras informações relevantes.
Mantenha um registro centralizado de todas as partes envolvidas nas transações comerciais.

📲 `Cadastro de Produtos`

Cadastre novos produtos, incluindo informações detalhadas como nome, descrição, preço unitário e quantidade em estoque.
Atualize os detalhes dos produtos sempre que necessário.

📊 `Controle de Estoque`

Acompanhe o estoque atual de produtos em tempo real.
Registre entradas e saídas de produtos para manter um controle preciso do estoque disponível.

↔️ `Gestão de Transações`

Registre todas as transações comerciais, como compras e vendas.
Mantenha um histórico completo das transações, incluindo produtos envolvidos, datas e valores.

📄 `Geração de Relatórios em PDF`

Crie relatórios detalhados em formato PDF, permitindo uma análise mais aprofundada dos dados.
Esses relatórios podem ser usados para tomada de decisões, contabilidade e auditorias.

---

## 📃 Tutorial de uso da StockZ API

Siga esse tutorial para gerenciar categorias, produtos, fornecedores, compradores e realizar transações de entrada e saída. Certifique-se de seguir a ordem indicada para obter os melhores resultados.

1. Cadastrar uma Categoria
Primeiro, você precisa cadastrar uma categoria para classificar seus produtos.

2. Cadastrar um Produto
Agora, crie um novo produto associado à categoria que você cadastrou no Passo 1.

3. Cadastrar um Fornecedor
Registre um fornecedor para estabelecer uma relação comercial.

4. Realizar uma Transação de Entrada
Execute uma transação de entrada para adicionar produtos ao estoque.

5. Cadastrar um Comprador
Cadastre um comprador para representar um cliente.

6.  Realizar uma Transação de Saída
Execute uma transação de saída para registrar uma venda.

7.  Gerar um Relatório em PDF
Agora que você realizou transações, pode gerar um relatório em PDF.

---

### 📚 Acessando a documentação da API

A documentação completa da API está disponível através do Swagger UI. Você pode acessá-la através da seguinte URL: `http://localhost:8080/swagger-ui/index.html`

O Swagger UI fornece uma interface interativa que permite explorar e testar os endpoints da API, bem como visualizar detalhes sobre os parâmetros, respostas e exemplos de uso.

---

## 🏦 Acessando o banco de dados (H2) 

1. Certifique-se de que sua aplicação Spring Boot esteja em execução. Se você não tiver iniciado a aplicação, faça isso executando o aplicativo Spring Boot.

2. Abra um navegador da web de sua escolha.

3. Acesse a URL do console do H2 no seguinte endereço:

`http://localhost:8080/h2-console`

4. Preencha os campos da página de login com as seguintes informações:

```
Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:mem:stockz 

User Name: sa

Password: Deixe este campo em branco.
```

5. Clique no botão "Connect" para fazer login no console do H2.

**OBS.: Lembre-se de que o console do H2 só estará disponível enquanto sua aplicação Spring Boot estiver em execução.** 

---

### 🏦 Alterando o banco de dados (H2) para dados serem persistidos

1. Acesse o application.properties que está dentro da pasta /main/resources

2. Altere a URL: 
`spring.datasource.url=jdbc:h2:mem:stockz`
para `spring.datasource.url=jdbc:h2:file:C:/data/demodb`

---

### 🏦 Alterando o banco de dados para o Postgres

1. Acesse o application.properties que está dentro da pasta /main/resources

2. Comente as configurações do H2 utilizando `#` na frente das linhas abaixo do escrito `### #### Configuração H2 ####`

3. Descomente as configurações do Postgres deletando os `#` na frente das linhas abaixo do escrito `### #### Configuração POSTGRES ####`

---

## 🚧 Melhorias para futuras atualizações

- Refatorar códigos de Transações, Transações de Entrada e Transações de Saída para facilitar o uso dessas classes.
- Criar uma forma de Transações de Entrada e Transações de Saída terem Ids separados e não apenas vinculados a Transações.
- Refatorar códigos de Usuários, Fornecedores e Compradores para facilitar o uso dessas classes.
- Criar uma forma de Compradores e Fornecedores de Saída terem Ids separados e não apenas vinculados a Usuários.
- Preencher todos os atributos para as entidades a fim de tornar o projeto mais completo.
- Gerar relatórios de Usuários.
- Realizar cobertura de 100% dos testes em todas as classes

---

## 👨‍💻 Autor

Nome: João Cruz<br>Linkedin: https://www.linkedin.com/in/joaosilvacruz/

---

<h4 align=center>Made with 💚 by <a href="https://gith








