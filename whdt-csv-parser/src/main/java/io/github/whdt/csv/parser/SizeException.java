package io.github.whdt.csv.parser;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SizeException extends RuntimeException {
    private final int row;
    private final List<String> header;
    private final String[] values;

    public SizeException(int r, List<String> header,String[] values)
    {
        super("nella riga " + r + " il numero degli elementi non coincide con quello degli header: numero header " + header.size() + "\n numero elementi " + values.length);
        this.row = r;
        this.header = header;
        this.values = values;
        try {
            this.writeToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Errore.txt", false))) {

            writer.println("=== ERRORE SIZE EXCEPTION ===");
            writer.println("Messaggio: " + this.getMessage());
            writer.println();

            // Dati specifici dell'errore
            writer.println("DETTAGLI ERRORE:");
            writer.println("Riga con errore: " + row);
            writer.println("Numero header attesi: " + header.size());
            writer.println("Numero elementi trovati: " + values.length);
            writer.println();

            // Header
            writer.println("HEADER:");
            for (int i = 0; i < header.size(); i++) {
                writer.println("  [" + i + "] " + header.get(i));
            }
            writer.println();

            // Valori
            writer.println("VALORI TROVATI:");
            for (int i = 0; i < values.length; i++) {
                writer.println("  [" + i + "] " + values[i]);
            }
            writer.println("================================");
            writer.println();
        }
    }


}
