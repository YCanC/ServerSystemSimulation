package yazlab1.pkg2;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Gui implements Runnable{

    MainProduceConsume MainPc;
    ArrayList<SubProduceConsume> SubPcList;
    JFrame jframe;
    JPanel jpanel = new JPanel(); 
    JProgressBar mainServerBar = new JProgressBar();
    Label mainlabel = new Label();
    
    JProgressBar constServerBar1 = new JProgressBar();
    Label Sublabel1 = new Label();

    JProgressBar constServerBar2 = new JProgressBar();
    Label Sublabel2 = new Label();
    
   ArrayList<JProgressBar> SubServerBars = new ArrayList<>();
   ArrayList<Label> SubServerLabels = new ArrayList<>();
   CurrentCapacity currentCapacityValue;
    
    
    
    public Gui(MainProduceConsume MainPc, ArrayList<SubProduceConsume> SubPcList, CurrentCapacity currentCapacityValue) {
        this.MainPc = MainPc;
        this.SubPcList = SubPcList;
        this.currentCapacityValue = currentCapacityValue;
    }
    
    int temp=2;
    @Override
    public void run() {
        baslangic();
        
        //int temp=3;
        while(true)
       {
          try {
              Thread.sleep(40);
          } catch (InterruptedException ex) {
              Logger.getLogger(ServerSystem.class.getName()).log(Level.SEVERE, null, ex);
          }
           System.out.println("Main %"+(MainPc.MainCurrentValue)/100+" dolu");
           mainServerBar.setValue((MainPc.MainCurrentValue)/100);
           System.out.println("SubPcListSize: "+SubPcList.size());
           
           for (int i = 0; i < SubPcList.size(); i++) {
               
               if(SubServerBars.size()== SubPcList.size())
               {
               SubServerBars.get(i).setValue(SubPcList.get(i).getSubCurrentCapacity()/5);
               System.out.println("SubPcList.get"+i+" %"+SubPcList.get(i).getSubCurrentCapacity()/5+" dolu");
                jpanel.revalidate();
              jpanel.repaint();
               }
               else if (SubServerBars.size()<SubPcList.size())
               {
               SubServerBars.add(new JProgressBar());
               SubServerBars.get(SubServerBars.size()-1).setBounds(60, 120+20*i, 130, 30);
               SubServerBars.get(SubServerBars.size()-1).setValue(SubPcList.get(SubServerBars.size()-1).getSubCurrentCapacity()/5); 
               SubServerBars.get(SubServerBars.size()-1).setStringPainted(true); 
               SubServerLabels.add(new Label());
               SubServerLabels.get(SubServerLabels.size()-1).setText("Sub Server "+(SubServerBars.size()));
               SubServerLabels.get(SubServerLabels.size()-1).setFont(new Font(Sublabel2.getName(), Font.PLAIN, 15));
             
               jpanel.add(SubServerLabels.get(SubServerLabels.size()-1));
               jpanel.add(SubServerBars.get(SubServerBars.size()-1));
              jpanel.revalidate();
              jpanel.repaint();
               }
               if (SubServerBars.size()>SubPcList.size())
               {
                   for (int j = 2; j < SubServerBars.size(); j++) {
                       jpanel.remove(SubServerBars.get(j));
                       jpanel.remove(SubServerLabels.get(j));
                       SubServerBars.remove(j);
                       SubServerLabels.remove(j);
                        jpanel.revalidate();
                        jpanel.repaint();
                   }
                   for (int j = 2; j <i; j++) {
                        SubServerBars.add(new JProgressBar());
                        SubServerBars.get(SubServerBars.size()-1).setBounds(60, 120+20*j, 130, 30);
                        SubServerBars.get(SubServerBars.size()-1).setValue(SubPcList.get(SubServerBars.size()-1).getSubCurrentCapacity()/5); 
                        SubServerBars.get(SubServerBars.size()-1).setStringPainted(true); 
                        Label Sublabel3 = new Label();
                        Sublabel3.setText("Sublabel "+(SubServerBars.size()-1));
                        jpanel.add(Sublabel3);
                        jpanel.add(SubServerBars.get(SubServerBars.size()-1));
                        jpanel.revalidate();
                        jpanel.repaint();
                   }
               } 
               
           }
       
        /* if(SubServerBars.size()+1 == SubPcList.size())
           {
               SubServerBars.add(new JProgressBar());
               SubServerBars.get(SubServerBars.size()-1).setBounds(60, 120, 130, 30);
               SubServerBars.get(SubServerBars.size()-1).setValue(SubPcList.get(SubServerBars.size()-1).getSubCurrentCapacity()/5); 
               SubServerBars.get(SubServerBars.size()-1).setStringPainted(true); 
               Label Sublabel3 = new Label();
               Sublabel3.setText("Sublabel "+(SubServerBars.size()-1));
               jpanel.add(Sublabel3);
               jpanel.add(SubServerBars.get(SubServerBars.size()-1));
               jpanel.repaint();
               temp++;
           }
           */
            jpanel.revalidate();
            jpanel.repaint();
        }
      
    }
    
    public void baslangic(){
        
       jframe = new JFrame();
                
       mainServerBar.setBounds(250, 250, 250, 250);
       mainServerBar.setValue(0); 
       mainServerBar.setStringPainted(true); 
       mainlabel.setText("Main Server");
       mainlabel.setFont(new Font(mainlabel.getName(), Font.PLAIN, 15));
      
       constServerBar1.setBounds(250, 250, 250, 250);
       constServerBar1.setValue(0);
       constServerBar1.setStringPainted(true);
       Sublabel1.setText("Sub Server1");
       Sublabel1.setFont(new Font(Sublabel1.getName(), Font.PLAIN, 15));
 
   
       constServerBar2.setBounds(250, 250, 250, 250);
       constServerBar2.setValue(0);
       constServerBar2.setStringPainted(true);
       
       
       Sublabel2.setText("Sub Server2");
       Sublabel2.setFont(new Font(Sublabel2.getName(), Font.PLAIN, 15));
       
       SubServerBars.add(constServerBar1);
       SubServerBars.add(constServerBar2);
       
       SubServerLabels.add(Sublabel1);
       SubServerLabels.add(Sublabel2);
       
       jframe.add(jpanel);
       jpanel.add(mainlabel);
       jpanel.add(mainServerBar);
       jpanel.add(Sublabel1);
       jpanel.add(constServerBar1);
       jpanel.add(Sublabel2);
       jpanel.add(constServerBar2);
       jframe.setSize(350, 1000); 
       jframe.setVisible(true);
       
    }
    
}
