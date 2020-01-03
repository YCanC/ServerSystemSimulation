//author Yaşar Can Çilingir 170202017 & Sergen Azizoğlu 170202039

package yazlab1.pkg2;

import java.awt.*;
import java.awt.Label;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class ServerSystem {
    public static void main(String[] args) {
        
      CurrentCapacity currentCapacityValue = new CurrentCapacity();
      MainProduceConsume MainPc = new MainProduceConsume(currentCapacityValue); 
      //SubServer SubPc2 = new SubProduceConsume(currentCapacityValue);
      //Thread subServerProduce = new Thread(new SubServerProduce(currentCapacityValue));
      ArrayList<Thread> SubThreads = new ArrayList<>();
      ArrayList<Thread> SubProducerThreads = new ArrayList<>();
      ArrayList<Thread> SubConsumerThreads = new ArrayList<>();
      
      ArrayList<SubProduceConsume> SubPcList= new ArrayList<>();
      boolean yeniThreadleriBaslat=false;
      
      Thread MainProducer = new Thread(new Runnable() {
            @Override
            public void run() {
                MainPc.produce();
            }
        });
      MainProducer.setName("mainProducerThread");
        Thread MainConsumer = new Thread(new Runnable() {
            @Override
            public void run() {
                MainPc.consume();
            }
        });
        MainConsumer.setName("mainConsumerThread");
        
  //1. Sabit Sub Serveri oluşturma işlemi
     SubPcList.add(new SubProduceConsume(currentCapacityValue,1));
    // SubProduceConsume SubPc = new SubProduceConsume(currentCapacityValue,1);
     SubProducerThreads.add(new Thread(new Runnable(){
          @Override
          public void run() {
              SubPcList.get(0).produce();
             }
          }
         )       
        );
     SubProducerThreads.get(SubProducerThreads.size()-1).setName("SubProducerThread"+(SubProducerThreads.size()));

     SubConsumerThreads.add(new Thread(new Runnable(){
          @Override
          public void run() {
              SubPcList.get(0).consume();
             }
          }
         )       
        );
     SubConsumerThreads.get(SubConsumerThreads.size()-1).setName("SubConsumerThread"+(SubConsumerThreads.size()));  
    
    //2. Sabit Sub Serveri oluşturma işlemi
     SubPcList.add(new SubProduceConsume(currentCapacityValue,2));
     SubProducerThreads.add(new Thread(new Runnable(){
          @Override
          public void run() {
              SubPcList.get(1).produce();
             }
          }
         )       
        );
        SubProducerThreads.get(SubProducerThreads.size()-1).setName("SubProducerThread"+(SubProducerThreads.size()));
      SubConsumerThreads.add(new Thread(new Runnable(){
          @Override
          public void run() {
              SubPcList.get(1).consume();
             }
          }
         )       
        );
      SubConsumerThreads.get(SubConsumerThreads.size()-1).setName("SubConsumerThread"+(SubConsumerThreads.size()));  

        
     /* 
        Thread SubProducer = new Thread(new Runnable(){
          @Override
          public void run() {
              SubPc.produce();
          }
        });
        SubProducer.setName("subProducerThread");
        
        Thread SubConsumer = new Thread(new Runnable(){
          @Override
          public void run() {
              SubPc.consume();
          }
        });
        SubConsumer.setName("subProducerThread");
        
        Thread SubProducer2 = new Thread(new Runnable(){
          @Override
          public void run() {
              SubPc2.produce();
          }
        });
        SubProducer2.setName("subProducerThread2");
        
        Thread SubConsumer2 = new Thread(new Runnable(){
          @Override
          public void run() {
              SubPc2.consume();
          }
        });
        SubConsumer2.setName("subConsumerThread2");
        */
     
     
     //threadleri kontrol kısmı
      SubControl SubControll = new SubControl(SubProducerThreads,SubConsumerThreads,SubPcList,currentCapacityValue);
        Thread SubControlThread = new Thread(new Runnable() {
          @Override
          public void run() {        
           SubControll.control();
              
          }
      });
        SubControlThread.setName("subControlThread");
        
       //Arayüz kısmı*************************************************
       
   Thread gui = new Thread(new Gui(MainPc,SubPcList,currentCapacityValue));
   gui.setName("gui threadi");
   
      
    MainProducer.start();
    MainConsumer.start();
    
    
    SubProducerThreads.get(0).start();
    SubProducerThreads.get(1).start();
    SubConsumerThreads.get(0).start();
    SubConsumerThreads.get(1).start();
    SubControlThread.start();
    gui.start();
    
    
   
       /* try {
            MainProducer.join();
            MainConsumer.join();
            SubProducerThreads.get(0).join();
            SubProducerThreads.get(1).join();
            SubConsumerThreads.get(0).join();
            SubConsumerThreads.get(1).join();
           /* SubProducer.join();
            SubConsumer.join();
            SubProducer2.join();
            SubConsumer2.join();
            SubControlThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        */

 
    }
}

