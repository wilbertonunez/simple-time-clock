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
import javax.persistence.Table;

@Entity
@Table(name = "shift_break")
public class ShiftBreak {
	
	private Integer id;
	private LocalDateTime breakStart;
	private LocalDateTime breakEnd;
	private Shift shift;
	private BreakMotive motive;
	
	public ShiftBreak() {}
	
	public ShiftBreak(Shift shift, LocalDateTime breakStart, BreakMotive motive) {
		this.shift = shift;
		this.breakStart = breakStart;
		this.motive = motive;
	}
	
	@Column(name = "break_start")
	public LocalDateTime getBreakStart() {
		return breakStart;
	}
	public void setBreakStart(LocalDateTime breakStart) {
		this.breakStart = breakStart;
	}
	@Column(name = "break_end")
	public LocalDateTime getBreakEnd() {
		return breakEnd;
	}
	public void setBreakEnd(LocalDateTime breakEnd) {
		this.breakEnd = breakEnd;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id")
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	
	@Enumerated(EnumType.STRING)
	public BreakMotive getMotive() {
		return motive;
	}
	public void setMotive(BreakMotive motive) {
		this.motive = motive;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
