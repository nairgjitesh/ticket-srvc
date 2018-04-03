package com.ticket.service.domain;

public class Seat {

	private int ticketBookingId;
	private int seatId;
	private int rowId;
	private String seatStatus;
	private String customerEmail;
	
	public Seat(int ticketBookingId, int rowHoldNum, int seatHoldNum, String strStatus, String strCustomerEmail) {
		this.ticketBookingId = ticketBookingId;
		this.seatId = seatHoldNum;
		this.rowId = rowHoldNum;
		this.seatStatus = strStatus;
		this.customerEmail = strCustomerEmail;
	}
	public int getTicketBookingId() {
		return ticketBookingId;
	}
	public void setTicketBookingId(int ticketBookingId) {
		this.ticketBookingId = ticketBookingId;
	}
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getSeatStatus() {
		return seatStatus;
	}
	public void setSeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
