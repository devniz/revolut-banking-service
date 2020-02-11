![revolut image](https://www.cityam.com/wp-content/uploads/2019/11/revolut-card-5c7ed94007b1a-960x641-960x641.jpg)

# Revolut Backend Test

Design and implement a RESTful API (including data model and the backing implementation) for
money transfers between accounts.
Explicit requirements:
1. You can use Java or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 and keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require a
pre-installed container/routesImpl).
7. Demonstrate with tests that the API works as expected.
Implicit requirements:
1. The code produced by you is expected to be of high quality.
2. There are no detailed requirements, use common sense.
Please put your work on github or bitbucket.

## Analyse

The problem I see in this assessment is how can we handle concurrent transactions safely? For a banking service, it is not acceptable to have unsafe and unpredictable transactions across multiple distributed systems.

## Solution & Approach

I first did a quick search on how a basic transfer money system operates. I took a TDD approach to write a Unit Test and assert that my current minimal implementation of a money transfer service is showing some random assertions and unpredictable results:
![nonsense-assertions](https://i.ibb.co/HGQMYvh/Screenshot-2020-02-10-at-22-37-13.png)
> Sometimes it was even green...

From there, I was looking back at optimistic/pessimistic locking, Java synchronized, CountDownLatch classes... and so on.
  
## Implementation phase

### Dependencies:
- Spark Framework: Lightweight micro framework for web services.
- JDBI 3: Better implementation of JDBC. It provides full support for JDBC transaction.
- JUnit: To write all my Unit Tests.
- RestAssured: Testing REST services.

### Build the project

- Run `mvn clean package` to build the JAR file.
- Then `java -jar ./target/bank-service-1-0.jar-with-dependencies.jar` to spin up the embedded Jetty server

### How to test?

- In test  > postman-collection, You can find banking-service.json file with all necessary endpoint calls.

The REST API is accepting those calls:

- /POST account: Create a new Account given a name in the body.
- /GET account: Return all created account stored.
- /GET account/{id}: Return account by ID.
- /POST transfer: Create a new transaction between 2 accounts.


