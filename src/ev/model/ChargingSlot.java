package ev.model;
public class ChargingSlot {
    private int slotId, availablePorts;
    private String stationName, chargerType;
    private double ratePerUnit;
    public ChargingSlot(int id,String station,String type,double rate,int ports){
        slotId=id; stationName=station; chargerType=type; ratePerUnit=rate; availablePorts=ports;
    }
    public int getSlotId(){return slotId;}
    public String getStationName(){return stationName;}
    public String getChargerType(){return chargerType;}
    public double getRatePerUnit(){return ratePerUnit;}
    public int getAvailablePorts(){return availablePorts;}
    public void setStationName(String s){stationName=s;}
    public void setChargerType(String s){chargerType=s;}
    public void setRatePerUnit(double r){ratePerUnit=r;}
    public void setAvailablePorts(int p){availablePorts=p;}
    public boolean isAvailable(int p){return availablePorts>=p;}
    public void reducePorts(int p){availablePorts-=p;}
    public void addPorts(int p){availablePorts+=p;}
}
