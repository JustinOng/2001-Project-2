import java.io.*;
import java.util.*;

public class Project2 {
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
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
		System.out.println(Runtime.getRuntime().totalMemory());

		fr = new FileReader(new File(hospitalPath));
		br = new BufferedReader(fr);

		while ((line = br.readLine()) != null) {
			if (line.startsWith("#"))
				continue;

			search.setHospital(Integer.parseInt(line));
		}

		br.close();
		fr.close();

		long start = System.currentTimeMillis();
		search.search();
		System.out.println((System.currentTimeMillis() - start) / 1000.0);

		Map<Integer, Integer> counts = new HashMap<>();
		for (Vertex v : search.getVertexes()) {
			counts.merge(v.getVisits(), 1, Integer::sum);
			System.out.println(v);
		}

		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			System.out.printf("%d visits: %d\n", entry.getKey(), entry.getValue());
		}
	}
}
