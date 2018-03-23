package com.paychex.clock.domain;
import java.time.*;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Shift {
	
	private Integer id;
	private LocalDateTime start;
	private LocalDateTime end;
	private ShiftStatus status;
	private User user;
	private ShiftBreak shiftBreak;
	
	public Shift() { }
	
	public Shift(LocalDateTime start, User user) {
		this.start = start;
		this.user = user;
		this.setStatus(ShiftStatus.WORKING);
	}
	
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Enumerated(EnumType.STRING)
	public ShiftStatus getStatus() {
		return status;
	}

	public void setStatus(ShiftStatus status) {
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(mappedBy = "shift")
	public ShiftBreak getShiftBreak() {
		return shiftBreak;
	}

	public void setShiftBreak(ShiftBreak shiftBreak) {
		this.shiftBreak = shiftBreak;
	}
	
	

}
