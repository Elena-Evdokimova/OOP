package entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "broadcast")
public class Broadcast {

    private String broadcastName;
    private int broadcastPriority;
    private BigDecimal minuteCost;

    public Broadcast() {
    }

    public Broadcast(String broadcastName, int broadcastPriority, BigDecimal minuteCost) {
        this.broadcastName = broadcastName;
        this.broadcastPriority = broadcastPriority;
        this.minuteCost = minuteCost;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    @XmlElement(name = "name")
    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public int getBroadcastPriority() {
        return broadcastPriority;
    }

    @XmlElement(name = "priority")
    public void setBroadcastPriority(int broadcastPriority) {
        this.broadcastPriority = broadcastPriority;
    }

    public BigDecimal getMinuteCost() {
        return minuteCost;
    }

    @XmlElement
    public void setMinuteCost(BigDecimal minuteCost) {
        this.minuteCost = minuteCost;
    }

    @Override
    public String toString() {
        return "Broadcast: " + "broadcastName " + broadcastName + " "
                + "broadcastPriority " + broadcastPriority + " "
                + "minuteCost " + minuteCost + ". ";
    }

}
