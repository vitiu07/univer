/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rclab1;

import java.util.ArrayList;

/**
 *
 * @author vicu
 */
public class GraphAlgorithms
{
    public static void  FloydWarshall(double w[][], double d[][], int path[][])
    {
        for(int i = 0; i<w.length; i++)
        {
            for (int j = 0; j < w.length; j++) {
                path[i][j] = j;
                if(w[i][j]!=0)
                {
                    d[i][j] = w[i][j];
                }
                else
                {
                    d[i][j] = Double.POSITIVE_INFINITY;
                }
                if(i==j) d[i][j] = 0;
            }
        }
        for (int k = 0; k < w.length; k++) {
            for (int i = 0; i < w.length; i++) {
                    for (int j = 0; j < w.length; j++) {
                             if ( d[i][j] >  d[i][k] + d[k][j])
                             {
                                    d[i][j] = d[i][k] + d[k][j];
                                    path[i][j] = path[i][k];
                             }
                    }
                }
            }
        
    }
    static void prim(int initial, double ad[][], double rs[][])
    {
        int u[] = new int[ad.length];
        int s[] = new int[ad.length];
        double min = Double.NEGATIVE_INFINITY;
        int t = 0 ,p = 0;
         for(int i=0;i<ad.length;i++) 
         {
             s[i]=0;
         }
         s[initial]=1;
          u[0]=initial;
          for(int i=0;i<ad.length-1;i++)
          {
          min=Double.NEGATIVE_INFINITY;
          for(int k=0;k<=i;k++)
             for(int j=0;j<ad.length;j++)
             { 
                if(ad[u[k]][j] != 0)
                {
                   if(s[j]==0&&(ad[u[k]][j]>min))
                   { 
                      min=ad[u[k]][j];
                      t=j;
                      p=u[k];
                   }
                }
             }
          u[i+1]=t;
          s[t]=1;
          rs[p][t] = rs[t][p] = min;
          }
    }
    public static ArrayList FloydWarshallPath(int u, int v, int path[][])
    {
        ArrayList pathReturn = new ArrayList();
        pathReturn.add(u);
        while (u != v)
        {
            u = path[u][v];
            pathReturn.add(u);
        }
        return pathReturn;
    }
}
