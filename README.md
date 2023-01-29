# Manage Recipes - Reciepe API

## Build

Use the below command  to build the API.

```
mvn clean install
```

## Usage

The project used postgress DB as the datasource. 
docker-compose file is provided to initialise the DB. 

Run below command to spin up the DB:

```
docker-compose up
```

Run below command to stop the DB:

```
docker-compose down
```

## Documentation

* The design records are documented as ADR's under folder /doc. 
* Swagger spec is placed under folder /resources/swagger. 


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

