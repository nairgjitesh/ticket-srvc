package com.ticket.service.domain;

import java.util.HashMap;
import java.util.List;

public class SeatHold {

	private int ticketBookingId;
	private String customerEmailId; 
	private String seatStatus;
	private List<Seat> seats;
	private long holdTimeStamp; 
	private long confirmationTimeStamp; 
	private String confirmationCode; 
	private double totalAmount;
	private String seatHoldMessage;
	private HashMap<Integer,List<Integer>> rowsHeld;
	
	public int getTicketBookingId() {
		return ticketBookingId;
	}
	public void setTicketBookingId(int ticketBookingId) {
		this.ticketBookingId = ticketBookingId;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public String getSeatStatus() {
		return seatStatus;
	}
	public void setSeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	public long getHoldTimeStamp() {
		return holdTimeStamp;
	}
	public void setHoldTimeStamp(long holdTimeStamp) {
		this.holdTimeStamp = holdTimeStamp;
	}
	public long getConfirmationTimeStamp() {
		return confirmationTimeStamp;
	}
	public void setConfirmationTimeStamp(long confirmationTimeStamp) {
		this.confirmationTimeStamp = confirmationTimeStamp;
	}
	public String getConfirmationCode() {
		return confirmationCode;
	}
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSeatHoldMessage() {
		return seatHoldMessage;
	}
	public void setSeatHoldMessage(String seatHoldMessage) {
		this.seatHoldMessage = seatHoldMessage;
	}
	public HashMap<Integer, List<Integer>> getRowsHeld() {
		return rowsHeld;
	}
	public void setRowsHeld(HashMap<Integer, List<Integer>> rowsHeld) {
		this.rowsHeld = rowsHeld;
	}
	

	

}
