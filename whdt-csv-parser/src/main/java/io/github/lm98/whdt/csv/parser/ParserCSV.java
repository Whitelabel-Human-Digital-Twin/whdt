package io.github.lm98.whdt.csv.parser;

import io.github.lm98.whdt.core.hdt.model.property.*;
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration;
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfigurationBuilder;
import it.wldt.adapter.mqtt.physical.exception.MqttPhysicalAdapterConfigurationException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class ParserCSV {
	private final static Logger logger = LoggerFactory.getLogger(ParserCSV.class);

    //BROKER URL
    private static String BROKER_URI = "tcp://127.0.0.1:1883";
	private final Pattern PATTERN = Pattern.compile("\\d+,\\d{1,15}");
	
	private final String nomeFile;
    private List<String> dates = new ArrayList<>();
    private List<String> header = new ArrayList<>();
	private Map<Integer,GenericProperty> properties = new HashMap<>();
	
	public ParserCSV(String nome) {
		this.nomeFile = nome;
	}
	private String convertiRiga(String riga) {
		String tmp = PATTERN.matcher(riga).replaceAll(match -> {
			return match.group().replace(",", "#!@");
		});

		tmp = tmp.replace(",", ";");

		tmp = tmp.replace(".", ",");

		tmp = tmp.replace("\"", "");

		tmp = tmp.replace("#!@", ",");

		return tmp;
	}

	private void leggiFile(){
		try {
			String tmp;
			BufferedReader reader = Files.newBufferedReader(Paths.get(nomeFile));
			header = Arrays.asList(reader.readLine().replace(",", ";").split(";"));
			while ( (tmp = reader.readLine()) != null) {
				Arrays.asList(convertiRiga(tmp).split(";")).forEach(ele -> dates.add(ele));
			}
			dates.removeAll(header);
			System.out.println(header.size());
			header.forEach(dati -> System.out.println("testa = "+dati));
			System.out.println(dates.size());
			dates.forEach(dati -> System.out.println("dato = "+dati));
			if((dates.size() % header.size()) != 0 )
				throw new sizeException("il numero degli elementi non coincide con quello degli header");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createProperties() {
		int index = 0;
		for (String s : header) {
			properties.put((index+1),new GenericProperty(s, s,"A generic property that can hold any type of value.", Integer.toString(index), this.getPropertyValue(index)));
			index++;
		}
		properties.forEach((p,g) -> System.out.println("Proprieta' numero "+p+" : "+g.toString()));
	}

	private PropertyValue getPropertyValue(int col) {
		if(identifyDataType(dates.get(col)).equals("Float"))
			return new PropertyValue.FloatPropertyValue(Float.parseFloat(dates.get(col)));
		else if(identifyDataType(dates.get(col)).equals("Integer"))
			return new PropertyValue.IntPropertyValue(Integer.parseInt(dates.get(col)));
		else if(identifyDataType(dates.get(col)).equals("Boolean"))
			return new PropertyValue.BooleanPropertyValue(Boolean.parseBoolean(dates.get(col)));
		else if(identifyDataType(dates.get(col)).equals("String"))
			return new PropertyValue.StringPropertyValue(dates.get(col));
		else
			throw new DataTypeException("Tipo di dato non riconnosciuto dal sistema");
	}
	
	public MqttPhysicalAdapterConfiguration mqttAdapter() {
		 try {
			 MqttPhysicalAdapterConfigurationBuilder conf = MqttPhysicalAdapterConfiguration.builder("127.0.0.1", 1883);
			 for(int i=0;i<header.size();i++) {
				 if(identifyDataType(dates.get(i)).equals("Double")) {
					 System.out.println("Valore letto "+ dates.get(i)+ " tipo : Double");
					 conf.addPhysicalAssetPropertyAndTopic(header.get(i), 0, "topic/"+header.get(i), Double::parseDouble);
				 }else if(identifyDataType(dates.get(i)).equals("Integer")) {
					 System.out.println("Valore letto "+ dates.get(i)+ " tipo : Integer");
					 conf.addPhysicalAssetPropertyAndTopic(header.get(i), 0, "topic/"+header.get(i), Integer::parseInt);
				 }else if(identifyDataType(dates.get(i)).equals("String")) {
					 System.out.println("Valore letto "+ dates.get(i)+ " tipo : String");
					 conf.addPhysicalAssetPropertyAndTopic(header.get(i), 0, "topic/"+header.get(i), String::toUpperCase);
				 }else
					 System.out.println("Valore letto "+ dates.get(i) +" da errore");
			 }
			 MqttPhysicalAdapterConfiguration configurato = conf.build();
			 return configurato;
		 } catch (MqttPhysicalAdapterConfigurationException e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
	public String identifyDataType(String data){
		String input= data.strip();
		try {
	        Integer.parseInt(input);
	        return "Integer";
	    }catch (Exception e){}

		try{
			Float.parseFloat(input);
			return "Float";
		} catch (Exception e){}

		try {
	        Double.parseDouble(input);
	        return "Double";
	    }catch (Exception e){}

		if (input.toUpperCase().equals("TRUE") ||   input.toUpperCase().equals("FALSE")) {
			return "Boolean";
		}
		
		return "String";
	}
	
	public void updateDate() {
		try{

            String mqttClientId = UUID.randomUUID().toString();

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient client = new MqttClient(BROKER_URI,mqttClientId, persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);

            //Connect to the target broker
            logger.info("Connected ! Client Id: {}", mqttClientId);

            //Start to publish MESSAGE_COUNT messages
            for(int i = 0; i < dates.size(); i++) {
            		publishData(client,"topic/"+header.get(i%header.size()), dates.get(i).strip());
            		System.out.println(header.get(i%header.size())+" = "+ dates.get(i).strip());
            }
            //Disconnect from the broker and close connection
            client.disconnect();
            client.close();

            logger.info("Disconnected !");

        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	private void publishData(IMqttClient mqttClient, String topic, String msgString) throws MqttException {

        logger.debug("Publishing to Topic: {} Data: {}", topic, msgString);

        if (mqttClient.isConnected() && msgString != null && topic != null) {
        	
            MqttMessage msg = new MqttMessage(msgString.getBytes());
            msg.setQos(0);
            msg.setRetained(false);
            mqttClient.publish(topic,msg);
            logger.debug("Data Correctly Published !");
        }
        else{
            logger.error("Error: Topic or Msg = Null or MQTT Client is not Connected !");
        }

    }
	
	public static void main(String[] args) {
		ParserCSV parserCSV = new ParserCSV("csvEsempio.csv");
		parserCSV.leggiFile();
		parserCSV.createProperties();
	}
	
}
