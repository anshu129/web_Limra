package com.limrainfracon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.Plot;

@Repository
public interface PlotRepository extends JpaRepository<Plot, String> {
	 @Query(value = "SELECT * FROM Plot WHERE project_id = ?1", nativeQuery = true)
	 List<Plot> findPlotsByProjectId(Long projectId);

	@Query(value = "SELECT * FROM Plot WHERE phase_id = ?1", nativeQuery = true)
	List<Plot> findPlotsByPhaseId(String phaseId);
	
	@Query(value = "SELECT * FROM Plot WHERE plot_id = ?1", nativeQuery = true)
	Plot findByPlotId(String plotId);

	 @Query(value = "SELECT * FROM plot WHERE project_id = :projectId AND phase_id = :phaseId AND plot_number = :plotNumber LIMIT 1", nativeQuery = true)
	 Optional<Plot> findByProjectIdPhaseIdAndPlotNumber(@Param("projectId") String projectId, @Param("phaseId") String phaseId, @Param("plotNumber") String plotNumber);


}
