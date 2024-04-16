**Backend for Explore California tour operator.** \
Tour,Tour Package and Tour Rating functionality. \
Key Things Implemented: 
1. **Spring Data REST and Spring Data JPA** for Get,Post,Put & Delete API. 
2. **PagingAndSorting** for Tour and Tour Rating. 
3. **Controlling API Exposure** - use of @RepositoryRestResource,@RestResource.
4. **slf4j Logger** for Logging
5. **JUnit and Mockito** for Unit, Integration and API Testing 
6. **HAL Browser** and **Open API Swagger UI** for API documentation. 
7. **H2 embedded DB** as Relational DB
8. **CommandLineRunner** for loading TourPackage and Tour data and testing through command line.

Things to do:
1. Versioning
2. HATEOAS
3. Spring Security
4. Dockerization
5. AWS Deployment

##.Project has been implemented using **MongoDB** for non-relational data [branch : exporeCali_MongoDB]
Requirement : Tour data can have varying number of fields with tourPackage and title being only required field. \
Refer link : <a href="https://stackoverflow.com/questions/74734106/how-to-use-embedded-mongodb-with-springboot-v3-0-0" target="_blank">how-to-use-embedded-mongodb-with-springboot-v3-0-0</a>

UI link : <a href="https://explorecalifornia.org/" target="_blank">explorecalifornia.org</a>
