import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import java.io.*;

public class Project2 {
	public static void main(String[] args) throws Exception {
		if (args.length == 0)
			return;

		String path = args[0];

		FileReader fr = new FileReader(new File(path));
		BufferedReader br = new BufferedReader(fr);

//		Graph graph = new MultiGraph(path);
//		graph.setStrict(false);
//		graph.setAutoCreate(true);
		SearchAlgorithm search = new SearchAlgorithm();

		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("#"))
				continue;
			
			String[] data = line.split("	");
//			graph.addEdge(data[0] + data[1], data[0], data[1]);
			search.createFromEdge(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
		}
		System.out.println(Runtime.getRuntime().totalMemory());
		
		search.assignHospitalsRandomly();
		
		long start = System.currentTimeMillis();
		search.search();
		System.out.println((System.currentTimeMillis() - start) / 1000.0);
	}
}
