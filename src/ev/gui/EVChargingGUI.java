package ev.gui;
import ev.collection.*; import ev.exception.*; import ev.model.*;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*;
public class EVChargingGUI extends JFrame {
    private final SlotRegistry registry; private final User user; private final Booking cart;
    private final DefaultTableModel slotModel,billModel; private final JLabel total=new JLabel("Net Payable: Rs. 0.00");
    public EVChargingGUI(SlotRegistry r,User u){
        super("EV Charge & Bill - "+u.getRole()); registry=r;user=u;cart=new Booking(1001,u);
        setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(800,500);setLayout(new BorderLayout(8,8));
        JMenuBar mb=new JMenuBar(); JMenu file=new JMenu("File"); JMenuItem refresh=new JMenuItem("Refresh Slots");
        JMenuItem exit=new JMenuItem("Exit"); refresh.addActionListener(e->refreshSlots()); exit.addActionListener(e->dispose());
        file.add(refresh);file.addSeparator();file.add(exit);mb.add(file);setJMenuBar(mb);
        slotModel=new DefaultTableModel(new String[]{"ID","Station","Type","Rate/kWh","Ports"},0);
        JTable table=new JTable(slotModel);add(new JScrollPane(table),BorderLayout.CENTER);refreshSlots();
        billModel=new DefaultTableModel(new String[]{"Station","kWh","Line Total"},0);
        JTable bill=new JTable(billModel); JPanel right=new JPanel(new BorderLayout());
        right.add(new JLabel(" Current Booking"),BorderLayout.NORTH);right.add(new JScrollPane(bill),BorderLayout.CENTER);right.add(total,BorderLayout.SOUTH);
        right.setPreferredSize(new Dimension(300,0));add(right,BorderLayout.EAST);
        JTextField id=new JTextField(4),kwh=new JTextField(5); JButton add=new JButton("Add to Booking"),place=new JButton("Book & Bill");
        JPanel bottom=new JPanel();bottom.add(new JLabel("Slot ID:"));bottom.add(id);bottom.add(new JLabel("kWh:"));bottom.add(kwh);bottom.add(add);bottom.add(place);add(bottom,BorderLayout.SOUTH);
        add.addActionListener(e->{
            try{
                int sid=Integer.parseInt(id.getText().trim()); double units=Double.parseDouble(kwh.getText().trim());
                if(units<=0) throw new InvalidBookingException("kWh must be positive");
                registry.bookSlot(sid,1); ChargingSlot s=registry.get(sid); cart.addItem(new BookingItem(s,units));
                billModel.addRow(new Object[]{s.getStationName(),units,String.format("Rs.%.2f",s.getRatePerUnit()*units)});
                total.setText(String.format("Net Payable: Rs. %.2f",cart.getNetAmount()));refreshSlots();
            }catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Enter numeric Slot ID and kWh.");}
            catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Booking Error",JOptionPane.ERROR_MESSAGE);}
        });
        place.addActionListener(e->{
            if(cart.getItems().isEmpty()){JOptionPane.showMessageDialog(this,"Your booking is empty.");return;}
            cart.setStatus(Booking.Status.BILLED);
            JOptionPane.showMessageDialog(this,String.format(
                "Booking billed for %s%nGross: Rs.%.2f%nDiscount (%.0f%%): Rs.%.2f%nNet: Rs.%.2f",
                user.getName(),cart.getGrossAmount(),user.getDiscountRate()*100,
                cart.getDiscountAmount(),cart.getNetAmount()),"Bill",JOptionPane.INFORMATION_MESSAGE);
        });
    }
    private void refreshSlots(){slotModel.setRowCount(0);for(ChargingSlot s:registry.list())
        slotModel.addRow(new Object[]{s.getSlotId(),s.getStationName(),s.getChargerType(),
        String.format("Rs.%.2f",s.getRatePerUnit()),s.getAvailablePorts()});}
}
