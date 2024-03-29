package com.example.ec.web;

import com.example.ec.domain.TourRating;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourRatingService;
import com.example.ec.service.TourService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
	private TourRatingRepository tourRatingRepository;
	private TourRepository tourRepository;
	private TourService tourService;
	private TourRatingService tourRatingService;

	@Autowired
	public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository,
			TourService tourService, TourRatingService tourRatingService) {
		super();
		this.tourRatingRepository = tourRatingRepository;
		this.tourRepository = tourRepository;
		this.tourService = tourService;
		this.tourRatingService = tourRatingService;
	}

    protected TourRatingController() {

    }

    /**
     * Create a Tour Rating.
     *
     * @param tourId tour identifier
     * @param tourRating rating data transfer object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId,
                                 @RequestBody @Validated TourRating tourRating) {
    	tourService.verifyTour(tourId);
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(),
                tourRating.getScore(), tourRating.getComment()));
    }

    /**
     * Lookup a page of Ratings for a tour.
     *
     * @param tourId Tour Identifier
     * @param pageable paging details
     * @return Requested page of Tour Ratings as RatingDto's
     */
    @GetMapping
    public Page<TourRating> getRatings(@PathVariable(value = "tourId") String tourId,
                                            Pageable pageable){
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    /**
     * Calculate the average Score of a Tour.
     *
     * @param tourId tour identifier
     * @return Tuple of "average" and the average value.
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
    	tourService.verifyTour(tourId);
        return Map.of("average",tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() ->
                new NoSuchElementException("Tour has no Ratings")));
    }

    /**
     * Update score and comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param tourRating rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId,
                                    @RequestBody @Validated TourRating tourRating) {
        TourRating rating = tourRatingService.verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }
    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId tour identifier
     * @param tourRating rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
                                      @RequestBody @Validated TourRating tourRating) {
        TourRating rating = tourRatingService.verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }
        if (tourRating.getComment() != null) {
            rating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(rating);
    }

    /**
     * Delete a Rating of a tour made by a customer
     *
     * @param tourId tour identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId,
                       @PathVariable(value = "customerId") int customerId) {
        TourRating rating = tourRatingService.verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

}
