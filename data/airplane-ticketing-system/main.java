import java.util.ArrayList;
import java.util.List;

public class main {
	public static void main(String[] args) {
		double oversellPercent = 1 + Double.parseDouble(args[2])/100;
		double ticketsAvailable = Math.floor(Double.parseDouble(args[1]) * oversellPercent);
		TicketNumber tickets = new TicketNumber(ticketsAvailable);
		List<TicketSeller> sellers = new ArrayList<TicketSeller>();
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < Integer.parseInt(args[0]); i++){
			sellers.add(new TicketSeller(tickets, i+1));
			threads.add(new Thread(sellers.get(i),"thread"+(i+1)));
			threads.get(i).start();
		}
		try {
			for(Thread thread : threads){
				thread.join();
			}
		} catch (Exception e){
			System.out.println(e);
		}
		System.out.println("Ticket Sales Complete - " + tickets.getTicketsSold() + " tickets sold");
	}
}
