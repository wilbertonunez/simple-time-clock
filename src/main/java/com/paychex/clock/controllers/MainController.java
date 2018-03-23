package com.paychex.clock.controllers;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paychex.clock.domain.BreakMotive;
import com.paychex.clock.domain.Shift;
import com.paychex.clock.domain.ShiftBreak;
import com.paychex.clock.domain.User;
import com.paychex.clock.domain.UserRole;
import com.paychex.clock.service.ShiftBreakService;
import com.paychex.clock.service.ShiftService;
import com.paychex.clock.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private ShiftBreakService shiftBreakService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return "about";
	}
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String process(HttpServletRequest request){
		Integer userId = Integer.parseInt(request.getParameter("id"));
		String clockaction = request.getParameter("clockaction");
		String message = "";
		if(clockaction.equals("shift")) {
			message = processShift(userId);			
		} else if(clockaction.startsWith("break")) {
			message = processBreak(userId);
		} else if(clockaction.equals("activity")) {
			message = "Not implemented yet!"; // TO DO
		}
		
		System.out.println(message);
		
		return "home";
	}

	private String processBreak(Integer userId) {
		String message = "";
		User user = userService.getUser(userId);
		Optional<Shift> checkShift = shiftService.findActiveShiftByUserId(user.getId());
		
		if(checkShift.isPresent()) {
			user.setShift(checkShift.get());
			
			Optional<ShiftBreak> checkShiftBreak = shiftBreakService.findActiveBreakByShiftId(user.getShift().getId());
			if(checkShiftBreak.isPresent()) {
				user.getShift().setShiftBreak(checkShiftBreak.get());
				ShiftBreak endedBreak = user.endBreak();
				if(endedBreak == null)
					return user.getMessage();
				
				shiftBreakService.updateShiftBreak(endedBreak);
				shiftService.updateShift(user.getShift());
				String time = endedBreak.getBreakEnd().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
				message = "Ended break at " + time;
			} else {
				ShiftBreak startedBreak = user.startBreak(BreakMotive.BREAK);
				if(startedBreak == null)
					return user.getMessage();
				
				shiftBreakService.addShiftBreak(startedBreak);
				shiftService.updateShift(user.getShift());
				String time = startedBreak.getBreakStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
				message = "Started break at " + time;
			}
			
			
		} else {
			message = "ERROR: You must be clocked in to start a break.";
		}
		
		return message;
	}

	private String processShift(Integer userId) {
		String message = "";
		User user = userService.getUser(userId);
		Optional<Shift> checkShift = shiftService.findActiveShiftByUserId(user.getId());
		
		if(checkShift.isPresent()) {
			user.setShift(checkShift.get());
			
			Optional<ShiftBreak> checkShiftBreak = shiftBreakService.findActiveBreakByShiftId(user.getShift().getId());
			boolean hasActiveBreak = false;
			if(checkShiftBreak.isPresent())	{			
				user.getShift().setShiftBreak(checkShiftBreak.get());
				hasActiveBreak = true;
			}

			Shift endedShift = user.endShift();
			if(endedShift == null)
				return user.getMessage();
			
			if(hasActiveBreak)
				shiftBreakService.updateShiftBreak(endedShift.getShiftBreak());
			
			shiftService.updateShift(endedShift);
			String time = endedShift.getEnd().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
			message = "Ended shift at " + time;
		} else {
			Shift startedShift = user.startShift();
			if(startedShift == null)
				return user.getMessage();
			
			shiftService.addShift(startedShift);
			String time = startedShift.getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
			message = "Started shift at " + time;
		}
		
		return message;
		
	}


}
