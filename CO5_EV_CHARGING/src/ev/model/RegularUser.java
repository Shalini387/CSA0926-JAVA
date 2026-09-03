package ev.model;
public class RegularUser extends User {
    public RegularUser(String id,String name,String password){ super(id,name,password); }
    @Override public String getRole(){ return "REGULAR"; }
    @Override public double getDiscountRate(){ return 0.0; }
}
