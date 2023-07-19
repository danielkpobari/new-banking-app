<!-- ABOUT THE PROJECT -->
## A fake financial institution core app using Java and Spring

This project is a fake financial institution core app that bank employees use to manage customers accounts.

Technologies Used:
* Java 17
* Spring boot
* Spring Security
* Test Containers
* Maven
* JWT
* Liquibase
* MySQL
* Docker
* Postman


### API Documentation

Link: 
[API Doc Link](https://documenter.getpostman.com/view/6887603/2s93Xzw2DS#6a48916f-bc40-4e75-a1c7-c4db435cc6f7)

### Installation

Below is an example of how you can install and set up the app.

NB: Ensure you have Java 17 and Docker installed.

1. Clone the repo
   ```sh
   git clone 
   ```
2. Build the project
   ```sh
   ./mvnw clean install
3. Run the docker-compose.yml file to set up the database
   ```sh
   docker-compose up
   ```
4. Start the app
   ```sh
   ./mvnw spring-boot:run
   ```

##  Pre-populated Data

Admin data
```json
   {
      "first_name": "Peter",
      "last_name": "John",
      "email": "jpeter@yassir.com",
      "password": "123456"
   }
```

Account data
```json
   [
      {
         "first_name": "Test",
         "last_name": "User1",
         "email": "user1@test.com"
      },
      {
         "first_name": "Test",
         "last_name": "User2",
         "email": "user2@test.com"
      }
  ]
```

Wallet data
```json
   [
      {
         "id": 1,
         "balance": 2149.50,
         "currency_code": "EUR",
         "wallet_no": "6163759971",
         "account_id": 1
      },
      {
         "id": 2,
         "balance": 500.00,
         "currency_code": "GBP",
         "wallet_no": "2854004807",
         "account_id": 1
      },
      {
         "id": 3,
         "balance": 3850.50,
         "currency_code": "EUR",
         "wallet_no": "5814533487",
         "account_id": 2
      },
      {
         "id": 4,
         "balance": 557500.00,
         "currency_code": "GBP",
         "wallet_no": "3112805941",
         "account_id": 2
      },
      {
         "id": 5,
         "balance": 99250.00,
         "currency_code": "USD",
         "wallet_no": "7496493042",
         "account_id": 1
      },
      {
         "id": 6,
         "balance": 100750.00,
         "currency_code": "USD",
         "wallet_no": "2499962315",
         "account_id": 1
      }
   ]
```

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.
