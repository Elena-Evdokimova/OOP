package main.task.two;

import entity.Advertising;
import entity.Agent;
import entity.MarketingStorage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static utils.InitEntityUtils.initMarketingStorage;

public class Main {

    private static final String MARKETING_STORAGE_XML = "src/main/resources/marketingStorage.xml";

    public static void main(String[] args) {
        MarketingStorage marketingStorage = initMarketingStorage();
        serializeToFile(marketingStorage);

        MarketingStorage fromFile = deserializeFromFile();
        List<Advertising> advertising = fromFile.getAdvertisingStorage();
        List<Agent> agents = fromFile.getAgents();
        System.out.println("ADVERTISING");
        advertising.forEach(System.out::println);
        System.out.println("AGENTS");
        agents.forEach(System.out::println);
    }

    private static void serializeToFile(MarketingStorage marketingStorage) {
        try {
            JAXBContext context = JAXBContext.newInstance(MarketingStorage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(marketingStorage, System.out);
            marshaller.marshal(marketingStorage, new File(MARKETING_STORAGE_XML));
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }

    private static MarketingStorage deserializeFromFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(MarketingStorage.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (MarketingStorage) unmarshaller.unmarshal(new InputStreamReader(
                    new FileInputStream(MARKETING_STORAGE_XML), StandardCharsets.UTF_8));
        } catch (FileNotFoundException | JAXBException e) {
            System.out.println(e.getMessage());
        }
        return new MarketingStorage();
    }

}
