package ev.model;
public abstract class User {
    private String userId, name, password;
    protected User(String userId, String name, String password) {
        this.userId=userId; this.name=name; this.password=password;
    }
    public String getUserId(){ return userId; }
    public String getName(){ return name; }
    public String getPassword(){ return password; }
    public void setName(String name){ this.name=name; }
    public abstract String getRole();
    public abstract double getDiscountRate();
    @Override public String toString(){ return getRole()+" ["+userId+" - "+name+"]"; }
}
