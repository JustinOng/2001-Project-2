import java.io.*;

public class Project2 {
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: app <path to edges> <path to hospitals> <num of nearest paths>");
			return;
		}

		String edgePath = args[0];
		String hospitalPath = args[1];
		int numPaths = Integer.parseInt(args[2]);
		
		int iterationCount = 1;
		String outPath = null;
		
		for (int i = 3; i < args.length; i++) {
			String s = args[i];
			if (s.startsWith("-i") || s.startsWith("--iterations")) {
				if ((i+1) >= args.length || args[i+1].startsWith("-")) {
					System.out.println("Expected value after -i/--iterations");
					return;
				}
				
				iterationCount = Integer.parseInt(args[i+1]);
				i++;
			} else if (s.startsWith("-o") || s.startsWith("--out")) {
				if ((i+1) >= args.length || args[i+1].startsWith("-")) {
					System.out.println("Expected value after -o/--out");
					return;
				}
				
				outPath = args[i+1];
			}
		}

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
		
			
		if (outPath != null) {
			System.out.printf("Writing output to %s\n", outPath);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
			
			for (Vertex v : search.getVertexes()) {
			    writer.write(v.toString());
			    writer.write('\n');
			}
			
		    writer.close();
		}
	}
}
