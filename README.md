<p align="center">
  <img width="50%" src="images/btg-logo.jpg" alt="BTG Pactual Logo">
</p>

<h3 align="center">Desafio Backend - BTG Pactual</h3>

[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=erichiroshi_desafio-btgpactual-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=erichiroshi_desafio-btgpactual-backend)
[![codecov](https://codecov.io/gh/erichiroshi/desafio-btgpactual-backend/graph/badge.svg?token=9GX7M67KWD)](https://codecov.io/gh/erichiroshi/desafio-btgpactual-backend)

<p align="center">
  <img src="https://img.shields.io/badge/Java-25-red?style=flat-square&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot 4.0.6">
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker&logoColor=white" alt="Docker Compose">
  <img src="https://img.shields.io/badge/RabbitMQ-FF6600?style=flat-square&logo=rabbitmq&logoColor=white" alt="RabbitMQ">
  <img src="https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Jacoco-70%25-brightgreen?style=flat-square" alt="Jacoco">
  <img src="https://img.shields.io/badge/SonarCloud-passing-4E9BCD?style=flat-square&logo=sonarcloud&logoColor=white" alt="SonarCloud">
  <img src="https://img.shields.io/badge/GitHub_Actions-CI%2FCD-2088FF?style=flat-square&logo=githubactions&logoColor=white" alt="GitHub Actions">
  <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">
  <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
</p>

---

## 🧭 Visão Geral

Este projeto implementa uma **solução backend** para o desafio técnico do **BTG Pactual**, com foco em **processamento de pedidos**, **integração assíncrona via RabbitMQ** e **persistência em MongoDB**.  
A aplicação consome mensagens de uma fila, processa os dados, calcula valores agregados e expõe uma **API RESTful** para consulta dos resultados.

---

## 📚 Sumário
- [🧭 Visão Geral](#-visão-geral)
- [📚 Sumário](#-sumário)
- [🛠️ Stack](#️-stack)
- [🏗️ Arquitetura](#️-arquitetura)
- [🚀 Execução do Projeto](#-quick-start)
  - [✅ Pré-requisitos](#-pré-requisitos)
  - [📥 Clonar o repositório](#-clonar-o-repositório)
  - [Modo desenvolvimento (API local + infra via Docker)](#modo-desenvolvimento-api-local--infra-via-docker)
  - [Modo produção (tudo via Docker)](#modo-produção-tudo-via-docker)
- [💬 Interagindo com a API](#-interagindo-com-a-api)
  - [Exemplo de mensagem na fila RabbitMQ](#exemplo-de-mensagem-na-fila-rabbitmq)
- [🤝 Contribuições](#-contribuições)
- [🔗 Referências e Créditos](#-referências-e-créditos)

---

## 🛠️ Stack

| Tecnologia       | Versão      | Papel                                    |
|-----------------|-------------|------------------------------------------|
| Java             | 25          | Linguagem                                |
| Spring Boot      | 4.1.0       | Framework web + DI                       |
| PostgreSQL       | 16          | Persistência                             |
| RabbitMQ         | 4           | Mensageria orientada a eventos           |
| ArchUnit         | 1.4.2       | Validação automatizada de arquitetura    |
| Testcontainers   | 1.21.0      | Testes de integração                     |
| Docker Compose   | v2+         | Orquestração local                       |

---

## 🏗️ Arquitetura

Fluxo principal:

```
RabbitMQ Queue (pedidos)
        ↓
OrderConsumer (Spring AMQP)
        ↓
Port/UseCases (processamento e agregação)
        ↓
Postgres (armazenamento dos pedidos)
        ↓
REST API (consultas)
```

---

## 🚀 Quick Start

### ✅ Pré-requisitos
- **Java 25+**
- **Gradle 9+**
- **Docker + Docker Compose v2+**

### 📥 Clonar o repositório

```bash
git clone https://github.com/erichiroshi/desafio-btgpactual-backend.git
cd desafio-btgpactual-backend
```

### Modo desenvolvimento (API local + infra via Docker)

```bash
# 1.Subir containers
docker compose -f docker-compose.dev.yml --env-file .env.dev up -d

# 2. Rodar a aplicação
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Modo produção (tudo via Docker)

```bash
docker compose -f docker-compose.prod.yml --env-file .env.prod up --build -d
```

---

## 📡 Endpoints

A aplicação estará disponível em:  
👉 [http://localhost:8080](http://localhost:8080)

- `GET /customers/{id}/orders` → Lista pedidos de um cliente
- `GET /customers/1/orders/summary` → Consulta total de pedidos e valor

---

## 💬 Interagindo com a API

Utilize o **Postman**, **Insomnia** ou `curl` para enviar e consultar pedidos.

### Exemplo de mensagem na fila RabbitMQ

```json
{
  "codigoPedido": 1001,
  "codigoCliente": 1,
  "itens": [
    { "produto": "lápis", "quantidade": 100, "preco": 1.10 },
    { "produto": "caderno", "quantidade": 10, "preco": 1.00 }
  ]
}
```

---

## 🤝 Contribuições

Contribuições são sempre bem-vindas!  
Para contribuir:

1. Crie um fork do repositório.
2. Crie uma branch de feature:
   ```bash
   git checkout -b feature/nome-da-feature
   ```
3. Commit suas mudanças:
   ```bash
   git commit -m "feat: nova funcionalidade"
   ```
4. Envie um Pull Request.

📜 **Boas práticas**
- Adicione testes unitários.
- Documente suas alterações no código.
- Use mensagens de commit seguindo o padrão **Conventional Commits**.

---

## 🔗 Referências e Créditos

- Desafio original: [PROBLEM.md](PROBLEM.md)
- Baseado no conteúdo do canal [Build & Run](https://www.youtube.com/watch?v=e_WgAB0Th_I&list=PLxCh3SsamNs7y1Y-QaVdWx0MUh0wvo7TV)
- Desenvolvido por [**Eric Hiroshi**](https://github.com/erichiroshi)
- Licença: [MIT](LICENSE)

---

<p align="center">
  <em>“Código limpo é aquele que foi escrito com clareza, empatia e propósito.”</em>
</p>
