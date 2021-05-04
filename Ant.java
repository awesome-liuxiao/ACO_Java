import java.util.Random;
/*
 Ant class
 */
public class Ant {
    /**
     * path get by ant
     */
    public int[]tour;//Sequence of visiting cities
    //unvisitedcity 1: visited, 0: unvisited
    int[] unvisitedcity;
    /**
     * length of path get by an ant
     */
    public int tourlength;//total length of an ant passed
    int cities;//numOfCities
/**
 * Assign an ant to a random city
 * initilize ant
 * @param citycount total number of cities
 */
    public void RandomSelectCity(int citycount){
        cities=citycount;
        unvisitedcity=new int[citycount];
        tour=new int[citycount+1];
        tourlength=0;
        for(int i=0;i<citycount;i++){
            tour[i]=-1;
            unvisitedcity[i]=1;
        }//initialize variables

        long r1 = System.currentTimeMillis();//getting current time
        Random rnd=new Random(r1);
        int firstcity=rnd.nextInt(citycount);//randomly choose a city
        unvisitedcity[firstcity]=0;//0 means visited
        tour[0]=firstcity;//mark as the first visited city
    }
    /**
     * choose next city
     * @param index index of selecting city
     * @param tao   pheromone
     * @param distance  distance
     */
    public void SelectNextCity(int index,double[][]tao,int[][]distance){
        double []p;
        p=new double[cities];//possibility of selecting next city
        //coefficients of calculating the possibility
        double alpha=1.0;
        double beta=2.0;
        double sum=0;
        int currentcity=tour[index-1];//current city where ant is at
        //calculating denominator
        for(int i=0;i<cities;i++){
            if(unvisitedcity[i]==1)//not visited
                sum+=(Math.pow(tao[currentcity][i], alpha)*
                        Math.pow(1.0/distance[currentcity][i], beta));
        }
        //calculating the possibilities of each city being selected
        for(int i=0;i<cities;i++){
            if(unvisitedcity[i]==0)
                p[i]=0.0;//possibility is 0 if visited
            else{
                //calculating possibility if not visited
                p[i]=(Math.pow(tao[currentcity][i], alpha)*
                        Math.pow(1.0/distance[currentcity][i], beta))/sum;
            }
        }
        long r1 = System.currentTimeMillis();
        Random rnd=new Random(r1);
        double selectp=rnd.nextDouble();
        //select a city by random number
        double sumselect=0;
        int selectcity=-1;
        //selecting cities until the possibilities larger than the random number
        for(int i=0;i<cities;i++){
            sumselect+=p[i];
            if(sumselect>=selectp){
                selectcity=i;
                break;
            }
        }
        if (selectcity==-1)// a city not visited
            System.out.println();
        tour[index]=selectcity;
        unvisitedcity[selectcity]=0;
    }
    /**
     * calculating the total length of an ant get
     * @param distance  global
     */
    public void CalTourLength(int [][]distance){
        tourlength=0;
        tour[cities]=tour[0];//the first city is the last city
        for(int i=0;i<cities;i++){
            tourlength+=distance[tour[i]][tour[i+1]];//length of from A, visiting each city once, and returned to A
        }
    }
}