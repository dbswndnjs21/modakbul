package com.modakbul.repository.campground;

import com.modakbul.dto.campground.LocationDto;
import com.modakbul.entity.campground.Location;
import com.modakbul.entity.campground.LocationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationDetailRepository extends JpaRepository<LocationDetail, Long> {
    Optional<LocationDetail> findBySigunguAndLocation(String sigungu, Location location);
    List<LocationDetail> findByLocationId(int locationId);

    LocationDetail findById(int id);
}
