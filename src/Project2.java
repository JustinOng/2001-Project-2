import java.io.*;
import java.util.*;

public class Project2 {
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: app <path to edges> <path to hospitals> <num of nearest paths>");
			return;
		}

		String edgePath = args[0];
		String hospitalPath = args[1];
		int numPaths = Integer.parseInt(args[2]);

		FileReader fr = new FileReader(new File(edgePath));
		BufferedReader br = new BufferedReader(fr);

		SearchAlgorithm search = new SearchAlgorithm(numPaths);

		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("#"))
				continue;

			String[] data = line.split("	");
			search.createFromEdge(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
		}

		br.close();
		fr.close();
		System.out.printf("Memory Usage: %.2fGB\n", Runtime.getRuntime().totalMemory() / Math.pow(2, 30));

		fr = new FileReader(new File(hospitalPath));
		br = new BufferedReader(fr);

		while ((line = br.readLine()) != null) {
			if (line.startsWith("#"))
				continue;

			search.setHospital(Integer.parseInt(line));
		}

		br.close();
		fr.close();

		long start = System.nanoTime();
		search.search();
		System.out.printf("Took %dns to search\n", (System.nanoTime() - start));
		
		if (args.length >= 4) {
			String outPath = args[3];
			BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
			
			for (Vertex v : search.getVertexes()) {
			    writer.write(v.toString());
			    writer.write('\n');
			}
			
		    writer.close();
		}
	}
}
