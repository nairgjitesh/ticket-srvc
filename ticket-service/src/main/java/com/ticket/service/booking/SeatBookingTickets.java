package com.ticket.service.booking;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ticket.service.TicketService;
import com.ticket.service.TicketServiceImpl;
import com.ticket.service.dao.SeatsDAO;

public class SeatBookingTickets {

	public static void main(String[] args) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		TicketService ticketSrvc = new TicketServiceImpl();
		String emailID = null;
		int numSeats = 0;
		int seatHoldConfirmId = 0;

		
		while(true){
			try {
				System.out.println("\nPlease select one of the below options");
				System.out.println("1 - Check seat Availability");
				System.out.println("2 - Hold and Book Seats");
				System.out.println("3 - Reserve Seats");
				System.out.println("4 - Reset the Ticket Service System");
				System.out.println("5 - Exit");
				String userEntry = reader.readLine();
				
				switch (userEntry) {
					case "1":
					System.out.println("Number of seats available "+ticketSrvc.numSeatsAvailable());
					break;
					case "2":
						System.out.println("Please Enter your EmailID:");
						emailID = reader.readLine();
						System.out.println("Please Enter the Number of seats:");
						numSeats = Integer.parseInt(reader.readLine());
						System.out.println((ticketSrvc.findAndHoldSeats(numSeats, emailID)).getSeatHoldMessage());
						break;
					case "3":
						System.out.println("Please Enter Ticket Hold Confirmation ID:");
						seatHoldConfirmId = Integer.parseInt(reader.readLine());
						System.out.println(ticketSrvc.reserveSeats(seatHoldConfirmId, emailID));
						break;
					case "4":
						SeatsDAO.intializeSeats();;
						System.out.println("Theatre Seats - Reset / Re-Initialized");
						break;
					default:
						System.exit(0);
				}
				
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

}
