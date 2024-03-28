package com.example.ec.repo;

import com.example.ec.domain.Tour;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


/**
 * Tour Repository Interface
 *
 * Created by Mary Ellen Bowman
 */
//public interface TourRepository extends PagingAndSortingRepository<Tour, Integer>,CrudRepository<Tour, Integer> {
public interface TourRepository extends JpaRepository<Tour, Integer> {	
	/**
     * Find Tours associated with the Tour Package.
     *
     * @param code tour package code
     * @return List of found tours.
     */
	//added paging
    Page<Tour> findByTourPackageCode(@Param("code")String code,Pageable pageable);
}
