package ev.model;
public class CorporateUser extends User {
    public CorporateUser(String id,String name,String password){ super(id,name,password); }
    @Override public String getRole(){ return "CORPORATE"; }
    @Override public double getDiscountRate(){ return 0.15; }
}
