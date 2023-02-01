<!-- ABOUT THE PROJECT -->
## About The Project

Recipes API is an application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:
* Whether or not the dish is vegetarian 
* The number of servings 
* Specific ingredients (either include or exclude)
* Text search within the instructions.


For example, the API should be able to handle the following search requests:
   * All vegetarian recipes
   * Recipes that can serve 4 persons and have “potatoes” as an ingredient
   * Recipes without “salmon” as an ingredient that has “oven” in the instructions.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [Spring-Boot](https://spring.io/projects/spring-boot)
* [Postgres](https://www.postgresql.org/)
* [Maven](https://maven.apache.org/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites and Installation

* mvn - Build the project
```sh
  mvn clean install
 ```

* docker - run docker-compose to spin postgress DB instance.
```sh
  docker-compose up
  ```

* spring-boot - run the app
```sh
spring-boot:run
```

* Check the health of the app
```sh
http://localhost:8080/actuator/health
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage

1. Update any existing reciepe 

```
curl -X 'PUT' \
'http://localhost:8080/reciepes' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"name": "Fish Curry",
"dishType": "vegetarian",
"ingredient": [
"potato",
"tomato"
],
"servings": 7,
"instructions": "string",
"cookTime": "string",
"created": "2023-01-31T23:33:43.940Z",
"updated": "2023-01-31T23:33:43.940Z"
}'
```

2. Add a new Reciepe

```
curl -X 'POST' \
  'http://localhost:8080/reciepes' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Fish Curry",
  "dishType": "vegetarian",
  "ingredient": [
    "potato",
    "tomato"
  ],
  "servings": 7,
  "instructions": "string",
  "cookTime": "string",
  "created": "2023-01-31T23:35:42.909Z",
  "updated": "2023-01-31T23:35:42.909Z"
}'
```

3. Find Reciepes 

```
curl -X 'GET' \
  'http://localhost:8080/reciepes?dishType=Vegetarian&servings=2&
  ingredient=Pesto&search=oven&name=Curry&start=0&end=3' \
  -H 'accept: application/json'

```

Search Parameters allowed : dish type (Vegetarian, Italian), ingredient, instruction and servings.


4. Delete a reciepe

```
curl -X 'DELETE' \
  'http://localhost:8080/reciepes/curry' \
  -H 'accept: */*'

```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Your Name - gaurvitasta@gmail.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>
