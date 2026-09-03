package ev.dao;
import ev.model.*; import java.sql.*;
public class BookingDAO {
    public int saveBooking(Booking b)throws SQLException{
        String q="INSERT INTO bookings(user_id,status,gross,discount,net) VALUES(?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection()){c.setAutoCommit(false);
            try(PreparedStatement p=c.prepareStatement(q,Statement.RETURN_GENERATED_KEYS)){
                p.setString(1,b.getCustomer().getUserId());p.setString(2,b.getStatus().name());p.setDouble(3,b.getGrossAmount());p.setDouble(4,b.getDiscountAmount());p.setDouble(5,b.getNetAmount());p.executeUpdate();
                ResultSet k=p.getGeneratedKeys();int id=k.next()?k.getInt(1):b.getBookingId();
                try(PreparedStatement x=c.prepareStatement("INSERT INTO booking_item(booking_id,slot_id,units_kwh,line_total) VALUES(?,?,?,?)")){
                    for(BookingItem i:b.getItems()){x.setInt(1,id);x.setInt(2,i.getSlot().getSlotId());x.setDouble(3,i.getUnitsKWh());x.setDouble(4,i.getLineTotal());x.addBatch();}x.executeBatch();}
                c.commit();return id;
            }catch(SQLException e){c.rollback();throw e;}
        }
    }
}
