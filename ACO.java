import java.io.*;
/**
 *Using ACO to solve TSP
 */
public class ACO {

    Ant []ants; //ant colony
    int antcount;//numOfAnts
    int [][]distance;//distance between two cities
    double [][]tao;//pheromoen
    int citycount;//numOfCities
    int[]besttour;//best path
    int bestlength;//length of best path
    //filename tsp data file
    //antnum numOfAnts
    public void init(String filename,int antnum) throws FileNotFoundException, IOException{
        antcount=antnum;
        ants=new Ant[antcount];
        //reading data from tsp
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader tspdata = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        strbuff = tspdata.readLine();//first line of file, numOfCities
        citycount = Integer.valueOf(strbuff);
        distance = new int[citycount][citycount];
        x = new int[citycount];
        y = new int[citycount];
        for (int citys = 0; citys < citycount; citys++) {
            strbuff = tspdata.readLine();
            String[] strcol = strbuff.split(" ");
            x[citys] = Integer.valueOf(strcol[1]);//each line's second number: x coordinate
            y[citys] = Integer.valueOf(strcol[2]); // y coordinate
        }
        //calculating distances
        for (int city1 = 0; city1 < citycount - 1; city1++) {
            distance[city1][city1] = 0;
            for (int city2 = city1 + 1; city2 < citycount; city2++) {
                distance[city1][city2] = (int) (Math.sqrt((x[city1] - x[city2]) * (x[city1] - x[city2])
                        + (y[city1] - y[city2]) * (y[city1] - y[city2])));
                distance[city2][city1] = distance[city1][city2];//distance A to B is distance B to A
            }
        }
        distance[citycount - 1][citycount - 1] = 0;
        //initialize pheromone
        tao=new double[citycount][citycount];
        for(int i=0;i<citycount;i++)
        {
            for(int j=0;j<citycount;j++){
                tao[i][j]=0.1;
            }
        }
        bestlength=Integer.MAX_VALUE;
        besttour=new int[citycount+1];
        //Randomly assign ants in cities
        for(int i=0;i<antcount;i++){
            ants[i]=new Ant();
            ants[i].RandomSelectCity(citycount);
        }
    }
    //maxgen max iteration of ACO
    public void run(int maxgen){
        for(int runtimes=0;runtimes<maxgen;runtimes++){
            //for each iteration, all ants update and let them travel
            //System.out.print("no>>>"+runtimes);
            //movement of each ant
            for(int i=0;i<antcount;i++){
                for(int j=1;j<citycount;j++){
                    ants[i].SelectNextCity(j,tao,distance);//visiting plans of each ant
                }
                //calculating length of each ant travelled
                ants[i].CalTourLength(distance);
                if(ants[i].tourlength<bestlength){
                    //save best path
                    bestlength=ants[i].tourlength;
                    //print iff new bestlength has been found
                    System.out.println(runtimes+"th iteration£¬found new best length£º"+bestlength);
                    for(int j=0;j<citycount+1;j++)
                        besttour[j]=ants[i].tour[j];//update path
                }
            }
            //update pheromoen
            UpdateTao();
            //reset ant randomly
            for(int i=0;i<antcount;i++){
                ants[i].RandomSelectCity(citycount);
            }
        }
       }
    /**
     * update pheromoen
     */
    private void UpdateTao(){
        double rou=0.5;
        //pheromoen volatilizing
        for(int i=0;i<citycount;i++)
            for(int j=0;j<citycount;j++)
                tao[i][j]=tao[i][j]*(1-rou);
        //updating pheromoen
        for(int i=0;i<antcount;i++){
            for(int j=0;j<citycount;j++){
                tao[ants[i].tour[j]][ants[i].tour[j+1]]+=1.0/ants[i].tourlength;
            }
        }
    }
    /* printing result
     */
    public void ReportResult(){
        System.out.println("length of best length: "+bestlength);
        System.out.println("Printing best path£º");
        for(int j=0;j<citycount+1;j++)
            System.out.print( besttour[j]+">>");//printing best path
    }
}