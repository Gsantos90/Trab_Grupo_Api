# Clean Tech (branch: develop)

Grupo 3

Integrantes:
- Gabriel Speridião dos Santos
- PAMELA MIRANDA PEREIRA DE ASSIS
- Diego Sartorio Seguro
- CLAUDIANE APARECIDA SUTIL GEREMIAS
- Ireni Iachechen D Oliveira
- Emanueli schulzelopes

---

## Visão geral

Clean Tech é uma plataforma de e‑commerce desenvolvida com arquitetura em camadas, voltada à comercialização de produtos de higiene pessoal e limpeza. O sistema permite o gerenciamento de clientes, categorias, produtos e pedidos, com integração à API ViaCEP para consulta de endereços e envio automático de e‑mails de notificação.

O projeto foi construído como parte da formação técnica no programa Serratec, utilizando principalmente:
- Spring Boot
- Java
- PostgreSQL
- Swagger (OpenAPI)

---

## O que o Clean Tech representa

- Clareza: transmite ideias de produtos de limpeza com a melhor tecnologia.
- Profissionalismo: traz soluções reais e práticas.
- Versatilidade: você pode adquirir nossos produtos na loja física ou através de API.

---

## Arquitetura

O sistema foi desenvolvido em camadas (ex.: apresentação, serviço, domínio, repositório), promovendo separação de responsabilidades e maior facilidade de manutenção e testes.

---

## Funcionalidades principais

- Gestão de clientes (cadastro, atualização, consulta)
- Gestão de categorias e produtos
- Registro e acompanhamento de pedidos
- Integração com API ViaCEP para preenchimento automático de endereços
- Envio automático de e‑mails de notificação (confirmação de pedido, status, etc.)
- Documentação de API via Swagger / OpenAPI

---

## Requisitos

- Java 17+ (ou versão especificada no pom/gradle)
- Maven ou Gradle (conforme o projeto)
- PostgreSQL (ou container Docker)
- Rede com acesso à API ViaCEP para consultas
- Conta SMTP ou serviço de e‑mail configurado para envio de notificações

---

## Variáveis de ambiente / Configurações comuns

As propriedades típicas necessárias (exemplos para application.yml / application.properties):

- Configuração do banco de dados
  - SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/clean_tech_db
  - SPRING_DATASOURCE_USERNAME=seu_usuario
  - SPRING_DATASOURCE_PASSWORD=sua_senha

- Configuração de e‑mail (exemplo SMTP):
  - SPRING_MAIL_HOST=smtp.exemplo.com
  - SPRING_MAIL_PORT=587
  - SPRING_MAIL_USERNAME=seu_email@exemplo.com
  - SPRING_MAIL_PASSWORD=sua_senha
  - SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
  - SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true

- Perfil de execução:
  - SPRING_PROFILES_ACTIVE=develop

Obs.: Ajuste nomes e chaves conforme o arquivo de configuração do projeto (application.yml/properties).

---

## Executando localmente (modo desenvolvimento)

1. Garanta que o PostgreSQL esteja rodando e o banco criado.
2. Configure as variáveis de ambiente ou edite application.properties/yml com as credenciais.
3. Build e execução com Maven:

```bash
# compilar
mvn clean package

# executar (modo Spring Boot)
mvn spring-boot:run
```

Ou com o artefato gerado:

```bash
java -jar target/clean-tech-<versao>.jar --spring.profiles.active=develop
```

(Substitua `<versao>` pela versão gerada pelo build.)

---

## Swagger / Documentação da API

Após iniciar a aplicação, a documentação da API estará disponível em (endereço padrão — confirme conforme configuração do projeto):

- http://localhost:8080/swagger-ui.html
ou
- http://localhost:8080/swagger-ui/index.html

Use a documentação para explorar endpoints de clientes, categorias, produtos e pedidos.

---

## Integrações

- ViaCEP: utilizada para consulta de CEP e preenchimento automático dos dados de endereço.
  - Chamada direta para a API pública ViaCEP (sem chave).
- Email: envio de notificações via SMTP configurado nas propriedades do Spring.

---

## Boas práticas de desenvolvimento (branch develop)

- Sempre crie branches de feature a partir de develop: feature/nome-da-feature
- Faça commits pequenos e com mensagens claras (tipo: feat, fix, chore, refactor)
- Antes de abrir PR, atualize sua branch com os últimos commits de develop e rode os testes.
- PRs devem conter descrição, screenshots (se aplicável) e checklist de validação.

---

## Testes

- Execute os testes unitários/integrados com:
```bash
mvn test
```
- Configure bases de dados de teste separadas ou use profiles específicos para testes.

---

## Contribuição

1. Fork -> clone
2. Crie uma branch a partir de develop
3. Faça commits atômicos e claros
4. Abra um Pull Request para a branch develop
5. Aguarde revisão de código e CI

---

## Contato

Grupo 3 — Clean Tech  
Integrantes listados no início do documento.

---

Licença: ver arquivo LICENSE no repositório (se aplicável).

Observação final: este README foi preparado especificamente para orientar o desenvolvimento na branch "develop". Ajuste instruções e caminhos conforme a configuração real do repositório (build tool, nomes de artefatos, endpoints).
