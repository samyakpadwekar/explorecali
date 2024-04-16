package com.example.ec.web;

import com.example.ec.dto.RatingDto;
import com.example.ec.exception.NoSuchElementException;
import com.example.ec.service.TourRatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Tour Controller
 */
@RestController
@Tag(name = "Rating", description = "The Rating API")
@RequestMapping(path = "/ratings")
public class RatingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
    private TourRatingService tourRatingService;

    @Autowired
    public RatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @GetMapping
    @Operation(summary = "Gets all ratings")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    public List<RatingDto> getAll() {
        LOGGER.info("GET /ratings");
        return tourRatingService.lookupAll().stream()
                .map(tourRating -> new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getCustomerId())).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find ratings by id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
    		@ApiResponse(responseCode = "404", description = "Rating not found") })
    public RatingDto getRating(@PathVariable("id") Integer id) {
        LOGGER.info("GET /ratings/{id}", id);
        return new RatingDto(tourRatingService.lookupRatingById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found"))
        );
    }

}
