package com.ticket.service;

import com.ticket.service.domain.SeatHold;
import com.ticket.service.exception.TicketServiceException;
import com.ticket.service.helper.Constants;

public class TicketServiceImpl implements TicketService{

	@Override
	public int numSeatsAvailable() {
		SeatBooking seatBook = new SeatBooking();
		return seatBook.getAvailableSeats();
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		SeatBooking seatBook = new SeatBooking();
		try {
			return seatBook.findAndHoldSeats(numSeats, customerEmail);
		} catch (TicketServiceException e) {
			SeatHold seatHoldErr = new SeatHold();
			seatHoldErr.setSeatHoldMessage(e.getMessage());
			return (seatHoldErr);
		}catch (Exception ex) {
			SeatHold seatHoldErr = new SeatHold();
			seatHoldErr.setSeatHoldMessage(Constants.SYSTEM_EXCEPTION);
			ex.printStackTrace();
			return (seatHoldErr);
		}
		
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		SeatBooking seatBook = new SeatBooking();
		SeatHold seatHold = new SeatHold();
		try {
			seatHold = seatBook.reserveSeats(seatHoldId, customerEmail);
			return seatHold.getSeatHoldMessage();
		} catch (TicketServiceException e) {
			SeatHold seatHoldErr = new SeatHold();
			seatHoldErr.setSeatHoldMessage(e.getMessage());
			return (seatHoldErr.getSeatHoldMessage());
		}catch (Exception ex) {
			ex.printStackTrace();
			return Constants.SYSTEM_EXCEPTION;
			
		}
	}
	

}
