Backend created for Explore California tour operator.
Implemented functionality for Tours,TourPackage and Tour ratings.
*Model* :
1. TourPackage
Field : String code(PK); String name;
2. Tour
Field : int id(PK); String title; String description; String blurb; int price; String duration; String bullets; String keywords; TourPackage tourPackage; Difficulty difficulty; Region region;
Association : ManytoOne with Tour Package
3. TourRating
Field : TourRatingPk tourRatingPk (PK-embedded); int score; String comment;
4. TourRatingPk
Field : Tour tour; int customerId;
6. Difficulty(enum)
Easy; Medium; Difficult; Varies;
7. Region
Central_Coast; Southern_California; Northern_California; Varies;
