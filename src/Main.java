import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvalidAttributeValueException, IOException {
        int maxModulesNumber = 0, GraphsNumber=0, minNumOfRDG = 0, maxNumOfRDG = 0;
        Scanner scanner = new Scanner(System.in);
        String endString = "";

        System.out.print("Enter max number of modules: ");
        maxModulesNumber = scanner.nextInt();
        System.out.print("Enter number of graphs: ");
        GraphsNumber = scanner.nextInt();
        System.out.print("Min number of generated random diagnosis graphs for topology: ");
        minNumOfRDG = scanner.nextInt();
        System.out.print("Max number of generated random diagnosis graphs for topology: ");
        maxNumOfRDG = scanner.nextInt();

        TopologyGraphToRandomDiagnosisGraphTransformer transformer = new TopologyGraphToRandomDiagnosisGraphTransformer(minNumOfRDG, maxNumOfRDG);
        UnorderedGraphAdjacencyMatrixGenerator generator = new UnorderedGraphAdjacencyMatrixGenerator(maxModulesNumber, GraphsNumber);
        List<int[][]> Ds = transformer.transform(generator.generate());

        DiagnosticInfoSelectionSimulation simulation = new DiagnosticInfoSelectionSimulation(Ds);
        simulation.makeSimulationAndSaveToJSON();
        System.out.println("All results of simulation saved on 'data.json'");
        System.out.println("Please, enter something and click 'Enter' to quit");
        endString = scanner.next();
    }
}