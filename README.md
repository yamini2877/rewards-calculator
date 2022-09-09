# rewards-calculator

- This services is developed using Springboot and used in-memory H2 database. This has data.sql which inserts some records when the application is started. <br><br>
- This service has two entities, one is **USER** which stores userid and userName. Another entity is **Transactions** which stores all the transaction with tractionsId, userId, amount and date of transaction. <br><br>
- This service provides four endpoints, one is main endpoint of this assessment. **/users/{userId}/rewards** calculates the rewards of the user with the last three months of transactions. If user is not found in database, or there are no transactions for this user in last three months, then this endpoint returns **NOT_FOUND** exception. <br><br>
- Results are available in src/main/resources/results folder with postman screenshots of all scenarios. <br><br> 

- The remaining endpoints are one is to retrieve all the transactions of the user, add user to user table, add a transaction to particular user. This service didn't focus on validations and exceptions of these endpoints.