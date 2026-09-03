package ev.app;
import ev.collection.*; import ev.concurrent.*; import ev.model.*; import ev.gui.EVChargingGUI;
import javax.swing.*;
public class EVChargingApp {
    public static void main(String[] args) throws Exception {
        SlotRegistry r=new SlotRegistry();
        r.addOrUpdate(new ChargingSlot(1,"MG Road","DC-Fast",18,3));
        r.addOrUpdate(new ChargingSlot(2,"Anna Nagar","AC-Slow",8,6));
        r.addOrUpdate(new ChargingSlot(3,"T-Nagar","DC-Fast",20,2));
        r.addOrUpdate(new ChargingSlot(4,"Velachery","AC-Slow",7,8));
        r.addOrUpdate(new ChargingSlot(5,"Guindy","DC-Fast",19,3));
        User[] users={new RegularUser("U1","Arjun","p"),new PremiumUser("U2","Divya","p"),
            new CorporateUser("U3","FleetCo","p"),new RegularUser("U4","Priya","p"),new RegularUser("U5","Vikram","p")};
        System.out.println("=== Concurrency demo: 5 users, only 3 MG Road ports ===");
        Thread[] ts=new Thread[users.length];
        for(int i=0;i<users.length;i++){ts[i]=new Thread(new BookingProcessor(r,users[i],2000+i,1,1,7.5),"T-"+(i+1));ts[i].start();}
        for(Thread t:ts)t.join();
        System.out.println("Remaining ports = "+r.get(1).getAvailablePorts());
        SlotRegistry gui=new SlotRegistry();
        gui.addOrUpdate(new ChargingSlot(1,"MG Road","DC-Fast",18,4));
        gui.addOrUpdate(new ChargingSlot(2,"Anna Nagar","AC-Slow",8,6));
        gui.addOrUpdate(new ChargingSlot(3,"T-Nagar","DC-Fast",20,2));
        gui.addOrUpdate(new ChargingSlot(4,"Velachery","AC-Slow",7,8));
        gui.addOrUpdate(new ChargingSlot(5,"Guindy","DC-Fast",19,3));
        SwingUtilities.invokeLater(()->new EVChargingGUI(gui,new RegularUser("U101","Charging User","pw")).setVisible(true));
    }
}
