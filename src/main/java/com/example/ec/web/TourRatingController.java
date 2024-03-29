package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.dto.RatingDto;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourRatingService;
import com.example.ec.service.TourService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
	 * @param tourId    tour identifier
	 * @param ratingDto rating data transfer object
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createTourRating(@PathVariable(value = "tourId") int tourId,
			@RequestBody @Validated RatingDto ratingDto) {
		Tour tour = tourService.verifyTour(tourId);
		tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()),
				ratingDto.getScore(), ratingDto.getComment()));
	}

	/**
     * Lookup a page of Ratings for a tour.
     *
     * @param tourId Tour Identifier
     * @param pageable paging details
     * @return Requested page of Tour Ratings as RatingDto's
     */
    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId,
                                            Pageable pageable){
    	tourService.verifyTour(tourId);
        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId, pageable);
        return new PageImpl<>(
                ratings.get().map(RatingDto::new).collect(Collectors.toList()),
                pageable,
                ratings.getTotalElements()
        );
    }

	/**
	 * Calculate the average Score of a Tour.
	 *
	 * @param tourId tour identifier
	 * @return Tuple of "average" and the average value.
	 */
	@GetMapping(path = "/average")
	public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
		tourService.verifyTour(tourId);
		return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream().mapToInt(TourRating::getScore)
				.average().orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
	}

	/**
	 * Update score and comment of a Tour Rating
	 *
	 * @param tourId    tour identifier
	 * @param ratingDto rating Data Transfer Object
	 * @return The modified Rating DTO.
	 */
	@PutMapping
	public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId,
			@RequestBody @Validated RatingDto ratingDto) {
		TourRating rating = tourRatingService.verifyTourRating(tourId, ratingDto.getCustomerId());
		rating.setScore(ratingDto.getScore());
		rating.setComment(ratingDto.getComment());
		return new RatingDto(tourRatingRepository.save(rating));
	}

	/**
	 * Update score or comment of a Tour Rating
	 *
	 * @param tourId    tour identifier
	 * @param ratingDto rating Data Transfer Object
	 * @return The modified Rating DTO.
	 */
	@PatchMapping
	public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId,
			@RequestBody @Validated RatingDto ratingDto) {
		TourRating rating = tourRatingService.verifyTourRating(tourId, ratingDto.getCustomerId());
		if (ratingDto.getScore() != null) {
			rating.setScore(ratingDto.getScore());
		}
		if (ratingDto.getComment() != null) {
			rating.setComment(ratingDto.getComment());
		}
		return new RatingDto(tourRatingRepository.save(rating));
	}

	/**
	 * Delete a Rating of a tour made by a customer
	 *
	 * @param tourId     tour identifier
	 * @param customerId customer identifier
	 */
	@DeleteMapping(path = "/{customerId}")
	public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
		TourRating rating = tourRatingService.verifyTourRating(tourId, customerId);
		tourRatingRepository.delete(rating);
	}

	/**
	 * Exception handler if NoSuchElementException is thrown in this Controller
	 *
	 * @param ex exception
	 * @return Error message String.
	 *//*
		 * @ResponseStatus(HttpStatus.NOT_FOUND)
		 * 
		 * @ExceptionHandler(NoSuchElementException.class) public String
		 * return400(NoSuchElementException ex) { return ex.getMessage();
		 * 
		 * }
		 */
}