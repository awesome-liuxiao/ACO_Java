import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//Using ACO to solve TSP problem
//http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/
//Data includes the amount of cities and their coordinates
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ACO aco;
        aco=new ACO();
        try {
            aco.init("att48.txt", 100);//cityInfo file, numOfCities
            aco.run(1000);//numOfIteration
            aco.ReportResult();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}