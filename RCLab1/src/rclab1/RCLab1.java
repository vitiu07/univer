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
            System.out.println(p.getByType("FP"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RCLab1.class.getName()).log(Level.SEVERE, null, ex);
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
