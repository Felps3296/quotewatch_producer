# QuoteWatch 💵

Plataforma de observabilidade para coleta e monitoramento de cotações de câmbio em tempo real, construída para demonstrar arquitetura de sistemas distribuídos aplicada a dados financeiros.

## Sobre o projeto

O QuoteWatch simula um cenário real de infraestrutura de mercado financeiro: coleta contínua de cotações de câmbio a partir de uma API externa, processamento assíncrono via mensageria, persistência em banco relacional e visualização através de dashboards de observabilidade — toda a stack containerizada e testada.

O projeto nasceu como preparação técnica para um processo seletivo, e evoluiu para um portfólio completo cobrindo desenvolvimento backend, arquitetura orientada a eventos, testes automatizados e infraestrutura como código.

## Arquitetura

```bash
AwesomeAPI → Producer (Spring Boot) → Kafka → Consumer → PostgreSQL → Grafana
```

- **Producer**: coleta cotações automaticamente a cada 60 segundos e publica em um tópico Kafka
- **Kafka**: desacopla a coleta da persistência — mensagens não se perdem mesmo se o banco estiver temporariamente indisponível
- **Consumer**: escuta o tópico, converte os dados brutos e persiste no banco
- **PostgreSQL**: armazena o histórico completo de cotações coletadas
- **Grafana**: dashboards com cotação em tempo real, evolução histórica, estatísticas e indicador de saúde do pipeline

## Stack técnica

**Linguagem e framework:** Java 17 · Spring Boot · Spring Data JPA · Spring Kafka

**Infraestrutura:** Apache Kafka · PostgreSQL · Docker · Docker Compose

**Observabilidade:** Grafana · Kafka UI

**Testes:** JUnit 5 · Mockito · Awaitility (testes de integração com Kafka embarcado)

**Outros:** Lombok · Maven

## Destaques técnicos

- **Arquitetura orientada a eventos**: separação entre coleta (Producer) e persistência (Consumer) via Kafka, garantindo resiliência do pipeline
- **Testes em múltiplas camadas**:
  - Unitário puro (conversão de dados)
  - Unitário com mock (lógica de negócio isolada)
  - Integração HTTP end-to-end (API completa, banco real)
  - Integração de mensageria com Kafka embarcado (`@EmbeddedKafka` + `Awaitility`)
- **Containerização completa**: Dockerfile multi-stage, rede interna entre serviços, volumes persistentes para banco de dados e dashboards
- **Kafka multi-listener**: configuração que permite acesso simultâneo de dentro e de fora da rede Docker
- **Tratamento de erro estruturado**: exceções customizadas e handler global retornando respostas HTTP consistentes
- **Observabilidade real**: dashboard com indicador de saúde do pipeline baseado em frequência de coleta

## Endpoints da API

<div align="center">

<table>
  <tr>
    <th>Método</th>
    <th>Rota</th>
    <th>Descrição</th>
  </tr>
  <tr>
    <td><code>GET</code></td>
    <td><code>/api/cotacoes/{moeda}</code></td>
    <td>Retorna a última cotação coletada da moeda</td>
  </tr>
  <tr>
    <td><code>GET</code></td>
    <td><code>/api/cotacoes/{moeda}/historico</code></td>
    <td>Retorna o histórico completo de cotações da moeda</td>
  </tr>
</table>

</div>

## Como rodar o projeto

### Pré-requisitos

- Docker e Docker Compose instalados

### Subindo tudo com Docker Compose

```bash
cd infra
docker compose up -d --build
```

Isso sobe automaticamente:

| Serviço | Porta | Descrição |
|---|---|---|
| Aplicação (API) | `8081` | `http://localhost:8081/api/cotacoes/USD` |
| PostgreSQL | `5433` | Banco de dados |
| Kafka | `9092` / `9094` | Broker de mensageria |
| Kafka UI | `8082` | Interface visual do Kafka |
| Grafana | `3000` | Dashboards de observabilidade |

### Criando a tabela no banco (primeira execução)

```bash
docker exec -it postgres psql -U quotewatch_app -d quotewatch
```

```sql
CREATE TABLE cotacoes (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    moeda           VARCHAR(10)     NOT NULL,
    valor_compra    NUMERIC(18,6)   NOT NULL,
    valor_venda     NUMERIC(18,6)   NOT NULL,
    variacao_pct    NUMERIC(8,4),
    data_cotacao    TIMESTAMP       NOT NULL,
    data_coleta     TIMESTAMP       NOT NULL
);
```

### Rodando os testes

```bash
./mvnw test
```

## Dashboard

O Grafana expõe um dashboard com os seguintes painéis:

- 💵 Cotação atual do dólar
- 📈 Evolução da cotação nas últimas 24h
- 📊 Média das últimas 24h
- 🔀 Maior e menor cotação
- 📦 Total de coletas e detalhamento das últimas coletas

<img width="1603" height="735" alt="image" src="https://github.com/user-attachments/assets/30c2c59c-c813-4a54-bed9-bb57f679e790" />
<img width="1606" height="889" alt="image" src="https://github.com/user-attachments/assets/33d71d21-0993-4f50-ab96-3f7fee306b12" />

## Autor

Felipe Reis
[LinkedIn](https://linkedin.com/in/felipereis-5a5658227) · [GitHub](https://github.com/Felps3296)
