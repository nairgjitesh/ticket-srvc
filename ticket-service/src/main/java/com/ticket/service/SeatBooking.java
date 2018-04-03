package com.ticket.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ticket.service.dao.SeatsDAO;
import com.ticket.service.domain.Seat;
import com.ticket.service.domain.SeatHold;
import com.ticket.service.exception.TicketServiceException;
import com.ticket.service.helper.Constants;

public class SeatBooking {

	public int getAvailableSeats() {

		
		return SeatsDAO.getAvailableSeats();
	}

	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) throws TicketServiceException {
		
		
		
		if(numSeats > Constants.MAX_BOOK_NUM_SEAT)
			throw new TicketServiceException ("Seats cannot be booked more than "+Constants.MAX_BOOK_NUM_SEAT+ " at one time");
		
		
		if(numSeats > SeatsDAO.getAvailableSeats())
			throw new TicketServiceException ("Seats requested more than Available seats : Available seats : "+SeatsDAO.getAvailableSeats());
	
		
		SeatHold seatHold = new SeatHold();
		seatHold.setTicketBookingId(SeatsDAO.getNewTicketBookingId());
		seatHold.setHoldTimeStamp(System.currentTimeMillis());
		seatHold.setCustomerEmailId(customerEmail);
		seatHold.setSeats(new ArrayList<Seat>());
		HashMap<Integer, List<Seat>> hmStageSeats = SeatsDAO.getSeatRowDataMap();
		
		
		
		int rowHoldNum = SeatsDAO.currentAvailableRow;
		int rowNextHoldNum = SeatsDAO.currentAvailableRow;
		int currentRowAvailSeats = numSeats;
		int nextRowAvailSeats = 0;
		List<Seat> lstSeat = hmStageSeats.get(rowHoldNum);
		boolean bnNextRow = false;
		
		
		
		if(lstSeat==null)
			lstSeat = new ArrayList<Seat>();
		int currentSeatSize = lstSeat.size();
		
		if(lstSeat.size()>=Constants.MAX_ROW_SEAT) {
			SeatsDAO.mapRowAvailable.put(rowHoldNum, true);
			rowHoldNum= getNewCurrentAvailRow(rowHoldNum);
		}
		
		if((lstSeat.size()+numSeats)>=Constants.MAX_ROW_SEAT) {
			currentRowAvailSeats = Constants.MAX_ROW_SEAT - lstSeat.size();
			nextRowAvailSeats = numSeats - (Constants.MAX_ROW_SEAT - lstSeat.size());
			rowNextHoldNum= getNewCurrentAvailRow(rowHoldNum);
			bnNextRow = true;
			SeatsDAO.mapRowAvailable.put(rowHoldNum, true);
		}
				
		
		while(currentRowAvailSeats!=0) {
				holdSeats(seatHold, rowHoldNum, currentSeatSize, customerEmail,lstSeat);
				seatHold.setTotalAmount(seatHold.getTotalAmount()+Constants.SEAT_AMOUNT);
				currentRowAvailSeats--;
				
			}
		SeatsDAO.holdSeat(rowHoldNum, lstSeat);			
		
			
		if(bnNextRow) {
			lstSeat = hmStageSeats.get(rowNextHoldNum);
			if(lstSeat==null)
				lstSeat = new ArrayList<Seat>();
			
			currentSeatSize = lstSeat.size();
			SeatsDAO.currentAvailableRow=rowNextHoldNum;
			while(nextRowAvailSeats!=0) {
				holdSeats(seatHold, rowNextHoldNum, currentSeatSize, customerEmail,lstSeat);
				seatHold.setTotalAmount(seatHold.getTotalAmount()+Constants.SEAT_AMOUNT);
				SeatsDAO.reduceAvailableSeats();
				nextRowAvailSeats--;
			}
			SeatsDAO.holdSeat(rowNextHoldNum, lstSeat);	
		}
		
		SeatsDAO.holdSeatConfirm(seatHold);
		seatHold.setSeatHoldMessage("Seats are Held for Booking : TicketBookingId : "+seatHold.getTicketBookingId()+". "
				+ "Total Amount : "+seatHold.getTotalAmount()+"$. Seats Held for Booking : "+seatHold.getSeats().size()+". "
						+ "\n Please confirm and Reserve the tickets in "+Constants.SEAT_HOLD_TIME/1000.0+" secs");
		return seatHold;
	}

	private int getNewCurrentAvailRow(int rowHoldNum) throws TicketServiceException {
		
		int newRowHoldNum = rowHoldNum;
		boolean bnRowFound = false;
		while (newRowHoldNum <= Constants.MAX_ROWS) {
			
			if(!SeatsDAO.mapRowAvailable.get(newRowHoldNum+1)) {
				bnRowFound= true;
				newRowHoldNum += 1;
				break;
			}
			newRowHoldNum++;
		}
		
		if(!bnRowFound) {
			throw new TicketServiceException("No More Seats available. Please contact the desk");
		}
		
		
		return newRowHoldNum;
		
		
	}

	private void holdSeats(SeatHold seatHold, int rowHoldNum, int seatHoldNum, String strCustomerEmail, List<Seat> lstSeat) {
		
		Seat holdSeat = new Seat(seatHold.getTicketBookingId(),rowHoldNum,seatHoldNum,Constants.SEAT_HOLD_STATUS,strCustomerEmail);
		
		seatHold.getSeats().add(holdSeat);
		addSeatsForRowsHeld(seatHold,holdSeat,rowHoldNum);
		lstSeat.add(holdSeat);
		SeatsDAO.reduceAvailableSeats();

	}

	public SeatHold reserveSeats(int seatHoldId, String customerEmail) throws TicketServiceException{

		SeatHold seatHeld = SeatsDAO.getSaveHoldData(seatHoldId);
		checkIfHoldExpired(seatHeld);
		reserveTickets(seatHeld);
		
		return seatHeld;
	}

	
	private void reserveTickets(SeatHold seatHeld) {
		
		seatHeld.setConfirmationTimeStamp(System.currentTimeMillis());
		
		seatHeld.setConfirmationCode( Long.toHexString(Double.doubleToLongBits(Math.random())).toUpperCase());
		seatHeld.setSeatStatus(Constants.SEAT_CONFIRMED_STATUS);
		seatHeld.setSeatHoldMessage("Seat Reserved Successfully - Ticketing Id : "+seatHeld.getTicketBookingId()
				+ ". Confirmation Num : "+seatHeld.getConfirmationCode()+ " "
					+ "Total Amount : "+seatHeld.getTotalAmount()+"$. Seats Reserved : "+seatHeld.getSeats().size());
	}

	private void checkIfHoldExpired(SeatHold seatHeld) throws TicketServiceException {
		
		
		long timeDiff = System.currentTimeMillis() - seatHeld.getHoldTimeStamp();
		if(timeDiff>Constants.SEAT_HOLD_TIME) {
			removeHoldOnSeats(seatHeld);
			throw new TicketServiceException("Hold has expired on the Tickets Requested. Please request the tickets again");
		}
		
	}


	private void addSeatsForRowsHeld(SeatHold seatHold, Seat holdSeat, int rowHoldNum) {
		if(seatHold==null) {
			seatHold = new SeatHold();
		}
		HashMap<Integer,List<Integer>> hmRowsHeld = seatHold.getRowsHeld();
		if(hmRowsHeld== null) {
			hmRowsHeld = new HashMap<Integer, List<Integer>>();
		}
		
		List<Integer> lstRowsHeld = hmRowsHeld.get(rowHoldNum);
		if(lstRowsHeld==null) {
			lstRowsHeld = new ArrayList<Integer>();
		}
		lstRowsHeld.add(holdSeat.getSeatId());
		hmRowsHeld.put(rowHoldNum, lstRowsHeld);
		seatHold.setRowsHeld(hmRowsHeld);
	}

	
	private void removeHoldOnSeats(SeatHold seatHeld) {
		HashMap<Integer,List<Integer>> hmRowsHeld = seatHeld.getRowsHeld();
		
		for (int rowNum : hmRowsHeld.keySet()) {
			List<Integer> lstHeldSeatId = hmRowsHeld.get(rowNum);
			for(Integer heldSeatId : lstHeldSeatId){
				List<Seat> lstTheatreRowSeats =  SeatsDAO.getSeatRowDataMap().get(rowNum);
				for (int i = 0 ; i<lstTheatreRowSeats.size();i++) {
					Seat theatreSeat = lstTheatreRowSeats.get(i);
						if(theatreSeat!=null && heldSeatId.equals(theatreSeat.getSeatId())) {
							lstTheatreRowSeats.remove(i);  // remove the seat held from Theatre
							SeatsDAO.getSeatRowDataMap().put(rowNum, lstTheatreRowSeats);
							SeatsDAO.increaseAvailableSeats();
							break;
						}
				}
			}
		}
	}

	
}
