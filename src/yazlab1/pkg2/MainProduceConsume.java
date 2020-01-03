package yazlab1.pkg2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainProduceConsume {
    
    CurrentCapacity CurrentCapacity;
    MainProduceConsume(CurrentCapacity gelenSayi){
        CurrentCapacity=gelenSayi;
    }
    
    Random random = new Random();
   //Object lock = new Object();
    private int capasity = 10000;
    public int MainCurrentValue=0;
    
    public void produce(){
        while(true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized(CurrentCapacity){
                Integer value = random.nextInt(400)+1;
                
                if(CurrentCapacity.currentCapacityValue+value>=capasity){
                    //Bu kısımda alt thread üretme kısmı olacak
                    try {
                        MainCurrentValue=10000;
                        CurrentCapacity.currentCapacityValue=10000;
                     //   System.out.println("Current Value :"+CurrentCapacity.currentCapacityValue);
                     //   System.out.println("Main Producer bekliyor...");
                        
                        CurrentCapacity.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                MainCurrentValue+=value;
                CurrentCapacity.currentCapacityValue+=value;
                //long mainUretim = System.currentTimeMillis();
                //System.out.println("mainUretim: "+ mainUretim);
                //Integer value = random.nextInt(500);
                //currentCapasity+=value;
             //   System.out.println("Main Producer üretiyor................... : "+value);
              //  System.out.println("Current Value : "+CurrentCapacity.currentCapacityValue);
                CurrentCapacity.notify(); //Bekleyen Consumer threadi uyandırma işlemi
                }
            
            }
        }
    }
    
    public void consume(){
        
        while(true){
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized(CurrentCapacity){
            Integer value = random.nextInt(50)+1;
                if(CurrentCapacity.currentCapacityValue-value <= 0){
                    try {
                        MainCurrentValue=0;
                        CurrentCapacity.wait();
                     //   System.out.println("Main Consumer bekliyor..");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                MainCurrentValue-=value;
                CurrentCapacity.currentCapacityValue-=value;
              //  System.out.println("Main Consumer tüketiyor..................... : "+value);
                //System.out.println("Queue Size : "+ queue.size());
                CurrentCapacity.notify(); // Bekleyen produceri uyandırma işlemi
                }
        
            }
           
        } 
    }
    
}
