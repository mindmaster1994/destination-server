package com.ieng.task.dests.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ieng.task.dests.dtos.interfaces.GetDestinationListing;
import com.ieng.task.dests.model.Destination;


@Repository
@Transactional
public interface DestinationRepository extends JpaRepository<Destination, Long> {
	
	String DESTINATION_LISTING_QUERY = "SELECT t1.id AS id, t1.title AS title, t1.location_name AS locationName, t1.image AS image FROM dests_db.destination t1";
	String DESTINATION_LISTING_QUERY_CONT = "select COUNT(*) from ( " + DESTINATION_LISTING_QUERY + " ) as rows_count";

	String REGION_SEARCH_QUERY = DESTINATION_LISTING_QUERY + "AND where LOWER(t1.title) like :q";

	@Query(value = DESTINATION_LISTING_QUERY, countQuery = DESTINATION_LISTING_QUERY_CONT, nativeQuery = true)
	Page<GetDestinationListing> getDestinationListings(Pageable pageable);

}