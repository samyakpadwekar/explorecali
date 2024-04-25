**Backend for Explore California tour operator.** \
Tour,Tour Package and Tour Rating functionality. \
Key Things Implemented: 
1. **Spring Data REST and Spring Data JPA** for Get,Post,Put & Delete API.
2. **Spring Security** using **JWT**.
3. **JUnit5** for Unit, Integration and API Test cases.
4. **Github Actions** for CI/CD.
5. **MySQL (Amazon RDS)** as Relational DB.
6. **Elastic Beanstalk wih EC2** for deployment.
7. **PagingAndSorting** for Tour and Tour Rating. 
8. **Controlling API Exposure** - use of @RepositoryRestResource,@RestResource.
9. **slf4j Logger** for Logging
11. **HAL Browser** and **Postman** for API testing.
12. **Open API** for API documentation. 
13. **CommandLineRunner** for loading TourPackage and Tour data and testing through command line.

##.Project has been implemented using **MongoDB** for non-relational data [branch : exporeCali_MongoDB]
Requirement : Tour data can have varying number of fields with tourPackage and title being only required field. \
Refer link : <a href="https://stackoverflow.com/questions/74734106/how-to-use-embedded-mongodb-with-springboot-v3-0-0" target="_blank">how-to-use-embedded-mongodb-with-springboot-v3-0-0</a>

UI link : <a href="https://explorecalifornia.org/" target="_blank">explorecalifornia.org</a>
