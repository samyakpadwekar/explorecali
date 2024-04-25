**Backend for Explore California tour operator.** \
Tour,Tour Package and Tour Rating functionality. \
Key Things Implemented: 
1. **Spring Data REST and Spring Data JPA** for Get,Post,Put & Delete API.
2. **Spring Security** using **JWT**.
3. **PagingAndSorting** for Tour and Tour Rating. 
4. **Controlling API Exposure** - use of @RepositoryRestResource,@RestResource.
5. **slf4j Logger** for Logging
6. **JUnit and Mockito** for Unit, Integration and API Test cases. 
7. **HAL Browser** for testing.
8. **Open API** for API documentation. 
9. **H2 embedded DB** as Relational DB.
10. **Github Actions** for CI/CD.
11. **CommandLineRunner** for loading TourPackage and Tour data and testing through command line.

Things to do:
1. Dockerization
2. AWS Deployment

##.Project has been implemented using **MongoDB** for non-relational data [branch : exporeCali_MongoDB]
Requirement : Tour data can have varying number of fields with tourPackage and title being only required field. \
Refer link : <a href="https://stackoverflow.com/questions/74734106/how-to-use-embedded-mongodb-with-springboot-v3-0-0" target="_blank">how-to-use-embedded-mongodb-with-springboot-v3-0-0</a>

UI link : <a href="https://explorecalifornia.org/" target="_blank">explorecalifornia.org</a>
