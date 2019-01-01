## trabalho-final-javaee
Trabalho final da materia javaee.

### 1. Tecnologias Utilizadas
- Spring
- MongoDB
- RabbitMQ
- Docker

### 2. Tutorial
  1. Executar o projeto no Eclicpse como um projeto Maven;
  2. Configurar o arquivo EmailSender.java com email e senha do email de origem. Atualmente o arquivo está usando constantes de exemplo;
  3. Executar o MongoDB atraves do Docker (Rodar a linha de comando: docker run -d -p 27017:27017 -v ~/docker_data/mongodb:/data/db mong);
  4. Executar o RabbitMQ atraves do Docker (Rodar a linha de comando: docker run -d --hostname rabbitmq --name rabbitmq-management -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management);
  
  **Obs.:** Os Endereços do Docker são: MongoDB: mongodb://localhost:27017 ; RabbitMQ: http://localhost:15672/#/queues

### 4. Métodos API

  1. Investidores  
    - Listar: 
        GET http://localhost:8080/api/v1/investors
    - Criar: 
        POST http://localhost:8080/api/v1/investors
    
  2. Empresas  
    - Listar: 
        GET http://localhost:8080/api/v1/companies
    - Criar: 
        POST http://localhost:8080/api/v1/companies
    
  3. Ações
    1. Investidor
      - Listar Compras: 
          GET http://localhost:8080/api/v1/stocks/buy/
          
      - Comprar:
          POST http://localhost:8080/api/v1/stocks/buy/
          POST http://localhost:8080/api/v1/stocks/buy/{transactionId}/
          POST http://localhost:8080/api/v1/stocks/buy/{transactionId}/{investorId}
        
       - Listar Vendas: 
          GET http://localhost:8080/api/v1/stocks/sell/
          
       - Vender:
          POST http://localhost:8080/api/v1/stocks/sell/
          POST http://localhost:8080/api/v1/stocks/sell/{transactionId}/
          POST http://localhost:8080/api/v1/stocks/sell/{transactionId}/{investorId}
          
    2. Empresa
        - Listar:
            GET http://localhost:8080/api/v1/stocks/emit/{companyId}
            
        - Emitir:
            POST http://localhost:8080/api/v1/stocks/emit/{companyId}
  
