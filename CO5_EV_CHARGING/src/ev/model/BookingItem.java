package ev.model;
public class BookingItem {
    private ChargingSlot slot;
    private double unitsKWh;
    public BookingItem(ChargingSlot slot,double units){this.slot=slot;unitsKWh=units;}
    public ChargingSlot getSlot(){return slot;}
    public double getUnitsKWh(){return unitsKWh;}
    public double getLineTotal(){return slot.getRatePerUnit()*unitsKWh;}
}
