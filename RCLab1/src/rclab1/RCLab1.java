/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rclab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JTextField;

/**
 *
 * @author Budwar
 */
public class RCLab1 {
    /**
     */
    public final static double StandardPDV = 575;
    public final static double StandardPVV = 49;
    private PDVData PDVSpecifications;
    private PVVData PVVSpecifications;
    private int nrOfConcentrators;
    private double ConcentratorsAdjacency[][];
    private StationLink station_links[];
    private int nrOfStations;
    private int ConcentratorsPaths[][];
    
    public void SetPVVFromFile(String filename) throws FileNotFoundException
    {
        PVVSpecifications = new PVVData();
        PVVSpecifications.readFromFile(filename);
    }
    
    public void SetPDVFromFile(String filename) throws FileNotFoundException
    {
        PDVSpecifications = new PDVData();
        PDVSpecifications.readFromFile(filename);
    }
    
    public void SetNetworkPVVConcentrFromFile(String filename) throws FileNotFoundException
    {
        try (Scanner in = new Scanner(new File(filename))) {
            nrOfConcentrators = Integer.parseInt(in.next());
            ConcentratorsAdjacency = new double[nrOfConcentrators][nrOfConcentrators];
            while(in.hasNext())
            {
                int from = Integer.parseInt(in.next());
                int to = Integer.parseInt(in.next());
                String conType = in.next();
                in.next();
                PVVSpec pvv = PVVSpecifications.getByType(conType);
                ConcentratorsAdjacency[from][to] = ConcentratorsAdjacency[to][from] =
                        pvv.getIntermediateBase();
            }
        }
    }
    
    public void SetNetworkPDVConcentrFromFile(String filename) throws FileNotFoundException
    {
        try (Scanner in = new Scanner(new File(filename))) {
            nrOfConcentrators = Integer.parseInt(in.next());
            ConcentratorsAdjacency = new double[nrOfConcentrators][nrOfConcentrators];
            while(in.hasNext())
            {
                int from = Integer.parseInt(in.next());
                int to = Integer.parseInt(in.next());
                String conType = in.next();
                double length = Double.parseDouble(in.next());
                PDVSpec pdv = PDVSpecifications.getByType(conType);
                ConcentratorsAdjacency[from][to] = ConcentratorsAdjacency[to][from] =
                        pdv.getEnvirDelay()*length + pdv.getIntermediateBase();
            }
        }
    }
    
    public void SetStationLinksFromFile(String filename) throws FileNotFoundException
    {
        int counter = 0;
        try (Scanner in = new Scanner(new File(filename))) {
            nrOfStations = Integer.parseInt(in.next());
            station_links = new StationLink[nrOfStations];
            while(in.hasNext())
            {
                int from = Integer.parseInt(in.next());
                int to = Integer.parseInt(in.next());
                String conType = in.next();
                double length = Double.parseDouble(in.next());
                station_links[counter] = new StationLink(from, to, conType, length);
                counter++;
            }
        }
    }
    public Node[] findMaxPath(ComputedPath MaxPath)
        {
            ArrayList concentratorsPath = 
                    GraphAlgorithms.
                            FloydWarshallPath(MaxPath.getFirstLink().getConcentrator(), 
                                    MaxPath.getLastLink().getConcentrator(), ConcentratorsPaths );
            Node returnPath[] = new Node[concentratorsPath.size()+2];
            returnPath[0] = new Node("S", MaxPath.getFirstLink().getStation()+1);
            int counter = 1;
            for(Object i : concentratorsPath)
            {
                returnPath[counter] = new Node("C", (int)i+1);
                counter++;
            }
            returnPath[counter] = new Node("S", MaxPath.getLastLink().getStation()+1);
            return returnPath;

        }
    
    
        public ComputedPath computeMaxPVV()
    {
        double w[][] = new double[nrOfConcentrators][nrOfConcentrators];
        ConcentratorsPaths = new int [nrOfConcentrators][nrOfConcentrators];
        ComputedPath returnPath = new ComputedPath();
        returnPath.setDistance(Double.NEGATIVE_INFINITY);
        StationLink iLink;
        //find max concentrators path
        GraphAlgorithms.prim(0,ConcentratorsAdjacency,w);
        GraphAlgorithms.FloydWarshall(w, w, ConcentratorsPaths);
        
        for(int i = 0;i<nrOfStations;i++)
        {
            iLink = station_links[i];
            double leftPVV = PVVSpecifications.getByType(iLink.getType()).getLeftBase();
            for(int j = 0; j<nrOfStations; j++)
            {
                if(iLink.getStation()!=station_links[j].getStation())
                {
                    double full_length = 
                            leftPVV + w[iLink.getConcentrator()][station_links[j].getConcentrator()];
                    if(full_length > returnPath.getDistance())
                    {
                        returnPath.setDistance(full_length);
                        returnPath.setFirstLink(iLink);
                        returnPath.setLastLink(station_links[j]);
                    }
                }
            }
        }
        return returnPath;
    }
    
