import java.io.*;
import java.util.*;

public class Project2 {
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: app <path to edges> <path to hospitals> <num of nearest paths>");
			return;
		}
		
		int iterationCount = 1;
		for (String s : args) {
			if (s.startsWith("-i")) {
				iterationCount = Integer.parseInt(s.substring(2));
			}
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

		long totalExecTime = 0;
		int executionCount = 0;
		
		long start, execTime;
		
		for (int i = 0; i < iterationCount; i++) {
			start = System.nanoTime();
			search.search();
			execTime = System.nanoTime() - start;
			totalExecTime += execTime;
			executionCount++;
			
			// attempt to perform some cleanup
			search.reset();
			System.gc();
			System.runFinalization();
		}
		
		System.out.printf("Took %dns on average to exec %d iterations\n", totalExecTime / executionCount, executionCount);
		
		if (args.length >= 4) {
			String outPath = null;
			for (int i = 3; i < args.length; i++) {
				if (args[i].startsWith("-i")) continue;
				
				outPath = args[3];
			}
			
			if (outPath != null) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
				
				for (Vertex v : search.getVertexes()) {
				    writer.write(v.toString());
				    writer.write('\n');
				}
				
			    writer.close();
			}
		}
	}
}
