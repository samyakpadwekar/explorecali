package com.example.ec.repo;

import com.example.ec.domain.TourPackage;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * Tour Package Repository Interface
 *
 * Created by Mary Ellen Bowman
 */
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
	/**
     * Find Tour Package by name.
     *
     * @param name name of the package
     * @return Optional of TourPackage
     */
    Optional<TourPackage> findByName(String name);
    //Spring data query method
}

