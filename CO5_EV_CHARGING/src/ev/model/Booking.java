package ev.model;
import java.util.*;
public class Booking {
    public enum Status { PLACED, MODIFIED, CANCELLED, BILLED }
    private int bookingId;
    private User customer;
    private final List<BookingItem> items=new ArrayList<>();
    private Status status=Status.PLACED;
    public Booking(int id,User user){bookingId=id;customer=user;}
    public int getBookingId(){return bookingId;}
    public User getCustomer(){return customer;}
    public List<BookingItem> getItems(){return items;}
    public Status getStatus(){return status;}
    public void setStatus(Status s){status=s;}
    public void addItem(BookingItem i){items.add(i);}
    public double getGrossAmount(){double t=0; for(BookingItem i:items)t+=i.getLineTotal(); return t;}
    public double getDiscountAmount(){return getGrossAmount()*customer.getDiscountRate();}
    public double getNetAmount(){return getGrossAmount()-getDiscountAmount();}
}
