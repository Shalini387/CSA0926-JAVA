package ev.dao;
import ev.model.ChargingSlot; import java.sql.*; import java.util.*;
public class SlotDAO {
    public void insert(ChargingSlot s)throws SQLException{String q="INSERT INTO charging_slot VALUES(?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1,s.getSlotId());p.setString(2,s.getStationName());p.setString(3,s.getChargerType());p.setDouble(4,s.getRatePerUnit());p.setInt(5,s.getAvailablePorts());p.executeUpdate();}}
    public void update(ChargingSlot s)throws SQLException{String q="UPDATE charging_slot SET station_name=?,charger_type=?,rate_per_unit=?,available_ports=? WHERE slot_id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setString(1,s.getStationName());p.setString(2,s.getChargerType());p.setDouble(3,s.getRatePerUnit());p.setInt(4,s.getAvailablePorts());p.setInt(5,s.getSlotId());p.executeUpdate();}}
    public void delete(int id)throws SQLException{try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM charging_slot WHERE slot_id=?")){p.setInt(1,id);p.executeUpdate();}}
    public List<ChargingSlot> findAll()throws SQLException{List<ChargingSlot> a=new ArrayList<>();try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM charging_slot")){while(r.next())a.add(new ChargingSlot(r.getInt(1),r.getString(2),r.getString(3),r.getDouble(4),r.getInt(5)));}return a;}
}
