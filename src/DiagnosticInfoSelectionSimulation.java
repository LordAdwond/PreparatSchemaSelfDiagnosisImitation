import javax.management.InvalidAttributeValueException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DiagnosticInfoSelectionSimulation {
    private class PairChecksSimulation {
        int[][] diagnosticGraph; // adjacency matrix of diagnostic graph
        List<List<Integer>> modulesPairsElementaryChecks = new ArrayList<>(); // in every element of the list
                                                                              // first element is a work of machine, what make checks (1 if working and 0 in opposite case)
                                                                              // second element is a work of machine, what is checked (1 if working and 0 in opposite case)
                                                                              // next elements are results of checks by Preparata schema
        private final Random random = new Random();

        public PairChecksSimulation(int[][] graph) throws InvalidAttributeValueException {
            if(graph.length==0)
            {
                throw new InvalidAttributeValueException("Empty list of diagnostic graphs");
            }

            this.diagnosticGraph = graph.clone();
        }

        public void setDiagnosticGraph(int[][] diagnosticGraph) {
            this.diagnosticGraph = diagnosticGraph;
        }

        public int[][] getDiagnosticGraph() {
            return diagnosticGraph;
        }

        public void makeSimulation(int minChecks, int maxChecks)
        {
            int i, j, k;
            int n = this.diagnosticGraph.length;
            int viWorks, vjWorks;
            int ChecksNumber = random.nextInt(maxChecks-minChecks+1)+minChecks;
            List<Integer> elementaryChecks = new ArrayList<>();
            double noise = 0; // it's needed for simulate probability of error (in transmission, interpreting etc.)

            for(i=0; i<n; i++)
            {
                for(j=0; j<n; j++)
                {
                    if(this.diagnosticGraph[i][j]==0)
                    {
                        continue;
                    }
                    else
                    {
                        viWorks = random.nextInt(2);
                        vjWorks = random.nextInt(2);
                        if(viWorks==1)
                        {
                            noise = 10*Math.random();
                            if (vjWorks==1)
                            {
                                for(k=1; k<ChecksNumber; k++)
                                {
                                    elementaryChecks.add(noise>0.01 ? 0 : 1);
                                }
                            }
                            else
                            {
                                for(k=1; k<ChecksNumber; k++)
                                {
                                    elementaryChecks.add(noise>0.01 ? 1 : 0);
                                }
                            }
                        }
                        else
                        {
                            for(k=1; k<ChecksNumber; k++)
                            {
                                elementaryChecks.add(random.nextInt(2));
                            }
                        }
                        modulesPairsElementaryChecks.add(elementaryChecks);
                    }
                }
            }
        }

        public List<List<Integer>> getSimulationResult()
        {
            return this.modulesPairsElementaryChecks;
        }
    }
    List<int[][]> diagnosticGraphs = new ArrayList<>();
    int minElementaryChecksNum = 0, maxElementaryChecksNum = 0;

    public DiagnosticInfoSelectionSimulation(List<int[][]> graphs, int minChecksNum, int maxChecksNum) throws InvalidAttributeValueException {
        if(graphs.isEmpty())
        {
            throw new InvalidAttributeValueException("Empty list of diagnostic graphs");
        }
        if(minChecksNum<1)
        {
            throw new InvalidAttributeValueException("Min number of elementary checks must be at least 1 (recommended to enter 4 or greater)");
        }
        if(maxChecksNum<minChecksNum)
        {
            throw new InvalidAttributeValueException("Max number of elementary checks can't be less than its min number");
        }

        this.diagnosticGraphs = graphs;
        this.minElementaryChecksNum = minChecksNum;
        this.maxElementaryChecksNum = maxChecksNum;
    }

    public List<List<Integer>> makeSimulation() throws InvalidAttributeValueException {
        List<List<Integer>> checksSeriesList = new ArrayList<>();
        PairChecksSimulation simulation;

        for(var A : diagnosticGraphs)
        {
            simulation = new PairChecksSimulation(A);
            simulation.makeSimulation(this.minElementaryChecksNum, this.maxElementaryChecksNum);
            checksSeriesList.addAll(simulation.getSimulationResult());
        }

        return checksSeriesList;
    }

    public void makeSimulationAndSaveToJSON() throws InvalidAttributeValueException, IOException {
        List<List<Integer>> checksSeriesList = new ArrayList<>();
        PairChecksSimulation simulation;
        StringBuilder sb = new StringBuilder();
        FileWriter output = new FileWriter("data.json");
        int i, j;

        for(var A : diagnosticGraphs)
        {
            simulation = new PairChecksSimulation(A);
            simulation.makeSimulation(this.minElementaryChecksNum, this.maxElementaryChecksNum);
            checksSeriesList.addAll(simulation.getSimulationResult());
        }

        sb.append("[");
        for (i = 0; i < checksSeriesList.size(); i++) {
            List<Integer> innerList = checksSeriesList.get(i);
            sb.append("[");

            for (j = 0; j < innerList.size(); j++) {
                sb.append(innerList.get(j));
                if (j < innerList.size() - 1) {
                    sb.append(", ");
                }
            }

            sb.append("]");
            if (i < checksSeriesList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        output.append(sb);
        output.close();
    }
}
