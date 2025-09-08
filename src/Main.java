import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvalidAttributeValueException, IOException {
        int maxModulesNumber = 0, GraphsNumber=0, minNumOfRDG = 0, maxNumOfRDG = 0;
        int minNumOfChecks = 0, maxNumOfChecks = 0;
        Scanner scanner = new Scanner(System.in);
        int endVal = 1;

        for(;;)
        {
            try
            {
                System.out.print("Enter max number of modules: ");
                maxModulesNumber = scanner.nextInt();
                System.out.print("Enter number of graphs: ");
                GraphsNumber = scanner.nextInt();
                System.out.print("Min number of generated random diagnosis graphs for topology: ");
                minNumOfRDG = scanner.nextInt();
                System.out.print("Max number of generated random diagnosis graphs for topology: ");
                maxNumOfRDG = scanner.nextInt();
                System.out.print("Min number of elementary checks in pair (vi, vj): ");
                minNumOfChecks = scanner.nextInt();
                System.out.print("Max number of elementary checks in pair (vi, vj): ");
                maxNumOfChecks = scanner.nextInt();

                TopologyGraphToRandomDiagnosisGraphTransformer transformer = new TopologyGraphToRandomDiagnosisGraphTransformer(minNumOfRDG, maxNumOfRDG);
                UnorderedGraphAdjacencyMatrixGenerator generator = new UnorderedGraphAdjacencyMatrixGenerator(maxModulesNumber, GraphsNumber);
                List<int[][]> Ds = transformer.transform(generator.generate());

                DiagnosticInfoSelectionSimulation simulation = new DiagnosticInfoSelectionSimulation(Ds, minNumOfChecks, maxNumOfChecks);
                simulation.makeSimulationAndSaveToJSON();
                System.out.println("All results of simulation saved on 'data.json'");
                System.out.println("Please, enter 0 to quit and 1 in opposite case");
                endVal = scanner.nextInt();
                if(endVal==0)
                {
                    // break;
                    System.exit(0);
                }
            } catch (Exception e)
            {
                // throw new RuntimeException(e);
                System.out.println('\n'+e.getMessage());
                System.out.println("\nPlease, try enter parameters correctly");
            }
        }
    }
}