Overview

This Spring Boot application demonstrates the application of ACID (Atomicity, Consistency, Isolation, Durability) principles in the context of database transactions. ACID is a set of properties that guarantee reliable processing of database transactions, ensuring data integrity and reliability.

ACID Principles

1. Atomicity

Atomicity ensures that a transaction is treated as a single, indivisible unit of work. In case of any failure during the transaction, the entire operation is rolled back to its original state, ensuring that the database remains consistent.

2. Consistency

Consistency ensures that a transaction brings the database from one consistent state to another. It enforces the integrity constraints and business rules, preserving the overall correctness of the data.

3. Isolation

Isolation ensures that multiple transactions can run concurrently without interfering with each other. Each transaction is isolated from others until it is committed, preventing inconsistencies that could arise from simultaneous execution of transactions.

4. Durability

Durability guarantees that once a transaction is committed, its changes are permanent and survive any subsequent system failures. This is typically achieved through persistent storage mechanisms.

Dependencies

• Spring Boot Starter Data JPA:

• Oracle JDBC Driver:

• Project Lombok (Optional):
# jdbc-acid-example
