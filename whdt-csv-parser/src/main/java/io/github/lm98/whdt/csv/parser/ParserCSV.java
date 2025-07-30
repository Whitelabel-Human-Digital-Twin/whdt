package io.github.lm98.whdt.csv.parser;

import io.github.lm98.whdt.core.hdt.model.property.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class ParserCSV {

	private final Pattern PATTERN = Pattern.compile("\\d+[.,]\\d{1,20}");

	private static ParserCSV parserCSV;
    private final List<String> dates = new ArrayList<>();
    private final List<String> header = new ArrayList<>();
	private final Map<Integer,List<GenericProperty>> properties = new HashMap<>();

	// metodo per ottenere il ParserCSV
	public static ParserCSV createParserCSV(){
		if(parserCSV == null)
			parserCSV = new ParserCSV();
		return parserCSV;
	}

	// metodo statico per stampare a schermo i dati ottenuti
	public static void MyToSting(Map<Integer,List<GenericProperty>> prop){
		prop.forEach((p,g) -> g.forEach( gp -> System.out.println("Paziente numero "+ p +" : "+gp.toString())));
	}

	// metodo costruttore
	private ParserCSV() {}

	// metodo principale
	public Map<Integer,List<GenericProperty>> parsing(String fileName) {
		this.clearAll();
		if(fileName.contains(".csv"))
			this.readFile(fileName);
		else
			this.readString(fileName);
		this.createProperties();
		return this.properties;
	}

	// metodo per resettare il parser per poterlo riusare
	private void clearAll(){
		this.dates.clear();
		this.header.clear();
		this.properties.clear();
	}

	// metodo per riuscire a identificare i valori e pulire la stringa per poterci lavorare
	private String covertRow(String riga) {
		String tmp = PATTERN.matcher(riga).replaceAll(match -> {
			return match.group().replace(",", "__").replace(".", "__");
		});

		tmp = tmp.replace(",", ";");

		tmp = tmp.replace("\"", "");

		tmp = tmp.replace("__", ",");

		return tmp;
	}

	// metodo che data una stringa in entrata separare gli header dai dati controllando che ci siano abbastanza dati per gli header
	private void readString(String stringaCSV) {
		List<String> lines = divide(stringaCSV);
		this.header.addAll(Arrays.asList(covertRow(lines.getFirst()).split(";")));
		for(String line : lines) {
			if(covertRow(line).split(";").length == this.header.size())
				this.dates.addAll(Arrays.asList(covertRow(line).split(";")));
			else
				throw new SizeException("il numero degli elementi non coincide con quello degli header");
		}
		this.dates.removeAll(this.header);
	}

	// metodo per separare la varie righe cercando determinati caratteri
	private List<String> divide(String stringaCSV){
        String[] tmp = stringaCSV.split("\\r\\n|\\r|\\n");
        return new ArrayList<>(Arrays.asList(tmp));
	}

	//Metodo che dato un file in entrata separare gli header dai dati controllando che ci siano abbastanza dati per gli header. -problema con la ricerca del file
	private void readFile(String nomeFile) {
		try {
			String tmp;
			URL url = ClassLoader.getSystemClassLoader().getResource(nomeFile);
			if (url == null)
				throw new FileNotFoundException("File non trovato: " + nomeFile);
			BufferedReader reader = Files.newBufferedReader(Paths.get(url.toURI()));
			this.header.addAll(Arrays.asList(reader.readLine().replace(",", ";").split(";")));
			while ( (tmp = reader.readLine()) != null) {
				if(covertRow(tmp).split(";").length == this.header.size())
					this.dates.addAll(Arrays.asList(covertRow(tmp).split(";")));
				else
					throw new SizeException("il numero degli elementi non coincide con quello degli header");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

	//Metodo per creare le varie proprietà
	private void createProperties() {
		List<GenericProperty> tmp = new ArrayList<>();
		int index = 1;
		String description = "A generic property that can hold any type of value.";
		for (int i = 0; i < this.dates.size(); i++) {
			int p = i % this.header.size() ;
			tmp.add(new GenericProperty(this.header.get(p),this.header.get(p),description,Integer.toString(p+1),this.getPropertyValue(this.dates.get(i))));
			if(p+1 == this.header.size()) {
				this.properties.put(index, tmp.stream().toList());
				tmp.clear();
				index++;
			}
		}
	}

	//Metodo che in base al dato restituisce una proprietà adeguata
	private PropertyValue getPropertyValue(String date) {
		if(identifyDataType(date).equals("Float"))
			return new PropertyValue.FloatPropertyValue(Float.parseFloat(date));
		else if(identifyDataType(date).equals("Integer"))
			return new PropertyValue.IntPropertyValue(Integer.parseInt(date));
		else if(identifyDataType(date).equals("Boolean"))
			return new PropertyValue.BooleanPropertyValue(Boolean.parseBoolean(date));
		else if(identifyDataType(date).equals("String"))
			return new PropertyValue.StringPropertyValue(date);
		else
			throw new DataTypeException("Tipo di dato non riconosciuto dal sistema");
	}

	//Metodo che in base al dato in entrata ne identifica il tipo
	private String identifyDataType(String data){
		String input= data.strip();
		try {
	        Integer.parseInt(input);
	        return "Integer";
	    }catch (Exception _){}

		try{
			Float.parseFloat(input);
			return "Float";
		} catch (Exception _){}


		if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false"))
			return "Boolean";
		return "String";
	}
}
