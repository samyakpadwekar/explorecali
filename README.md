**Backend for Explore California tour operator.** \
Tour,Tour Package and Tour Rating functionality. \
Key Points : 
1. **Get,Post,Put,Delete** functionality for Tours,TourPackage and Tour ratings. 
- Spring Data REST for Tours,TourPackage(no Controller),Spring MVC for TourRating (Controller used). 
3. **PagingAndSorting** for Tour and Tour Rating. 
4. **Controlling API Exposure** - use of @RepositoryRestResource,@RestResource. 
5. **HAL Browser**.
6. **H2 embedded DB** as Relational DB
- **CommandLineRunner** for loading TourPackage and Tour data.
7. **MongoDB** for non-relational data.
- Tour data can have varying number of fields with tourPackage and title being only required field. \

  
**Model** :
1. *TourPackage* \
Field : String code(PK); String name;
2. *Tour* \
Field : int id(PK); String title; String description; String blurb; int price; String length; String bullets; String keywords; TourPackage tourPackage; Difficulty difficulty; Region region;
Association : ManytoOne with Tour Package
3. *TourRating* \
Field : TourRatingPk tourRatingPk (PK-embedded); int score; String comment;
4. *TourRatingPk* \
Field : Tour tour; int customerId;
6. *Difficulty(enum)* \
Easy; Medium; Difficult; Varies;
7. *Region(enum)* \
Central_Coast; Southern_California; Northern_California; Varies;
