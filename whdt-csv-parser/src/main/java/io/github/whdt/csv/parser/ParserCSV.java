package io.github.whdt.csv.parser;

import io.github.whdt.core.hdt.model.property.*;
import io.github.whdt.core.hdt.model.property.Properties;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class ParserCSV {

	private final Pattern patternPunto = Pattern.compile("\\d+\\.+\\d");
	private final Pattern patternVirgola = Pattern.compile("\"[^\"]*\\d+,+\\d+[^\"]*\"");

	private static ParserCSV parserCSV;
	private final List<String> dates = new ArrayList<>();
	private final List<String> header = new ArrayList<>();
	private final Map<String,List<Property>> properties = new HashMap<>();

	// metodo per ottenere il ParserCSV
	public static ParserCSV createParserCSV(){
		if(parserCSV == null)
			parserCSV = new ParserCSV();
		return parserCSV;
	}

	// metodo statico per stampare a schermo i dati ottenuti
	public static void MyToSting(Map<String,List<Property>> prop){
		prop.forEach((p,g) -> g.forEach( gp -> System.out.println("Paziente numero "+ p +" : "+gp.toString())));
	}

	// metodo costruttore
	private ParserCSV() {}

	// metodo principale
	public Map<String,List<Property>> parsing(String fileName) {
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
		String tmp = patternPunto.matcher(riga).replaceAll(match -> match.group().replace(".", "__"));

		tmp = patternVirgola.matcher(tmp).replaceAll(match -> match.group().replace(",", "__"));

		tmp = tmp = tmp.replace(",", ";");

		tmp = tmp.replace("\"", "");

		tmp = tmp.replace("__", ".");

		return tmp;
	}

	// metodo che data una stringa in entrata separare gli header dai dati controllando che ci siano abbastanza dati per gli header
	private void readString(String stringaCSV) {
		List<String> lines = divide(stringaCSV);
		this.header.addAll(Arrays.asList(covertRow(lines.getFirst()).split(";")));
		int index = 1;
		for(String line : lines) {
			String[] tmp_line = covertRow(line).split(";");
			if(tmp_line.length == this.header.size())
				this.dates.addAll(Arrays.asList(tmp_line));
			else
				throw new SizeException(index,this.header,tmp_line);
			index++;
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
			int index=1;
			while ( (tmp = reader.readLine()) != null) {
				String[] tmp_line = covertRow(tmp).split(";");
				if( tmp_line.length == this.header.size())
					this.dates.addAll(Arrays.asList(tmp_line));
				else
					throw new SizeException(index,this.header,tmp_line);
				index++;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	//Metodo per creare le varie proprietà
	private void createProperties() {
		List<Property> tmp = new ArrayList<>();
		String index = "";
		String description = "A generic property that can hold any type of value.";
		for (int i = 0; i < this.dates.size(); i++) {
			int p = i % this.header.size() ;
			if (p != 0) {
				Property prop = Properties.INSTANCE.singleValueProperty(
						this.header.get(p),
						this.header.get(p),
						description,
						"value",
						this.getPropertyValue(this.dates.get(i))
				);
				tmp.add(prop);
			}else{
				index= this.dates.get(i);
			}
			if(p+1 == this.header.size()) {
				this.properties.put(index, tmp.stream().toList());
				tmp.clear();
			}
		}
	}

	//Metodo che in base al dato restituisce una proprietà adeguata
	private PropertyValue getPropertyValue(String date) {
		if(identifyDataType(date).equals("Float"))
			return new PropertyValue.FloatPropertyValue(Float.parseFloat(date));
		else if(identifyDataType(date).equals("Integer"))
			return new PropertyValue.IntPropertyValue(Integer.parseInt(date));
		else if (identifyDataType(date).equals("Double"))
			return new PropertyValue.DoublePropertyValue(Double.parseDouble(date));
		else if(identifyDataType(date).equals("Long"))
			return new PropertyValue.LongPropertyValue(Long.parseLong(date));
		else if(identifyDataType(date).equals("Boolean"))
			return new PropertyValue.BooleanPropertyValue(Boolean.parseBoolean(date));
		else if(identifyDataType(date).equals("String"))
			return new PropertyValue.StringPropertyValue(date);
		return null;
	}

	//Metodo che in base al dato in entrata ne identifica il tipo
	private String identifyDataType(String data){
		String input= data.strip();
		try {
			Integer.parseInt(input);
			return "Integer";
		}catch (Exception _){}

		try{
			Long.parseLong(input);
			return "Long";
		}catch (Exception _){}

		try{
			Float.parseFloat(input);
			return "Float";
		}catch (Exception _){}

		try{
			Double.parseDouble(input);
			return "Double";
		}catch (Exception _){}

		if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false"))
			return "Boolean";
		return "String";
	}
}
