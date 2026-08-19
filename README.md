# 💈 BarberPRO

Sistema de gerenciamento de agendamentos para barbearias, desenvolvido como projeto de estudo e portfólio com foco em **desenvolvimento backend**, organização em camadas e aplicação de regras de negócio.

O projeto está sendo desenvolvido com **Java, Spring Boot, Spring Data JPA e MySQL**, buscando simular situações reais de um sistema de agendamento.

---

## 🎯 Objetivo

O BarberPRO tem como objetivo permitir o gerenciamento de:

- Clientes
- Barbeiros
- Serviços
- Agendamentos

Além do cadastro e consulta dos dados, o sistema possui regras para evitar conflitos de horários e controlar o ciclo de vida dos agendamentos.

---

## 🛠️ Tecnologias

| Tecnologia        | Utilização                          |
| ----------------- | ----------------------------------- |
| Java 21           | Linguagem principal                 |
| Spring Boot 4.1.0 | Framework da aplicação              |
| Spring WebMVC     | Construção da API REST              |
| Spring Data JPA   | Persistência e acesso aos dados     |
| Spring Validation | Validação das informações recebidas |
| MySQL             | Banco de dados                      |
| Maven             | Gerenciamento e build do projeto    |
| Lombok            | Redução de código boilerplate       |
| Git / GitHub      | Versionamento                       |

---

## 🏗️ Arquitetura

O backend foi organizado seguindo uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

### Estrutura do projeto

```text
src/
└── main/
    ├── java/
    │   └── barberpro/
    │       ├── controller/
    │       │   ├── AgendamentoController.java
    │       │   ├── BarbeiroController.java
    │       │   ├── ClienteController.java
    │       │   └── ServicoController.java
    │       │
    │       ├── dto/
    │       │   ├── AgendamentoResponseDTO.java
    │       │   └── StatusAgendamentoDTO.java
    │       │
    │       ├── entity/
    │       │   ├── Agendamento.java
    │       │   ├── Barbeiro.java
    │       │   ├── Cliente.java
    │       │   ├── Servico.java
    │       │   └── StatusAgendamento.java
    │       │
    │       ├── exception/
    │       │   ├── GlobalExceptionHandler.java
    │       │   └── RecursoNaoEncontradoException.java
    │       │
    │       ├── repository/
    │       │   ├── AgendamentoRepository.java
    │       │   ├── BarbeiroRepository.java
    │       │   ├── ClienteRepository.java
    │       │   └── ServicoRepository.java
    │       │
    │       └── service/
    │           ├── AgendamentoService.java
    │           ├── BarbeiroService.java
    │           ├── ClienteService.java
    │           └── ServicoService.java
    │
    └── resources/
        └── application.properties
```

---

## 📋 Funcionalidades

### 👤 Clientes

- Listagem de clientes
- Cadastro de clientes
- Consulta por ID
- Exclusão de clientes
- Validação dos dados

### 💈 Barbeiros

- Listagem de barbeiros
- Cadastro de barbeiros
- Exclusão de barbeiros
- Validação de telefone

### ✂️ Serviços

- Listagem de serviços
- Cadastro de serviços
- Validação de serviços duplicados

### 📅 Agendamentos

- Cadastro de agendamentos
- Consulta de agendamentos
- Consulta por ID
- Atualização de agendamento
- Exclusão de agendamento
- Controle de status
- Validação de data e horário
- Validação de cliente
- Validação de barbeiro
- Validação de serviço
- Prevenção de conflito de horário

---

## 🔄 Status dos Agendamentos

Os agendamentos possuem um fluxo de status:

```text
AGENDADO
    ↓
CONFIRMADO
    ↓
CONCLUIDO
```

Também é possível cancelar um agendamento enquanto ele estiver em:

```text
AGENDADO → CANCELADO

CONFIRMADO → CANCELADO
```

Transições inválidas são bloqueadas pela regra de negócio.

---

