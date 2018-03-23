package com.paychex.clock.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class User {

	private Integer id;
	private String firstName;
	private String lastName;	
	private Shift shift; // Represents an active shift
	private UserRole role;
	private String message;
	
	public Shift startShift() {
		if(shift == null || shift.getStatus() == ShiftStatus.FINISHED)	
			return shift = new Shift(LocalDateTime.now(), this);
		
		message = "ERROR: There is currently an active shift.";
		
		return null;
	}
	
	public Shift endShift() {
		
		Shift endedShift;
		if(shift == null || (shift.getStatus() != ShiftStatus.WORKING && role != UserRole.ADMIN)) {
			message = "ERROR: There is no active shift to end.";
			return null;
		}
		
		if(role != UserRole.ADMIN && shift.getShiftBreak() != null && shift.getShiftBreak().getBreakEnd() == null) {
			message = "ERROR: Cannot end shift while on break unless user is administrator.";
			return null;
		}
		
		LocalDateTime now = LocalDateTime.now();
		if(shift.getShiftBreak() != null && shift.getShiftBreak().getBreakEnd() == null)
			shift.getShiftBreak().setBreakEnd(now);
		
		shift.setStatus(ShiftStatus.FINISHED);
		shift.setEnd(now);
		endedShift = shift;
		
		shift = null;
		
		return endedShift;
	}

	public ShiftBreak startBreak(BreakMotive motive) {
		
		if(shift != null && shift.getStatus() == ShiftStatus.WORKING) {
			shift.setStatus(ShiftStatus.BREAK);
			ShiftBreak newBreak = new ShiftBreak(shift, LocalDateTime.now(), motive);
			shift.setShiftBreak(newBreak);
			return newBreak;
		}
		
		message = "ERROR: You must be clocked in to start a break.";
		return null;		
		
	}
	
	public ShiftBreak endBreak() {
		if(shift != null && shift.getStatus() == ShiftStatus.BREAK) {
			shift.setStatus(ShiftStatus.WORKING);
			
			ShiftBreak endedBreak = shift.getShiftBreak();
			endedBreak.setBreakEnd(LocalDateTime.now());
			
			shift.setShiftBreak(null);
			
			return endedBreak;
		}
		
		message = "ERROR: You must be on a break to end a break.";
		
		return null;
	
	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.STRING)
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@OneToOne(mappedBy = "user")
	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	@Transient
	public String getMessage() {
		return message;
	}

	
	
	
}
