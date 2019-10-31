
public class TicketNumber {
	private static double ticketsAvailable;
	private static double ticketsSold;
	
	public TicketNumber(double ticketsAvailable){
		TicketNumber.ticketsAvailable = ticketsAvailable;
		ticketsSold = 0;
	}
	
	public boolean updateTickets(double num){
		if(ticketsSold+num <= ticketsAvailable){
			ticketsSold += num;
			return true;
		} else {
			return false;
		}
	}
	
	// REMOVED: Same as updateTickets, except not synchronized
	// public boolean updateTicket(double num){
	// 	if(ticketsSold+num <= ticketsAvailable){
	// 		ticketsSold += num;
	// 		return true;
	// 	} else {
	// 		return false;
	// 	}
	// }
	
	public boolean soldAllTickets(){
		return(ticketsAvailable == ticketsSold);
	}
	
	public double getTicketsSold(){
		return ticketsSold;
	}
}
