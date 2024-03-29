package com.example.ec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec.domain.TourRating;
import com.example.ec.exception.NoSuchElementException;
import com.example.ec.repo.TourRatingRepository;

@Service
public class TourRatingService {
	private TourRatingRepository tourRatingRepository;

	@Autowired
	public TourRatingService(TourRatingRepository tourRatingRepository) {
		this.tourRatingRepository = tourRatingRepository;
	}

	/**
     * Verify and return the TourRating for a particular tourId and Customer
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    public TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                + tourId + " for customer" + customerId));
    }

}
