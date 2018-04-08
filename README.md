# Microservice-Testing
This project is a POC of Spring Cloud Contract which enables Consumer Driven Contract (CDC) development of JVM-based applications. It moves TDD to the level of software architecture.

## Consumer Driven Contract?
There're 2 ways to test microservices 
* Deploy all microservices and perform end-to-end tests.
* Mock other microservices in unit/integration tests.

The above method is not really work well for us during testing microservices, here is how I think:

### Deploy all microservices and perform end-to-end tests
* To test one microservice, we have to deploy other microservices, a couple of databases, etc.
* It takes so long time to run, and some functinality might be limited for us to test in real runtime system. ex. account doesn't not have that specific feature, will spend more time to create that account.
* Hard to debug the code

Then we come up with the idea to mock up all the other service so it will be run fast and you will be able to stub the data you want :)

### However Mock other microservices in unit/integration tests
* The stub create for microservices doesn't synchronize with the reality (production code). If the service code change then you will need to your stub in the mock service which will cause high chance of inconsistent.
* What you test with the stub might not be what in the production.

### So, Spring Clould Contract Verifier with Stub Runner will solve the these issues
* To provide a way to publish changes in contracts that are immediately visible on both sides of the communication. ex. create stub contract in server side, which use to test both server and client code to secure the synchronization. 
* To ensure that WireMock/Messaging stubs (used when developing the client) do exactly what the actual server-side implementation does.
* To promote ATDD method and Microservices architectural style.
* To generate boilerplate test code to be used on the server side.

## How it works?

## Reference
https://cloud.spring.io/spring-cloud-contract/
https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.2.1.RELEASE/single/spring-cloud-contract.html#_spring_cloud_contract
https://cloud.spring.io/spring-cloud-contract/multi/multi__spring_cloud_contract_verifier_setup.html
