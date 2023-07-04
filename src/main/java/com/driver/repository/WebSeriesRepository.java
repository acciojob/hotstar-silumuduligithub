package com.driver.repository;

import com.driver.model.WebSeries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebSeriesRepository extends JpaRepository<WebSeries,Integer> {

    Optional<WebSeries> findBySeriesName(String seriesName);
}
