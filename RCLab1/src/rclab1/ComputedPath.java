/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rclab1;

/**
 *
 * @author vicu
 */
public class ComputedPath
{

    public StationLink getFirstLink() {
        return firstLink;
    }

    public StationLink getLastLink() {
        return LastLink;
    }

    /**
     *
     * @return
     */
    public double getDistance() {
        return distance;
    }

    public void setFirstLink(StationLink firstLink) {
        this.firstLink = firstLink;
    }

    public void setLastLink(StationLink LastLink) {
        this.LastLink = LastLink;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    private StationLink firstLink;
    private StationLink LastLink;
    private double distance;
}