## 🧠 Regras de negócio

O BarberPRO possui algumas regras para garantir a consistência dos dados.

### Conflito de horário

Um barbeiro não pode possuir dois agendamentos no mesmo horário.

Um cliente também não pode possuir dois agendamentos no mesmo horário.

### Data do agendamento

Não é permitido criar ou alterar um agendamento para uma data passada.

### Status

Agendamentos concluídos ou cancelados não podem ser alterados ou excluídos.

As transições de status são controladas pelo `AgendamentoService`.

### Integridade dos relacionamentos

Clientes, barbeiros e serviços precisam existir antes de serem utilizados em um agendamento.

---

## 🚨 Tratamento de exceções

O projeto possui um tratamento global através do:

```text
GlobalExceptionHandler
```

Alguns dos principais retornos são:

| Situação                         |              HTTP |
| -------------------------------- | ----------------: |
| Dados inválidos                  | `400 Bad Request` |
| Regra de negócio violada         | `400 Bad Request` |
| Recurso não encontrado           |   `404 Not Found` |
| Conflito de integridade no banco |    `409 Conflict` |

Exemplo:

```json
{
  "erro": "Agendamento não encontrado."
}
```

---

## 🔌 Principais endpoints

### Clientes

```http
GET    /clientes
GET    /clientes/{id}
POST   /clientes
DELETE /clientes/{id}
```

### Barbeiros

```http
GET    /barbeiros
POST   /barbeiros
DELETE /barbeiros/{id}
```

### Serviços

```http
GET  /servicos
POST /servicos
```

### Agendamentos

```http
GET    /agendamentos
GET    /agendamentos/{id}
POST   /agendamentos
PUT    /agendamentos/{id}
DELETE /agendamentos/{id}
```

O controle de status possui endpoint próprio para alteração do ciclo do agendamento.

---

## ▶️ Como executar o projeto

### Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 21
- MySQL
- Maven ou Maven Wrapper

### Banco de dados

Crie um banco MySQL para o projeto:

```sql
CREATE DATABASE barberpro;
```

Configure as informações de conexão no:

```text
src/main/resources/application.properties
```

### Executando com Maven Wrapper

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação será iniciada na porta:

```text
8080
```

API:

```text
http://localhost:8080
```

---

## 🧪 Testes da API

Os endpoints foram testados durante o desenvolvimento utilizando requisições HTTP.

Entre os cenários verificados estão:

- Cadastro e consulta de clientes
- Cadastro e consulta de barbeiros
- Cadastro e consulta de serviços
- Criação de agendamentos
- Consulta de agendamentos
- Cliente inexistente
- Barbeiro inexistente
- Serviço inexistente
- Data passada
- Barbeiro com horário ocupado
- Cliente com horário ocupado
- Alterações de status
- Transições de status inválidas
- Tratamento de recursos inexistentes
- Integridade de registros relacionados

---

## 📌 Status do projeto

🚧 **Em desenvolvimento**

O backend principal já possui as funcionalidades de gerenciamento de clientes, barbeiros, serviços e agendamentos, incluindo validações e regras de negócio.

As próximas etapas serão voltadas para documentação da API, melhorias e evolução da aplicação.

---

## 👨‍💻 Desenvolvedor

**Lucas Soares**

Projeto desenvolvido como parte da minha jornada de aprendizado em desenvolvimento de software, com foco em backend Java e construção de APIs REST.

---

## 📚 Aprendizados

Durante o desenvolvimento do BarberPRO, estou colocando em prática conceitos como:

- Programação em Java
- Orientação a objetos
- APIs REST
- Spring Boot
- Injeção de dependência
- JPA e Hibernate
- Relacionamentos entre entidades
- Validação de dados
- Regras de negócio
- Tratamento de exceções
- Banco de dados relacional
- Git e GitHub
- Organização de projetos backend

O projeto continuará evoluindo conforme novos conhecimentos forem sendo aplicados.