    public ComputedPath computeMaxPDV()
    {
        double w[][] = new double[nrOfConcentrators][nrOfConcentrators];
        ConcentratorsPaths = new int [nrOfConcentrators][nrOfConcentrators];
        ComputedPath returnPath = new ComputedPath();
        returnPath.setDistance(Double.NEGATIVE_INFINITY);
        StationLink iLink;
        //find max concentrators path
        GraphAlgorithms.prim(0,ConcentratorsAdjacency,w);
        GraphAlgorithms.FloydWarshall(w, w, ConcentratorsPaths);
        
        for(int i = 0;i<nrOfStations;i++)
        {
            iLink = station_links[i];
            double leftPDV = 
                    PDVSpecifications.getByType(iLink.getType()).getEnvirDelay() * iLink.getLength()+
                    PDVSpecifications.getByType(iLink.getType()).getLeftBase();
            for(int j = 0; j<nrOfStations; j++)
            {
                if(iLink.getStation()!=station_links[j].getStation())
                {
                    double rightPDV = station_links[j].getLength()*PDVSpecifications.getByType(station_links[j].getType()).getEnvirDelay()+
                    PDVSpecifications.getByType(station_links[j].getType()).getRightBase();
                    double full_length = 
                            rightPDV+
                            leftPDV +
                            w[iLink.getConcentrator()][station_links[j].getConcentrator()];
                    if(full_length > returnPath.getDistance())
                    {
                        returnPath.setDistance(full_length);
                        returnPath.setFirstLink(iLink);
                        returnPath.setLastLink(station_links[j]);
                    }
                }
            }
        }
        return returnPath;
    }

    void SetPDVFromFile(JTextField pdvConfigField) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}



class StationLink
{

    @Override
    public String toString() {
        return "StationLink{" + "Station=" + Station + ", Concentrator=" + Concentrator + ", type=" + type + ", length=" + length + '}';
    }

    public StationLink(int Station, int Concentrator, String type, double length) {
        this.Station = Station;
        this.Concentrator = Concentrator;
        this.type = type;
        this.length = length;
    }
    public int getStation() {
        return Station;
    }

    public void setStation(int Station) {
        this.Station = Station;
    }

    public int getConcentrator() {
        return Concentrator;
    }

    public void setConcentrator(int Concentrator) {
        this.Concentrator = Concentrator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    private int Station;
    private int Concentrator;
    private String type;
    private double length;
}


class PVVData
{
    Map container;
    PVVData()
    {
        container = new HashMap<>();
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
            container.put(specs.getType(), specs);
        }
    }
    
   
    PVVSpec getByType(String type)
    {
        return (PVVSpec) container.get(type);
    }
}


class PDVData
{
    Map container;
    PDVData()
    {
        container = new HashMap<>();
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


class PDVSpec extends PVVSpec
{

    public PDVSpec() {
        super();
    }

    @Override
    public String toString() {
        super.toString();
        return "PDVSpec{" + "RightBase=" + RightBase + ", EnvirDelay=" + EnvirDelay + ", MaxLen=" + MaxLen + '}';
    }

    public PDVSpec(double RightBase, double EnvirDelay, double MaxLen, String Type, double LeftBase, double IntermediateBase) {
        super(Type, LeftBase, IntermediateBase);
        this.RightBase = RightBase;
        this.EnvirDelay = EnvirDelay;
        this.MaxLen = MaxLen;
    }

    public double getRightBase() {
        return RightBase;
    }

    public void setRightBase(double RightBase) {
        this.RightBase = RightBase;
    }

    public double getEnvirDelay() {
        return EnvirDelay;
    }

    public void setEnvirDelay(double EnvirDelay) {
        this.EnvirDelay = EnvirDelay;
    }

    public double getMaxLen() {
        return MaxLen;
    }

    public void setMaxLen(double MaxLen) {
        this.MaxLen = MaxLen;
    }
    
    private double RightBase;
    private double EnvirDelay;
    private double MaxLen;
}

class PVVSpec
{

    @Override
    public String toString() {
        return "PDVSpec{" + "Type=" + Type + ", LeftBase=" + LeftBase + ", IntermediateBase=" + IntermediateBase + '}';
    }
    public PVVSpec()
    {
        this.Type = new String();
    }

    public PVVSpec(String Type, double LeftBase, double IntermediateBase) {
        this.Type = Type;
        this.LeftBase = LeftBase;
        this.IntermediateBase = IntermediateBase;
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

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setLeftBase(double LeftBase) {
        this.LeftBase = LeftBase;
    }

    public void setIntermediateBase(double IntermediateBase) {
        this.IntermediateBase = IntermediateBase;
    }
    private String Type;
    private double LeftBase;
    private double IntermediateBase;
    
}

