package traning;

public interface TraningManagment {
    /**
     * Method responisble for loading traning units from the source provided as a path. 
     * @param pathToTraningFile string object representing the path to the source of traning data
     * @return method returns true if the process of loading traning was successfull, in any other case method returns false 
     */
    public boolean loadTraning(String pathToTraningFile);

    public void procesTraining();  
}