import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import java.io.*;
import java.util.*;

public class Project2 {
	public static void main(String[] args) throws Exception {
		if (args.length == 0)
			return;

		String edgePath = args[0];
		String hospitalPath = args[1];

		FileReader fr = new FileReader(new File(edgePath));
		BufferedReader br = new BufferedReader(fr);

		SearchAlgorithm search = new SearchAlgorithm();

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
		}
		
		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			System.out.printf("%d visits: %d\n", entry.getKey(), entry.getValue());
		}
	}
}
