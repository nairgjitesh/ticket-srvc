package com.ticket.service.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.ticket.service.domain.Seat;
import com.ticket.service.domain.SeatHold;
import com.ticket.service.helper.Constants;

public class SeatsDAO {

	
	private static int availableSeats;
	public static int currentAvailableRow;
	public static int currentAvailableSeatNum;
	public static int ticketBookingId;
	private static HashMap<Integer, List<Seat>> mapRowSeats; // Total Seats
	public static HashMap<Integer, Boolean> mapRowAvailable; // Rows which are available
	private static HashMap<Integer, SeatHold> mapSeatHold; // Hold SeatHold data
	
	
	static {
		intializeSeats();
	}

	public static void intializeSeats() {
		currentAvailableRow = 1;
		currentAvailableSeatNum = 1;
		ticketBookingId =0;
		availableSeats = Constants.MAX_ROWS*Constants.MAX_ROW_SEAT;
		mapRowSeats = new HashMap<Integer, List<Seat>>();
		mapSeatHold = new HashMap<Integer, SeatHold>();
		initializeRowAvailability();
	}

	private static void initializeRowAvailability() {
		mapRowAvailable = new HashMap<Integer, Boolean>();
			for(int i =1; i<=Constants.MAX_ROWS;i++) {
				mapRowAvailable.put(i, false);
			}
	}

	public static int getAvailableSeats() {
		return availableSeats;
	}
	
	public static int reduceAvailableSeats() {
		availableSeats--;
		return availableSeats;
	}
	public static int increaseAvailableSeats() {
		 availableSeats++;
		 return availableSeats;
	}
		
	public static HashMap<Integer, List<Seat>> getSeatRowDataMap() {
		return mapRowSeats;
	}
	
	public static void holdSeat(int rowHoldNum,  List<Seat> seatList) {
		mapRowSeats.put(rowHoldNum, seatList);
	}

	public static void holdSeatConfirm(SeatHold seatHold) {
		mapSeatHold.put(seatHold.getTicketBookingId(), seatHold);
		
	}

	public static SeatHold getSaveHoldData(int seatHoldId) {
		return mapSeatHold.get(seatHoldId);
	}

	public static int getNewTicketBookingId() {
		ticketBookingId++;
		 return ticketBookingId;

	}

	
	
}
