package com.paychex.clock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paychex.clock.domain.Shift;
import com.paychex.clock.domain.ShiftBreak;
import com.paychex.clock.domain.User;
import com.paychex.clock.repo.ShiftBreakRepository;
import com.paychex.clock.repo.ShiftRepository;

@Service
public class ShiftBreakService {
	
	private final ShiftBreakRepository shiftBreakRepository;
	
	public ShiftBreakService(ShiftBreakRepository shiftBreakRepository) {
		this.shiftBreakRepository = shiftBreakRepository;
	}
	
	public List<ShiftBreak> getAllShiftBreaks(){
		List<ShiftBreak> shiftBreaks = new ArrayList<>();
		shiftBreakRepository.findAll().forEach(shiftBreaks::add);
		
		return shiftBreaks;
	}
	
	public ShiftBreak getShiftBreak(Integer id) {
		return shiftBreakRepository.findOne(id);
	}
	
	public Optional<ShiftBreak> findActiveBreakByShiftId(Integer shiftId) {
		return shiftBreakRepository.findActiveBreakByShiftId(shiftId);
		
	}
	
	public Optional<ShiftBreak> findActiveBreakByUserId(Integer userId) {
		return shiftBreakRepository.findActiveBreakByUserId(userId);
		
	}
	
	public void addShiftBreak(ShiftBreak shiftBreak) {
		shiftBreakRepository.save(shiftBreak);
	}

	public void updateShiftBreak(ShiftBreak shiftBreak) {
		shiftBreakRepository.save(shiftBreak);
	}
	
	public void deleteShiftBreak(Integer id) {
		shiftBreakRepository.delete(id);
	}
}
