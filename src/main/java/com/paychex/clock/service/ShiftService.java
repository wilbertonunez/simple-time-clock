package com.paychex.clock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paychex.clock.domain.Shift;
import com.paychex.clock.domain.User;
import com.paychex.clock.repo.ShiftRepository;

@Service
public class ShiftService {
	
	private final ShiftRepository shiftRepository;
	
	public ShiftService(ShiftRepository shiftRepository) {
		this.shiftRepository = shiftRepository;
	}
	
	public List<Shift> getAllShifts(){
		List<Shift> shifts = new ArrayList<>();
		shiftRepository.findAll().forEach(shifts::add);
		
		return shifts;
	}
	
	public Shift getShift(Integer id) {
		return shiftRepository.findOne(id);
	}
	
	public Optional<Shift> findActiveShiftByUserId(Integer userId) {
		return shiftRepository.findActiveShiftByUserId(userId);
		
	}
	
	public void addShift(Shift shift) {
		shiftRepository.save(shift);
	}

	public void updateShift(Shift shift) {
		shiftRepository.save(shift);
	}
	
	public void deleteShift(Integer id) {
		shiftRepository.delete(id);
	}
}
