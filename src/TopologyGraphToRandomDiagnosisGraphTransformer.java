import javax.management.InvalidAttributeValueException;
import java.util.*;

public class TopologyGraphToRandomDiagnosisGraphTransformer {
    int MinGraphs; // min number of generated random diagnosis graphs
    int MaxGraphs; // max number of generated random diagnosis graphs

    public TopologyGraphToRandomDiagnosisGraphTransformer(int minGraphsNum, int maxGraphsNum) throws InvalidAttributeValueException
    {
        if(minGraphsNum<1)
        {
            throw new InvalidAttributeValueException("A min number of generated diagnosis graphs must be equal at least 1");
        }
        if(minGraphsNum>maxGraphsNum)
        {
            throw new InvalidAttributeValueException("A minimal number of generated diagnosis graphs must be not less than a max number of generated diagnosis graphs");
        }

        this.MinGraphs = minGraphsNum;
        this.MaxGraphs = maxGraphsNum;
    }

    public int getMinGraphs() {
        return this.MinGraphs;
    }

    public void setMinGraphs(int minGraphs) {
        this.MinGraphs = minGraphs;
    }

    public int getMaxGraphs() {
        return this.MaxGraphs;
    }

    public void setMaxGraphs(int maxGraphs) {
        this.MaxGraphs = maxGraphs;
    }

    public List<int[][]> transform(Set<int[][]> adjacencyMatrices)
    {
        /*
        if(adjacencyMatrices.size()<1)
        {
            throw new
        }*/
        List<int[][]> diagnosisMatrices = new ArrayList<int[][]>();
        // int i, j, k, n, randDiagnosisMatricesNumber, toCheck;
        Random random = new Random();

        adjacencyMatrices.parallelStream().forEach(A ->{
            int i, j, k, n, randDiagnosisMatricesNumber, toCheck;
            n = A.length;
            int[][] D = new int[n][n];
            randDiagnosisMatricesNumber = random.nextInt(this.MaxGraphs - this.MinGraphs + 1) + this.MinGraphs;
            for(k=0; k<randDiagnosisMatricesNumber; k++)
            {
                D = A.clone();
                for(i=0; i<n; i++)
                {
                    for(j=0; j<n; j++)
                    {
                        if(i!=j && A[i][j]==1)
                        {
                            toCheck = random.nextInt(2);
                            if (toCheck==1)
                            {
                                D[i][j] = 1;
                            }
                            else
                            {
                                D[i][j] = 0;
                            }
                        }
                    }
                }
            }
            if(isEveryModuleChecked(D))
            {
                diagnosisMatrices.add(D);
            }
        });

        return diagnosisMatrices;
    }

    private boolean isEveryModuleChecked(int[][] D)
    {
        int n = D.length, S=0;
        int i, j;

        for(j=0; j<n; j++)
        {
            S = 0;
            for(i=0; i<n; i++)
            {
                S += 1;
            }
            if(S==0)
            {
                return false;
            }
        }

        return true;
    }
}
