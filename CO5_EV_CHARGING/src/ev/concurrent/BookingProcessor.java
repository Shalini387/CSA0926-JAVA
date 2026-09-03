package ev.concurrent;
import ev.collection.SlotRegistry;
import ev.exception.*;
import ev.model.*;
public class BookingProcessor implements Runnable {
    private final SlotRegistry registry; private final User user;
    private final int slotId,ports,bookingId; private final double unitsKWh;
    public BookingProcessor(SlotRegistry r,User u,int bid,int sid,int p,double kwh){
        registry=r;user=u;bookingId=bid;slotId=sid;ports=p;unitsKWh=kwh;
    }
    public void run(){
        try{
            ChargingSlot s=registry.get(slotId);
            registry.bookSlot(slotId,ports);
            Booking b=new Booking(bookingId,user);
            b.addItem(new BookingItem(s,unitsKWh));
            System.out.printf("[%s] %s booked at %s -> Net Rs.%.2f%n",
                Thread.currentThread().getName(),user.getName(),s.getStationName(),b.getNetAmount());
        }catch(SlotUnavailableException|InvalidBookingException e){
            System.out.printf("[%s] FAILED for %s: %s%n",
                Thread.currentThread().getName(),user.getName(),e.getMessage());
        }
    }
}
