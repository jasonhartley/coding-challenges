import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Juggle Fest - A variation of the Stable Marriage Problem
 *
 * Challenge description: https://www.codeeval.com/open_challenges/88/
 *
 * This solution is based on the McVitie-Wilson algorithm,
 * which is based on the Gale-Shipley algorithm.
 */
public class Main {

    /**
     * This is still a work in progress
     * @param args - Will expect one value: the input filename
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<Circuit> circuits = new ArrayList<Circuit>();
        List<Juggler> jugglers = new ArrayList<Juggler>();

        // Open file stream
        InputStream input = new FileInputStream(args[0]);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
        String line, name;
        int h, e, p;

        // Load the input file into memory
        while ((line = buffer.readLine()) != null) {
            String[] elements = line.split("\\s+");
            // If a circuit line
            if (elements[0].equals("C")) {

            }
            // Parse values
            // Store name and hep

            // If a juggler line
            // Parse values
            // Store name, hep, and preferred circuits

        }


    }

    private int dotProduct(Hep hep1, Hep hep2) {
        return hep1.H * hep2.H + hep1.E * hep2.E + hep1.P * hep2.P;
    }

    public class Hep {
        public int H, E, P;

        public Hep(int h, int e, int p) {
            H = h;
            E = e;
            P = p;
        }
    }

    public class Circuit {
        public String name;
        public Hep hep;

        public Circuit(String name, Hep hep) {
            this.name = name;
            this.hep = hep;
        }

    }

    public class Juggler {
        public String name;
        public Hep hep;
        public int[] circuitPrefs;

        public Juggler(String name, Hep hep, int[] circuitPrefs) {
            this.name = name;
            this.hep = hep;
            this.circuitPrefs = circuitPrefs;
        }
    }
}

