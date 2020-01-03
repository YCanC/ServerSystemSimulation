package yazlab1.pkg2;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubControl {

    //SubProduceConsume Sub1Control;
    ArrayList<SubProduceConsume> SubPcList;
    ArrayList<Thread> SubProducerThreads;
    ArrayList<Thread> SubConsumerThreads;
    //SubProduceConsume Sub2Control;
    CurrentCapacity controlLock;
    //rrentCapacity NewControlLock = new CurrentCapacity();
    public SubControl(ArrayList<Thread> SubProducerThreadss,ArrayList<Thread> SubConsumerThreadss,ArrayList<SubProduceConsume> SubPcListt, CurrentCapacity ControlLock) {
        SubPcList=SubPcListt;
        controlLock = ControlLock;
        SubProducerThreads = SubProducerThreadss;
        SubConsumerThreads = SubConsumerThreadss;
        //Sub2Control=Sub2;
    }
    
    int isimlendirme =2;
    int ThresholdValue;
    public void control(){
        while(true)
        {
            synchronized(controlLock){
                
            for (int i = 0; i < SubPcList.size(); i++) {
                ThresholdValue = (SubPcList.get(i).getSubCapacity() *7)/10 ;
                if(SubPcList.get(i).getSubCurrentCapacity()>=ThresholdValue)
                {
                    isimlendirme+=1;
                    System.out.println("****************Sub"+(i+1)+"' in kapasitesi %70 i aştı***********");
                    
                    SubPcList.get(i).setSubCurrentCapacity(SubPcList.get(i).getSubCurrentCapacity()/2);
                    SubPcList.add(new SubProduceConsume(controlLock,(SubPcList.size()+1)));
                    SubPcList.get(SubPcList.size()-1).setSubCurrentCapacity(SubPcList.get(i).getSubCurrentCapacity());
                    SubProducerThreads.add(new Thread(new Runnable(){
                       @Override
                       public void run() {
                          
                          SubPcList.get(SubPcList.size()-1).produce(); 
                           if(Thread.currentThread().isInterrupted())
                           {
                           System.out.println("PRODUCEYİ KESTİLERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                           return; 
                           }
                         
                           
                          }
                       }
                      )       
                     );
                  SubProducerThreads.get(SubProducerThreads.size()-1).setName("SubProducerThread"+(isimlendirme));
                  SubProducerThreads.get(SubProducerThreads.size()-1).start();
                  
                 SubConsumerThreads.add(new Thread(new Runnable(){
                       @Override
                       public void run() {
                           
                          
                           SubPcList.get(SubPcList.size()-1).consume();
                           
                          System.out.println("CONSUMEYİ KESTİLERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                          
                               
                          }
                       }
                      )       
                     );
                  SubConsumerThreads.get(SubConsumerThreads.size()-1).setName("SubConsumerThread"+(isimlendirme));  
                  SubConsumerThreads.get(SubConsumerThreads.size()-1).start();
               
                 
                  /*
                          try {
                              SubThreads.get(SubThreads.size()-1).join();
                              SubThreads.get(SubThreads.size()-2).join();
                              
                          } catch (InterruptedException ex) {
                              Logger.getLogger(SubControl.class.getName()).log(Level.SEVERE, null, ex);
                          }  */
                        
                  /*  try {
                        controlLock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SubControl.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                    System.out.println("*******************SUB THREAD SAYISI ARTTI*********************SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS**** : "+(SubConsumerThreads.size()+SubProducerThreads.size()));
                }
                
                if(SubPcList.get(i).getSubCurrentCapacity()<=0 && SubPcList.size()>2 && i!=0 && i!=1)
                {
                 System.out.println("****************Sub"+(i+1)+"' SIFIRLANDIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII***********");
                 SubPcList.remove(i);
                 SubProducerThreads.get(i).interrupt();
                 SubConsumerThreads.get(i).interrupt();
                 SubProducerThreads.remove(i);
                 SubConsumerThreads.remove(i);
                 /*
                  for (int j = 0; j < SubConsumerThreads.size(); j++) {
                  SubConsumerThreads.get(j).setName("SubConsumerThread"+(SubConsumerThreads.size()));    
                 }
                  for (int j = 0; j < SubProducerThreads.size(); j++) {
                  SubProducerThreads.get(j).setName("SubProducerThread"+(SubProducerThreads.size()));    
                 } */
            
                 
                }
                
               }
              
             /*   if(Sub2Control.getSubCurrentCapacity()>=3500)
                {
                    System.out.println("********Sub2 ' in kapasitesi %70 i aştı***********");
                    try {
                        controlLock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SubControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return true;
                } */
          
            }
        }
    }
    
}