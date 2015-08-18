# Candy

Candy is a Dynamic Entity REST API Generator

[![Circle CI](https://circleci.com/gh/joaopedro/Candy/tree/develop.svg?style=svg&circle-token=b9dbd249084c6d57b84ef38429de738b7c5ffed6)](https://circleci.com/gh/joaopedro/Candy/tree/develop)

The idea is that the generated endpoints will be proper REST endpoints, with a WAD for discovery and documentation and with or without authentication (basic or ldap).

The UI will be web based and it will be used to define the entities and specify security methods.

The first version will not be able to create relationships between entities but this will be done in versions further down the road...

[![Join the chat at https://gitter.im/joaopedro/Candy](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/joaopedro/Candy?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
## Why do this?

I recently came to work for a company that used a opensource project called "SugarCRM" has a way to very quickly and with low costs setup a dynamic entity management backoffice with a rest and soap api.

SugarCRM is a great product and very dynamic CRM... but it is, in its core, a CRM. The ideia with Candy is to make a dynamic entity manager with a rest api that everyone could use to setup a serverside enpoint in minutes for mobile and front-end development.  

## The Code

We are using what we think is the best and most current choices for the problems we face.

The Backend is a spring-boot rest application that provides us with a very simple way to bootstrap and setup the solution. With facilities like the RepositoryRestResource we can easily expose REST endpoints for our model, liquibase gives us a managed database and cross database compatibility.

On the front-end we are using angularJS to consume the REST endpoints we are exposing on spring-boot.

## Setting Up

Just install java 8 and maven. Download or clone the project and in the root directory run:

```mvn clean package```

Than go to the target directory run:

```java -jar candy-0.0.1-SNAPSHOT.jar```

And access the project from http://localhost:8080

There are two REST api's available in the project. One accessible under http://localhost:8080/manage with a alps descriptor [http://localhost:8080/manage/alps](http://localhost:8080/manage/alps). And another wich is dynamic, depending on the entities you create, there is located under http://localhost:8080/api.

To try the REST api you can use curl and do something like this to create the dynamic entity :

```
curl -X POST http://localhost:8080/manage/entity --header "Content-Type:application/json" -d '{"friendlyName": "sample", "physicalName": "cndy_sample",
    "fields": [{"friendlyName": "id", "physicalName": "cndy_fld_id", "type": "NUMBER", "fieldId":true, "fieldIdentity":true},
    {"friendlyName": "field1", "physicalName": "cndy_fld_field1", "type": "NUMBER"},
    {"friendlyName": "field2", "physicalName": "cndy_fld_field2", "type": "STRING"},
    {"friendlyName": "field3", "physicalName": "cndy_fld_field3", "type": "NUMBER"},
    {"friendlyName": "field4", "physicalName": "cndy_fld_field4", "type": "STRING"},
    {"friendlyName": "field5", "physicalName": "cndy_fld_field5", "type": "NUMBER"},
    {"friendlyName": "field6", "physicalName": "cndy_fld_field6", "type": "STRING"}
    ]
} '
```
Than create a instance of that entity:

```curl --user admin:admin -X POST http://localhost:8080/api/sample --header "Content-Type:application/json" -d '{"field2": "value1", "field4": "value2"}'```

And that's it. You can see a list of instances of the "sample" entity with ```curl http://localhost:8080/api/sample``` or access a instance directly via its id with ```curl http://localhost:8080/api/sample/0```. 

##Contributing

[CONTRIBUTING](https://en.wikipedia.org/wiki/Open-source_movement).

##License
[MIT License](http://en.wikipedia.org/wiki/MIT_License)

## The Theme Song

<a href="/CandyIggyPop.ogg?raw=true" target="_blank">Get the theme song...</a>

<a href="https://www.youtube.com/watch?v=6bLOjmY--TA&t=2m18s" target="_blank">
Or the theme song video<br><img src="http://img.youtube.com/vi/6bLOjmY--TA/0.jpg" 
alt="The theme song video" border="10" /></a>
