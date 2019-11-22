package EnginePackage;

class Wheel{
    public double radius;//in m
    public double pressure;//in psi

    public Wheel(){
        radius=0.5;
        pressure=32;
    }
}

public class Engine{
    public int speed;
    public String oilCond;
    public Wheel w;
    public int g;

    public double fuel; 


    public Engine(){
        w = new Wheel();
        g = 0;
        oilCond = "good";
        speed = 0; 
    }


    public void accelarate(int init){

        //int speed=rpm*3.14*(w.radius/1000)*60;
            
            if(init ==0 )  
                speed+=init;
            else if(speed<150)
                speed+=4;

            


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

                System.out.println("eng.speed before"+this.speed);
                System.out.println("eng.g before"+this.g);

            //try{ Thread.sleep(100);}catch(Exception e){}
    }

    public void start(){
        
    }

}