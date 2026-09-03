package ev.model;
public class PremiumUser extends User {
    public PremiumUser(String id,String name,String password){ super(id,name,password); }
    @Override public String getRole(){ return "PREMIUM"; }
    @Override public double getDiscountRate(){ return 0.10; }
}
