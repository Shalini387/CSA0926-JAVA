package ev.collection;
import ev.exception.*;
import ev.model.*;
import java.util.*;
public class SlotRegistry {
    private final Map<Integer,ChargingSlot> slots=new HashMap<>();
    public synchronized void addOrUpdate(ChargingSlot s){slots.put(s.getSlotId(),s);}
    public synchronized ChargingSlot get(int id){return slots.get(id);}
    public synchronized List<ChargingSlot> list(){return new ArrayList<>(slots.values());}
    public synchronized void bookSlot(int id,int ports) throws InvalidBookingException,SlotUnavailableException {
        if(ports<=0) throw new InvalidBookingException("Ports requested must be positive");
        ChargingSlot s=slots.get(id);
        if(s==null) throw new InvalidBookingException("No such charging slot id: "+id);
        if(!s.isAvailable(ports)) throw new SlotUnavailableException(
            "Only "+s.getAvailablePorts()+" port(s) free at '"+s.getStationName()+"'");
        s.reducePorts(ports);
    }
    public synchronized void release(int id,int ports){
        ChargingSlot s=slots.get(id);
        if(s!=null){s.addPorts(ports); notifyAll();}
    }
}
