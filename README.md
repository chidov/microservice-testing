# Microservice-Testing [![Build Status](https://travis-ci.com/Dovchiproeng/microservice-testing.svg?branch=master)](https://travis-ci.com/Dovchiproeng/microservice-testing) [![codecov](https://codecov.io/gh/Dovchiproeng/microservice-testing/branch/master/graph/badge.svg)](https://codecov.io/gh/Dovchiproeng/microservice-testing) [![Join the chat at https://gitter.im/microservice-testing/Lobby](https://badges.gitter.im/microservice-testing/Lobby.svg)](https://gitter.im/microservice-testing/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Dovchiproeng_microservice-testing&metric=alert_status)](https://sonarcloud.io/dashboard?id=Dovchiproeng_microservice-testing)
This project is a POC of Spring Cloud Contract which enables Consumer Driven Contract (CDC) development of JVM-based applications. It moves TDD to the level of software architecture.

## Consumer Driven Contract?
There're 2 ways to test microservices 
* Deploy all microservices and perform end-to-end tests.
* Mock other microservices in unit/integration tests.

The above methods do not really work well for us during testing microservices, This is how I think:

### Deploy all microservices and perform end-to-end tests
* To test one microservice, we have to deploy other microservices, a couple of databases, etc.
* It takes such a long time to run, and some functionality might be limited for us to test in real runtime system. ex. account doesn't not have that specific feature, will spend more time to create that account.
* Hard to debug the code

Then we come up with the idea to mock up all the other service so tests will run fast and you will be able to stub the data you want :)

### However Mock other microservices in unit/integration tests
* The stub create for microservices doesn't synchronize with the reality (production code). If the service code changes then you will need to your stub in the mock service which will cause high chance of inconsistent. ex. if POJO class change its field name, you will get an inconsistent mock if you forget to update your mock.
* What you test with the stub might not be what is in the production.

### So, Spring Clould Contract Verifier with Stub Runner will solve these issues
The idea behind CDC (Consumer Driven Contracts) is to share the contract between both sides of the communication.
* To provide a way to publish changes in contracts that are immediately visible on both sides of the communication. ex. create stub contract in server side, which use to test both server and client code to secure the synchronization. 
* To ensure that WireMock/Messaging stubs (used when developing the client) do exactly what the actual server-side implementation does.
* To promote ATDD method and Microservice architectural style.
* To generate boilerplate test code to be used on the server side.

## How it works?
### Defining the contract
> [Spring Cloud Contract](https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html) supports out of the box 2 types of DSL. One written in Groovy and one written in YAML.  

By default, Spring Cloud Contract Verifier is looking for stubs in the src/test/resources/contracts directory.
so let's define the contract in `src/test/resources/contracts` which describe about food controller contract under `food/shoudGetAllFoods.groovy`
```groovy
package food

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/foods'
    }
    response {
        status 200
        body([[id: 1L, name: "Rice", description: "White Rice"], [id: 2L, name: "Fried Rice", description: "Premium Rice"]])
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
}
```
Base on the contract, Spring Cloud Contract Gradle and Maven plugins help you with that by generating a jar with stubs and contract definitions with a stubs classifier(`food-server-0.0.1-SNAPSHOT-stubs.jar`). Just upload it to some central repository where others can reuse it for their integration tests.

#### Advance contract DSL
Contract DSL also supports matcher and regex in order to match any request and response base on requirement:
* [dynamic properties for consumer and producer](https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html#_dynamic_properties_inside_the_body), for example client side will always want to get response id = 1 but server testing can expect anynumber, then set can set resposne as `id: $(consumer(1),producer(anyNumber()))`
* [regex and predefined-regex support](https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html#_regular_expressions)
* [matcher support](https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html#contract-matchers)

[more detail about contract DSL](https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html#_contract_dsl)

### Verified with Server Side Concrete Implementation (Producer Perspective Testing)
Since you are developing your stub, you need to be sure that it actually resembles your concrete implementation. You cannot have a situation where your stub acts in one way and your application behaves in a different way, especially in production.

To ensure that your application behaves the way you define in your stub, tests are auto-generated from the stub contract you provide.

The autogenerated test looks like this:
```java
//FoodTest is last contract folder name + 'Test' suffix
public class FoodTest extends FoodBase {

	@Test // method name is base on contract file name if more than one contract it will be {fileName}_{sequence#} or it will be request name if it is define in the specific contract
	public void validate_shoudGetAllFoods() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();

		// when:
			ResponseOptions response = given().spec(request)
					.get("/foods");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(2L);
			assertThatJson(parsedJson).array().contains("['description']").isEqualTo("White Rice");
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Fried Rice");
			assertThatJson(parsedJson).array().contains("['description']").isEqualTo("Premium Rice");
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(1L);
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Rice");
	}
}
```
The auto-generated tests will run through RestAssured which is setup in FoodBase, the purpose is to check whether stubbing contract is matched with the real implementation.

Note: FoodBase must be define as autogenerated test will extend it and based on contract path. for example if the contract is in `src/test/resources/contracts/test/food` then base class will be TestFoodBase (the naming convention is use last 2 directory start from base + 'Base' suffix). In the demo we put contract in `src/test/resources/contracts/food` so it will be FoodBase.

### Client Side test stub using Spring Cloud Contract Stub Runner
Spring Cloud Contract generates stubs, which you can use during client-side testing. You get a running WireMock instance/Messaging route that simulates the service. In here the StubRunner will base on the group id to retrieve the stub artifact (`food-server-0.0.1-SNAPSHOT-stubs.jar`) from your local maven repo since we define `stubsMode = StubRunnerProperties.StubsMode.LOCAL`
```java
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = {"com.egen:food-server:+:stubs:8080"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class FoodClientTest {

    @Autowired
    private FoodClient foodClient;
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void whenGetFoods_thenReturnFoods(){
        List<Food> foodList = foodClient.getFoods();
        assertThat(foodList).isNotEmpty();
        assertThat(foodList).extracting("id", "name", "description").containsExactly(
                new Tuple(1L, "Rice", "White Rice"),
                new Tuple(2L, "Fried Rice", "Premium Rice")
        );
    }
}
```
### Summary Spring Cloud Contract Flow
```bash
> Task :food-server:copyContracts - Copies contracts to the output folder
> Task :food-server:generateClientStubs - Generate client stubs from the contracts (stubs/mappings/)
> Task :food-server:verifierStubsJar - Creates the stubs JAR task
> Task :food-server:generateContractTests - Generate server tests from the contracts
> Task :food-server:test - Run all test case to verified server code and stubs
```
#### publish stub artifact to local maven repo
```
./gradlew publishStubsPublicationToMavenLocal
```
### Technology Stacks
* WireMock : Spring Cloud Contract Stub Runner use **WireMock** to run the Mock Server which inject the stub(`stubs/mappings/*.json`) from the stub artifact into it.
* RestAssured: Spring Cloud Contract Verifier use **RestAssured** as MockMvc test engine for auto-generated boilerplate test code.

## Reference
https://cloud.spring.io/spring-cloud-contract/  
https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/1.2.1.RELEASE/single/spring-cloud-contract.html#_spring_cloud_contract  
https://cloud.spring.io/spring-cloud-contract/multi/multi__spring_cloud_contract_verifier_setup.html
