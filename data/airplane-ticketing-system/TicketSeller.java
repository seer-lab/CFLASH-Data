import java.util.Random;

class TicketSeller implements Runnable {
	protected TicketNumber tickets;
	protected int agentNumber;
	
	public TicketSeller(TicketNumber tickets, int agentNumber) {
		this.tickets = tickets;
		this.agentNumber = agentNumber;
	}
	
	public void run() {
		boolean loop = true;
		Random rand = new Random();
		int transactionPercent;
		if((agentNumber % 2) == 0){
			transactionPercent = 45;
		} else {
			transactionPercent = 30;
		}
		int ticketMax = 4;
		while(loop){
			int randomNum = rand.nextInt(100);
			if(randomNum < transactionPercent){
				int ticketNum = rand.nextInt(ticketMax) + 1;
				if(tickets.updateTickets(ticketNum)){
					System.out.println("Ticket Agent " + agentNumber + ": Successful Transaction - " 
									   + ticketNum + " tickets sold");	
				} else {
					System.out.println("Ticket Agent " + agentNumber + ": Unsuccessful Transaction");
				}
				if(tickets.soldAllTickets()){
					loop = false;
				}
			} else {
				System.out.println("Ticket Agent " + agentNumber + ": Unsuccessful Transaction");
			}
		}
	}
}
