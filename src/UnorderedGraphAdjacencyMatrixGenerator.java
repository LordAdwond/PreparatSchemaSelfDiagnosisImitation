import javax.management.InvalidAttributeValueException;
import java.util.*;

public class UnorderedGraphAdjacencyMatrixGenerator {
    int MaxN; // max range of matrices
    int MatricesNumber; // a number of matrices, what will be generated

    public UnorderedGraphAdjacencyMatrixGenerator(int range, int matricesNum) throws InvalidAttributeValueException
    {
        if(range<2)
        {
            throw new InvalidAttributeValueException("Range (a number of module) must be not less than 2");
        }
        if(matricesNum<1)
        {
            throw new InvalidAttributeValueException("A number of adjacency matrices must be equal at least 1");
        }
        this.MaxN = range;
        this.MatricesNumber = matricesNum;
    }

    public int getRange()
    {
        return this.MaxN;
    }

    public void setRange(int newRange)
    {
        this.MaxN = newRange;
    }

    public int getMatricesNumber()
    {
        return this.MatricesNumber;
    }

    public void setMatricesNumber(int newMatricesNumber)
    {
        this.MatricesNumber = newMatricesNumber;
    }

    public Set<int[][]> generate()
    {
        Set<int[][]> matricesSet = new HashSet<int[][]>();
        int i, j, n;
        Random random = new Random();

        while (matricesSet.size() <= this.MatricesNumber)
        {
            n = random.nextInt(this.MaxN-2)+2;
            int[][] matrix = new int[n][n];

            for(i=0; i<n; i++)
            {
                matrix[i][i] = 0;
            }

            for(i=1; i<n; i++)
            {
                for(j=0; j<i; j++)
                {
                    matrix[i][j] = random.nextInt(2);
                    matrix[j][i] = matrix[i][j];
                }
            }

            if(isConnectedGraph(matrix))
            {
                matricesSet.add(matrix.clone());
            }
        }

        return matricesSet;
    }

    private boolean isConnectedGraph(int[][] A)
    {
        int n = A.length, S;
        int i, j;

        for(i=0; i<n; i++)
        {
            S = 0;
            for(j=0; j<n; j++)
            {
                S += A[i][j];
            }
            if(S==0)
            {
                return false;
            }
        }

        return true;
    }
}
