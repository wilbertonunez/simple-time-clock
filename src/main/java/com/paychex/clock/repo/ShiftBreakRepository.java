package com.paychex.clock.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.paychex.clock.domain.Shift;
import com.paychex.clock.domain.ShiftBreak;

public interface ShiftBreakRepository extends CrudRepository<ShiftBreak, Integer>{

	@Query("SELECT b FROM ShiftBreak b INNER JOIN Shift s ON shift_id = s.id WHERE b.breakEnd IS NULL AND s.id = ?1")
	Optional<ShiftBreak> findActiveBreakByShiftId(int shiftId);
	
	@Query("SELECT b FROM ShiftBreak b INNER JOIN Shift s ON shift_id = s.id INNER JOIN User u ON user_id = u.id WHERE b.breakEnd IS NULL AND u.id = ?1")
	Optional<ShiftBreak> findActiveBreakByUserId(int userId);
}
