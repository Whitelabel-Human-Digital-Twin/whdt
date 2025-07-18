package io.github.lm98.whdt.csv.parser;

import io.github.lm98.whdt.core.hdt.model.property.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class ParserCSV {

	private final Pattern PATTERN = Pattern.compile("\\d+,\\d{1,15}");

	private static ParserCSV parserCSV;
    private List<String> dates = new ArrayList<>();
    private List<String> header = new ArrayList<>();
	private Map<Integer,List<GenericProperty>> properties = new HashMap<>();

	public static ParserCSV createParserCSV(){
		if(parserCSV == null)
			parserCSV = new ParserCSV();
		return parserCSV;
	}
	
	private ParserCSV() {}

	public  Map<Integer,List<GenericProperty>> Parsing(String nomeFile) {
		leggiFile(nomeFile);
		createProperties();
		return properties;
	}

	private String covertRow(String riga) {
		String tmp = PATTERN.matcher(riga).replaceAll(match -> {
			return match.group().replace(",", "#!@");
		});

		tmp = tmp.replace(",", ";");

		tmp = tmp.replace(".", ",");

		tmp = tmp.replace("\"", "");

		tmp = tmp.replace("#!@", ".");

		return tmp;
	}

	private void leggiFile(String nomeFile) {
		try {
			String tmp;
			BufferedReader reader = Files.newBufferedReader(Paths.get(nomeFile));
			header = Arrays.asList(reader.readLine().replace(",", ";").split(";"));
			while ( (tmp = reader.readLine()) != null) {
				if(covertRow(tmp).split(";").length == header.size())
                    dates.addAll(Arrays.asList(covertRow(tmp).split(";")));
				else
					throw new sizeException("il numero degli elementi non coincide con quello degli header");
			}
			System.out.println(header.size());
			header.forEach(dati -> System.out.println("testa = "+dati));
			System.out.println(dates.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createProperties() {
		List<GenericProperty> tmp = new ArrayList<>();
		int index = 1;
		for (int i = 0; i < dates.size(); i++) {
			int p = i % header.size() ;
			tmp.add(new GenericProperty(header.get(p), header.get(p), "A generic property that can hold any type of value.", Integer.toString(p+1), this.getPropertyValue(dates.get(i))));
			if(p+1 == header.size()) {
				properties.put(index, tmp.stream().toList());
				tmp.clear();
				index++;
			}
		}
	}

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
			throw new DataTypeException("Tipo di dato non riconnosciuto dal sistema");
	}

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

		try {
	        Double.parseDouble(input);
	        return "Double";
	    }catch (Exception _){}

		if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
			return "Boolean";
		}
		
		return "String";
	}

	public void toSting(){
		this.header.forEach(h -> System.out.println("header = "+h));
		this.dates.forEach(d -> System.out.println("dates = "+d));
		this.properties.forEach((p,g) -> g.forEach( gp -> System.out.println("Paziente numero "+ p +" : "+gp.toString())));
	}
}
