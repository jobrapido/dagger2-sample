### dagger-article

The application takes the name of a person as input and returns his probable 'gender' and 'nationality' percentage. 

The REST endpoint is exposed using *Undertow* and to call the external APIs, we use *Java 11*. Also, the test cases are written using *JUnit 5*.



### Project Dependencies

Java 11



### Running the Application

```
./gradlew run
```

**Sample Request:**

```
GET http://localhost:8080/person?name=peter
```

**Sample Response:**

```
{"name":"peter","gender":{"gender":"male","probability":0.99},"nationality":{"country_id":"SK","probability":0.1245678651196634}}
```

​		

### Troubleshooting/Known Problems

We are using external APIs ([https://nationalize.io](https://nationalize.io/) and https://genderize.io/), which might sometimes not be available.

