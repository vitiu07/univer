/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rclab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Budwar
 */
public class RCLab1 {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PDVData p = new PDVData();
            p.readFromFile("pdv.txt");
            Scanner in = new Scanner(new File("network.txt"));
            int nrOfNodes = Integer.parseInt(in.next());
            double data[][] = new double[nrOfNodes][nrOfNodes];
            while(in.hasNext())
            {
                String type = in.next();
                int from = Integer.parseInt(in.next());
                int to = Integer.parseInt(in.next());
                String conType = in.next();
                double length = Double.parseDouble(in.next());
                PDVSpec pdv = p.getByType(conType);
                if(type.equals("I"))
                {
                    data[from][to] = data[to][from] =
                            pdv.getEnvirDelay()*length + pdv.getIntermediateBase();
                    
                }
                else
                {
                    if (type.equals("S"))
                            {
                                data[from][to] = 
                                        pdv.getEnvirDelay()*length + pdv.getLeftBase();
                                data[to][from] = 
                                        pdv.getEnvirDelay()*length + pdv.getRightBase();
                            }
                }
                    
            }
           
            double w[][] = new double[nrOfNodes][nrOfNodes];
            // w = data;
            FloydWarshall(data, w);
            System.out.println("");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RCLab1.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    static void  FloydWarshall(double w[][], double d[][])
    {
       // d = new double [w.length][w.length];
        for(int i = 0; i<w.length; i++)
        {
            //System.arraycopy(w[i], 0, d[i], 0, w.length);
            for (int j = 0; j < w.length; j++) {
                d[i][j] = w[i][j]>0?w[i][j]:-223412412;
                
            }
        }
        for (int k = 0; k < w.length; k++) {
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < w.length; j++) {
                    if(i!=j)
                    d[i][j] = Math.max(d[i][j],d[i][k] + d[k][j]);
                }
            }
        }
    }
    
}

class PDVData
{
    Map container;
    PDVData()
    {
        container = new HashMap<String,PDVSpec>();
    }
    void readFromFile(String filename) throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(filename));
        while (in.hasNext())
        {
            PDVSpec specs = new PDVSpec();
            specs.setType(in.next());
            specs.setLeftBase(Double.parseDouble(in.next()));
            specs.setIntermediateBase(Double.parseDouble(in.next()));
            specs.setRightBase(Double.parseDouble(in.next()));
            specs.setEnvirDelay(Double.parseDouble(in.next()));
            specs.setMaxLen(Double.parseDouble(in.next()));
            container.put(specs.getType(), specs);
        }
    }
    
   
    PDVSpec getByType(String type)
    {
        return (PDVSpec) container.get(type);
    }
}

class PDVSpec
{

    @Override
    public String toString() {
        return "PDVSpec{" + "Type=" + Type + ", LeftBase=" + LeftBase + ", IntermediateBase=" + IntermediateBase + ", RightBase=" + RightBase + ", EnvirDelay=" + EnvirDelay + ", MaxLen=" + MaxLen + '}';
    }
    public PDVSpec()
    {
        this.Type = new String();
    }

    public PDVSpec(String Type, double LeftBase, double IntermediateBase, double RightBase, double EnvirDelay, double MaxLen) {
        this.Type = Type;
        this.LeftBase = LeftBase;
        this.IntermediateBase = IntermediateBase;
        this.RightBase = RightBase;
        this.EnvirDelay = EnvirDelay;
        this.MaxLen = MaxLen;
    }

    public String getType() {
        return Type;
    }

    public double getLeftBase() {
        return LeftBase;
    }

    public double getIntermediateBase() {
        return IntermediateBase;
    }

    public double getRightBase() {
        return RightBase;
    }

    public double getEnvirDelay() {
        return EnvirDelay;
    }

    public double getMaxLen() {
        return MaxLen;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setLeftBase(double LeftBase) {
        this.LeftBase = LeftBase;
    }

    public void setIntermediateBase(double IntermediateBase) {
        this.IntermediateBase = IntermediateBase;
    }

    public void setRightBase(double RightBase) {
        this.RightBase = RightBase;
    }

    public void setEnvirDelay(double EnvirDelay) {
        this.EnvirDelay = EnvirDelay;
    }

    public void setMaxLen(double MaxLen) {
        this.MaxLen = MaxLen;
    }
    private String Type;
    private double LeftBase;
    private double IntermediateBase;
    private double RightBase;
    private double EnvirDelay;
    private double MaxLen;
    
}

