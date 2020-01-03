package yazlab1.pkg2;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;


public class SubProduceConsume{
    
    CurrentCapacity CurrentCapacity;
    int hangiSub;
    public SubProduceConsume(CurrentCapacity gelenSayi, int GelenHangiSub){
        CurrentCapacity=gelenSayi;
        hangiSub = GelenHangiSub;
    }
    Random random = new Random();
    private int SubCapacity = 500;

    public int getSubCapacity() {
        return SubCapacity;
    }

    public void setSubCapacity(int SubCapacity) {
        this.SubCapacity = SubCapacity;
    }
    
    
    private int SubCurrentCapacity=0;

    
    public int getSubCurrentCapacity() {
           return SubCurrentCapacity;
    }

    public void setSubCurrentCapacity(int SubCurrentCapacity) {
        this.SubCurrentCapacity = SubCurrentCapacity;
    }
    
     public void produce(){
        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                return;
                //Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized(CurrentCapacity){
                Integer value = random.nextInt(150)+1;
                if(CurrentCapacity.currentCapacityValue-value>=0)
                {
                  CurrentCapacity.currentCapacityValue-=value;
                }
                
                if(CurrentCapacity.currentCapacityValue-value<0)
                {
                    value= CurrentCapacity.currentCapacityValue;
                    CurrentCapacity.currentCapacityValue=0;
                }
               
                //long SubServerAktarim = System.currentTimeMillis();
                //System.out.println("SubServereAktarim :"+SubServerAktarim);
               
                //System.out.println(value + " istek subservere aktarıldı ");
                
                if((SubCurrentCapacity+value)>=SubCapacity){
                    //Bu kısımda alt thread üretme kısmı olacak
                    try {
                        //CurrentCapacity.currentCapacityValue=10000;
                        //System.out.println("Current Value :"+CurrentCapacity.currentCapacityValue);
                      //  System.out.println("SubCurrentCapacity"+hangiSub+" doldu, Sub Producer bekliyor...");
                        CurrentCapacity.wait();
                    } catch (InterruptedException ex) {
                        return;
                        //Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                SubCurrentCapacity+=value;
                //Integer value = random.nextInt(500);
                //currentCapasity+=value;
               // System.out.println("Sub Producer"+hangiSub+" maindeki istekleri alıyor................... : "+value);
              //  System.out.println("SubCurrent"+hangiSub+" Value : "+SubCurrentCapacity);
                CurrentCapacity.notify(); //Bekleyen Consumer threadi uyandırma işlemi
            }
        }
        
         System.out.println("PRODUCER INTERRUPT EDILDI*******************************************************************");
        return;
    }
    
      public void consume(){
        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                return;
                //Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized(CurrentCapacity){
            Integer value = random.nextInt(40)+1;
                if((SubCurrentCapacity-value) <= 0){
                    try {
                        SubCurrentCapacity=0;
                       // System.out.println("Subserver Consumer"+hangiSub+" bekliyor..........");
                        CurrentCapacity.wait();
                    } catch (InterruptedException ex) {
                        return;
                        //Logger.getLogger(MainProduceConsume.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    else{
                    SubCurrentCapacity-=value;
                   // System.out.println("Subserver Consumer"+hangiSub+" tüketiyor..................... : "+value);
                    //System.out.println("Queue Size : "+ queue.size());
                    CurrentCapacity.notify(); // Bekleyen produceri uyandırma işlemi
                    }
            }
        }  
         System.out.println("CONSUMER INTERRUPT EDILDI*******************************************************************");
        //return;
    } 
    
}
