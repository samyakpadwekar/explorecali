package com.example.ec.repo;

import com.example.ec.domain.TourRating;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Tour Rating Repository Interface
 */
//to prevent this repo from being exposed to spring data rest,only expose by controller
@RepositoryRestResource(exported = false)
@Tag(name = "Tour Rating Repo", description = "Tour Rating API")
public interface TourRatingRepository extends CrudRepository<TourRating, Integer>,JpaRepository<TourRating, Integer> {

    /**
     * Lookup all the TourRatings for a tour.
     *
     * @param tourId is the tour Identifier
     * @return a List of any found TourRatings
     */
    List<TourRating> findByTourId(Integer tourId);

    /**
     * Lookup a TourRating by the TourId and Customer Id
     * @param tourId tour identifier
     * @param customerId customer identifier
     * @return Optional of found TourRatings.
     */
    Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);

    /**
     * Fetch a Page of TourRatings
     *
     * @param tourId the tour identifier
     * @param pageable info to determine page
     * @return Page of Tour Ratings
     */
    Page<TourRating> findByTourId(Integer tourId, Pageable pageable);
    
    
}