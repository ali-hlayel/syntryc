# Spring Boot Graphql
This is the challenge project by Ali Hlayel, implemented based on the defined requirements. The project consists of one microservice, developed using IntelliJ IDE, and built on the Spring Boot framework, with H2 for the database and GraphQL for API queries. Additionally, I utilized Lombok to reduce boilerplate code and Elasticsearch for efficient searching.

The primary goal of integrating Elasticsearch is to enhance the search functionality for all Sellers based on specified filters. This approach enables faster search responses by leveraging Elasticsearch's ability to search through an index rather than directly through text.

The development stack includes:
- IntelliJ IDE
- Spring Boot
- H2 (for the database)
- GraphQL
- Lombok (for reducing boilerplate code)
- Elasticsearch (for efficient searching)

For a more comprehensive understanding, additional details about the specific requirements outlined in the challenge would be beneficial.

## How to Run the Project

1. Start by running the following command to launch Elasticsearch using Docker Compose:

   ```bash
   docker-compose up

This will ensure that Elasticsearch is up and running on http://localhost:9200.
After Elasticsearch is successfully launched, run the project using your preferred method.
Once the project is running, navigate to the GraphQL endpoint at http://localhost:8080/graphql.
once the App started it will add 1000 Seller and Seller dumy data to the DB 
That's it! You can now interact with the GraphQL API to perform queries and explore the functionality of the project.

