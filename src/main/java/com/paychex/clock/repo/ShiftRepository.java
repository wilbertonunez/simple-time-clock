package com.paychex.clock.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.paychex.clock.domain.Shift;

public interface ShiftRepository extends CrudRepository<Shift, Integer>{
	
	@Query("SELECT s FROM Shift s INNER JOIN User u ON user_id = u.id WHERE s.status <> 'FINISHED' AND u.id = ?1")
	Optional<Shift> findActiveShiftByUserId(int userId);

}
