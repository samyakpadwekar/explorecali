package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.dto.RatingDto;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourRatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Tour Rating Controller
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@Tag(name = "Tour Rating", description = "The Tour Rating API")
public class TourRatingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingController.class);
	private TourRatingRepository tourRatingRepository;
	private TourRepository tourRepository;
	private TourRatingService tourRatingService;

	@Autowired
	public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository,
			TourRatingService tourRatingService) {
		super();
		this.tourRatingRepository = tourRatingRepository;
		this.tourRepository = tourRepository;
		this.tourRatingService = tourRatingService;
	}

	protected TourRatingController() {

	}

	/**
	 * Create a Tour Rating.
	 *
	 * @param tourId    tour identifier
	 * @param ratingDto rating data transfer object
	 */
	@PostMapping
	@PreAuthorize("hasRole('ROLE_CSR')")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create tour rating")
	public void createTourRating(@PathVariable(value = "tourId") int tourId,
			@RequestBody @Validated RatingDto ratingDto) {
		LOGGER.info("POST /tours/{}/ratings", tourId);
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
	}

	/**
     * Create Several Tour Ratings for one tour, score and several customers.
     *
     * @param tourId
     * @param score
     * @param customers
     */
    @PostMapping("/{score}")
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Give same rating to many tours")    
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @PathVariable(value = "score") int score,
                                      @RequestParam("customers") Integer customers[]) {
        LOGGER.info("POST /tours/{}/ratings/{}", tourId, score);
        tourRatingService.rateMany(tourId, score, customers);
    }
	
	/**
	 * Lookup a page of Ratings for a tour.
	 *
	 * @param tourId   Tour Identifier
	 * @param pageable paging details
	 * @return Requested page of Tour Ratings as RatingDto's
	 */
	@GetMapping
	@Operation(summary = "Gets all ratings for a tour")
	public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
		verifyTour(tourId);
		Page<TourRating> ratings = tourRatingRepository.findByTourId(tourId, pageable);
		return new PageImpl<>(ratings.get().map(RatingDto::new).collect(Collectors.toList()), pageable,
				ratings.getTotalElements());
	}

	/**
	 * Calculate the average Score of a Tour.
	 *
	 * @param tourId tour identifier
	 * @return Tuple of "average" and the average value.
	 */
	@GetMapping(path = "/average")
	@Operation(summary = "Get average rating for a tour")
	public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
		verifyTour(tourId);
		return Map.of("average", tourRatingService.getAverageScore(tourId));
	}

	/**
	 * Update score and comment of a Tour Rating
	 *
	 * @param tourId    tour identifier
	 * @param ratingDto rating Data Transfer Object
	 * @return The modified Rating DTO.
	 */
	@PutMapping
	@PreAuthorize("hasRole('ROLE_CSR')")
	@Operation(summary = "Modify all attributes of a rating")
	public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId,
			@RequestBody @Validated RatingDto ratingDto) {
		LOGGER.info("PUT /tours/{}/ratings", tourId);
        return new RatingDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),
                 ratingDto.getScore(), ratingDto.getComment()));
		
		/*
		 * TourRating rating = tourRatingService.verifyTourRating(tourId,
		 * ratingDto.getCustomerId()); rating.setScore(ratingDto.getScore());
		 * rating.setComment(ratingDto.getComment()); return new
		 * RatingDto(tourRatingRepository.save(rating));
		 */
	}

	/**
	 * Update score or comment of a Tour Rating
	 *
	 * @param tourId    tour identifier
	 * @param ratingDto rating Data Transfer Object
	 * @return The modified Rating DTO.
	 */
	@PatchMapping
	@PreAuthorize("hasRole('ROLE_CSR')")
	@Operation(summary = "Modify some attributes of a rating")
	public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId,
			@RequestBody @Validated RatingDto ratingDto) {
		LOGGER.info("PATCH /tours/{}/ratings", tourId);
		return new RatingDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(),
                ratingDto.getScore(), ratingDto.getComment()));
	}

	/**
	 * Delete a Rating of a tour made by a customer
	 *
	 * @param tourId     tour identifier
	 * @param customerId customer identifier
	 */
	@DeleteMapping(path = "/{customerId}")
	@PreAuthorize("hasRole('ROLE_CSR')")
	@Operation(summary = "Delete a customer's rating of a tour")
	public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
		LOGGER.info("DELETE /tours/{}/ratings/{}", tourId, customerId);
        tourRatingService.delete(tourId, customerId);
	}

	/**
	 * Verify and return the Tour given a tourId.
	 *
	 * @param tourId tour identifier
	 * @return the found Tour
	 * @throws NoSuchElementException if no Tour found.
	 */
	public Tour verifyTour(int tourId) throws NoSuchElementException {
		return tourRepository.findById(tourId)
				.orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
	}

}
