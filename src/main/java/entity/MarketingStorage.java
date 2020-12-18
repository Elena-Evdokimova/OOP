package entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "root")
public class MarketingStorage {

    private List<Advertising> advertisingStorage;
    private List<Agent> agents;

    public MarketingStorage() {
    }

    public MarketingStorage(List<Advertising> advertisingStorage, List<Agent> agents) {
        this.advertisingStorage = advertisingStorage;
        this.agents = agents;
    }

    public List<Advertising> getAdvertisingStorage() {
        return advertisingStorage;
    }

    @XmlElementWrapper(name = "advertising")
    @XmlElement(name = "advertising")
    public void setAdvertisingStorage(List<Advertising> advertisingStorage) {
        this.advertisingStorage = advertisingStorage;
    }

    @XmlElementWrapper(name = "agent")
    @XmlElement(name = "agent")
    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

}
