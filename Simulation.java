import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.security.Key;
import java.awt.Button;  
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;

class Simulation extends JFrame implements ActionListener,KeyListener {
    JFrame jfrm = new JFrame("Car Monitor App");
    private static final long serialVersionUID = 1L;
    JLabel temp = new JLabel("");
    GridBagLayout gbl = new GridBagLayout();
    JLabel obLabel=new JLabel("");
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel namLbl = new JLabel();
    boolean started=false;
    boolean running=false;
    JButton strtBtn = new JButton("START");
    JButton stpBtn = new JButton("STOP");
    JButton rflBtn = new JButton("REFILL");
    JButton stsBtn = new JButton("CHECK STATUS");
    JButton rprBtn = new JButton("REPAIR");
    JButton crtObstBtn = new JButton("CREATE OBSTACLE");
    JButton clrObstBtn = new JButton("CLEAR OBSTACLE");
    JPanel lp = new JPanel();
    JPanel rp = new JPanel();
    JPanel mp = new JPanel();
    JPanel top = new JPanel();
    Simulation() {
        String user="Malli";
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(gbl);
        user="malli";
        user = JOptionPane.showInputDialog( "Please Enter your name...!!");
        namLbl.setText("Welcome "+user);
        JMenuBar mb=new JMenuBar();
        JMenu menu1=new JMenu("menu 1");
        JMenuItem mit1=new JMenuItem("menu item 1");
        JMenuItem mit2=new JMenuItem("menu item 2");
        JMenuItem mit3=new JMenuItem("menu item 3");
        JMenuItem mit4=new JMenuItem("menu item 4");
        menu1.add(mit1);menu1.add(mit2);
        menu1.add(mit3);menu1.add(mit4);
        mit1.addActionListener(this);
        mit2.addActionListener(this);
        mit3.addActionListener(this);
        mit4.addActionListener(this);
        JMenu menu2=new JMenu("menu 2");
        JMenu menu3=new JMenu("menu 3");
        JMenu menu4=new JMenu("menu 4");
        mb.add(menu1);
        mb.add(menu2);
        mb.add(menu3);
        mb.add(menu4);
        mb.add(namLbl);
        namLbl.setBounds(0,0,100,100);
        namLbl.setFont(new Font("Arial",Font.BOLD,20));
        jfrm.add(temp);
        gbc.insets = new Insets(20,20,20,20);
        createLeftPanel();
        createMidPanel();
        createRightPanel();
        jfrm.setVisible(true);
        jfrm.addKeyListener(this);
        addKeyListener(this);
        jfrm.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    public static void main(String args[]) {
        Simulation sd=new Simulation();
        sd.increaseFuel();
    } 
    class dThread extends Thread{
        double speed;double dist;
        public void run(){
            while(true){
                try{Thread.sleep(5000);}catch(Exception e){}
                speed=Double.parseDouble(spdLbl.getText());
                dist=Double.parseDouble(odLabel.getText());
                dist+=(((speed*5/18.0)*5)/1000);
                odLabel.setText(Double.toString(dist));
                jfrm.revalidate();
                jfrm.repaint();
            }
        }
    }
    double obDist;
    class obThread extends Thread{
        String str;
        double time;
        double speed;
        public void run(){
            str = JOptionPane.showInputDialog( "Enter the obstacle distance");
            obDist=Double.parseDouble(str);
            while(true){
                speed=Double.parseDouble(spdLbl.getText());
                speed=(speed*5/18);
                try{Thread.sleep(500);}catch(Exception e){}
                obDist-=speed*0.5;
                time=(int)obDist/speed;
                String str="CAR CRASHED....!!!!";
                if(speed!=0 && obDist<=0){
                    spdLbl.setText("0");
                    JOptionPane.showMessageDialog(jfrm , str,"Crashed",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                obLabel.setText("Obstacle at "+obDist+" metres");
                repaint();
                revalidate();
                obLabel.setText("Stop The car within "+time+"  seconds to avoid collision");
                repaint();
                revalidate();
            }
        }
    }
    class flThread extends Thread{
        int fuel;
        double d2,d1;
        public void run(){
            while(true){
                if(running==true || started== true){
                    d1=Double.parseDouble(odLabel.getText());
                    try{Thread.sleep(1000);}catch(Exception e){}
                    d2=Double.parseDouble(odLabel.getText());
                    fuel=fuelBar.getValue();
                    if(fuel<10){
                        JOptionPane.showMessageDialog(jfrm, "LOW Fuel ");
                    }
                    if(fuel==0){
                        JOptionPane.showMessageDialog(jfrm, "NO fuel in the car");
                    }
                    fuel=(int)(fuel-(d2-d1)/5);
                    fuelBar.setValue(fuel);
                    repaint();
                    revalidate();;
            }
        }
        }
    }
    JButton chkButton = new JButton("CHECK CONDITION");
    void createLeftPanel(){
        gbc.gridx = 0;
        gbc.gridy = 1;
        lp.setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 2;
        lp.add(crtObstBtn,gbc);
        crtObstBtn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        lp.add(clrObstBtn,gbc);
        clrObstBtn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        lp.add(chkButton,gbc);
        chkButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        jfrm.add(lp);
    }
    class JTextFieldG extends JTextField{
        private static final long serialVersionUID = 1L;
        JTextFieldG(String str){
            this.setText(str);
            this.setBounds(30,30,50,100);
        }
    }
    JLabel spdLbl = new JLabel("0.0");
    JLabel odLabel = new JLabel("10.0");
    double distanceTraversed;
    JLabel grLbl = new JLabel("0");
    JProgressBar fuelBar = new JProgressBar(0,50);

    JButton accButton = new JButton("ACCELERATE");
    JButton brkButton = new JButton("BRAKE");

    void createMidPanel(){
        mp.setLayout(gbl);
        gbc.gridx = 1;gbc.gridy = 2;
        mp.add(new JLabel("Speed : "),gbc);
        gbc.gridx = 2;gbc.gridy = 2;
        mp.add(spdLbl,gbc);
        spdLbl.addKeyListener(this);
        gbc.gridx = 3;gbc.gridy = 2;
        mp.add(new JLabel("km/hr"),gbc);
        gbc.gridx = 1;gbc.gridy = 3;
        mp.add(new JLabel("Total Distance : "),gbc);
        gbc.gridx = 2;gbc.gridy = 3;
        mp.add(odLabel,gbc);
        gbc.gridx = 3;gbc.gridy = 3;
        mp.add(new JLabel("km"),gbc);
        gbc.gridx = 1;gbc.gridy = 4;
        mp.add(new JLabel("Gear : "),gbc);
        gbc.gridx = 2;gbc.gridy = 4;
        mp.add(grLbl,gbc);
        gbc.gridx = 2;gbc.gridy = 7;
        jfrm.add(obLabel,gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        jfrm.add(mp,gbc);
        fuelBar.setValue(0);
        fuelBar.setStringPainted(true);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mp.add(new JLabel("Fuel :"),gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        mp.add(fuelBar,gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        brkButton.addActionListener(this);
        mp.add(brkButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        accButton.addActionListener(this);
        mp.add(accButton,gbc);
    }
    void increaseFuel(){
        int i=0;
        double speed;
        speed=Double.parseDouble(spdLbl.getText());
        if(speed!=0){
            JOptionPane.showMessageDialog(jfrm, "Please Stop engine before refilling the tank..!!!");
            return;
        }
        while(i<=50){
            fuelBar.setValue(i++);
            try{Thread.sleep(150);}catch(Exception e){}
        }
    }
    void createRightPanel(){
        rp.setLayout(gbl);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridx = 3;
        gbc.gridy = 2;
        rp.add(strtBtn,gbc);
        strtBtn.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 3;
        rp.add(stpBtn,gbc);
        stpBtn.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 4;
        rp.add(rflBtn,gbc);
        rflBtn.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        jfrm.add(rp);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        String str = ae.getActionCommand();
        switch(str){
            case "ACCELERATE" :fnAccelerate();
                                break;

            case "BRAKE" : fnBrake();
                            break;

            case "START" :startEngine();
                            break;

            case "CHECK CONDITION": fnCheckCondition();
                                    break;
            case "STOP":fnStopEngine();
                        break;

            case "REFILL":increaseFuel();
                            break;

            case "CREATE OBSTACLE":createObstacle();
                                    break;

            case "CLEAR OBSTACLE":clearObstacle();
                                    break;      
        }
        jfrm.revalidate();
        jfrm.repaint();
    }
    obThread obt;
    public void createObstacle(){
        obt=new obThread();
        if(running=true){
            obt.start();
        }
    }
    public void clearObstacle(){
        double speed;
        speed = Double.parseDouble(spdLbl.getText());
        if(speed!=0)
            JOptionPane.showMessageDialog(jfrm, "Please stop car to clear the obstacle");
        else{
            JOptionPane.showMessageDialog(jfrm, "Obstacle cleared successfully");
            obLabel.setText("");
            obt.stop();
        }
        repaint();
        revalidate();
    }
    flThread flthrd=new flThread();
    public void startEngine(){
        gbc.gridx = 2;
        gbc.gridy = 6;
        jfrm.add(namLbl,gbc);
        if(started==false){
            flthrd.start();
            started=true;
        }
        revalidate();
        repaint();
    }
    dThread totalDist=new dThread();
    public void fnAccelerate(){
        double speed;
        int g;
        speed = Double.parseDouble(spdLbl.getText()); 
        g = Integer.parseInt(grLbl.getText());        
        if(started==false){
            JOptionPane.showMessageDialog(jfrm, "Please Start engine before accelerating..!!!");
            return;
        }  
        if(started == true && running == false){
            totalDist.start();
        }
        if(speed == 0.0 ){  
                speed+=5;
                running=true;
        }
        else if(speed+4 <= 150)
            speed+=4;
  
        if(running==false)
        {
            totalDist.start();
        }

        if(speed>0 && speed<=15)
            g=1;
        else if(speed>15 && speed<=25)
            g=2;
        else if(speed>25 && speed<=35)
            g=3;
        else if(speed>35 && speed<=50)
            g=4;
        else if(speed>50 && speed<=70)
            g=5;
        else if(speed >70)
            g=6;

        spdLbl.setText(Double.toString(speed));
        grLbl.setText(Integer.toString(g));
        jfrm.revalidate();
        jfrm.repaint();
    }

    public void fnBrake(){
        double speed;int g;
        speed = Double.parseDouble(spdLbl.getText()); 
        g = Integer.parseInt(grLbl.getText());         
        if(speed-4 >= 0)
            speed-=4;

        if(speed==1.0)
            speed=0;
        if(speed>0 && speed<=15)
            g=1;
        else if(speed>15 && speed<=25)
            g=2;
        else if(speed>25 && speed<=35)
            g=3;
        else if(speed>35 && speed<=50)
            g=4;
        else if(speed>50 && speed<=70)
            g=5;
        else if(speed >70)
            g=6;

        spdLbl.setText(Double.toString(speed));
        grLbl.setText(Integer.toString(g));
        jfrm.revalidate();
        jfrm.repaint();
    }
    public void fnStopEngine(){
        double speed;
        speed = Double.parseDouble(spdLbl.getText()); 
        if(speed!=0){
            JOptionPane.showMessageDialog(jfrm, "Please slow down the car before Stopping");
        }
        else{
            started=false;
            running=false;
            JOptionPane.showMessageDialog(jfrm, "Good bye");
        }
    }
    public void fnCheckCondition(){
        String str;
        int fuel;
        String status;
        if(running==true)
            status="Running";
        else
            status="Not Running";

        fuel=fuelBar.getValue();
        str="Engine Status : "+status;
        str+="\nFuel status : "+fuel+" Litres";
        str+="\nOil Condition : Good";
        JOptionPane.showMessageDialog(jfrm, str);
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP  : temp.setText("UP key pressed ");
                                    System.out.println("UP key pressed ");
                                    temp.setVisible(true);
                                    break; 
            case KeyEvent.VK_DOWN : temp.setText("DOWN key pressed ");
                                    temp.setVisible(true);
                                    break;
            case KeyEvent.VK_W : temp.setText("W key pressed ");
                                    temp.setVisible(true);
                                    break;                         
            }
            jfrm.revalidate();jfrm.repaint();
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP : temp.setText("UP key pressed ");
                                    temp.setVisible(true);
                                    break; 
            case KeyEvent.VK_DOWN : temp.setText("DOWN key pressed ");
                                    temp.setVisible(true);
                                    break; 
            }
            jfrm.revalidate();jfrm.repaint();
    }
    public void keyTyped(KeyEvent e){
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP : temp.setText("UP key pressed ");
                                    temp.setVisible(true);
                                    break; 
            case KeyEvent.VK_DOWN : temp.setText("DOWN key pressed ");
                                    temp.setVisible(true);
                                    break; 
            }
            jfrm.revalidate();jfrm.repaint();
    }
}