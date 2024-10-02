package traning;

/**
 * TraingManagment interface definig the functionality of trenings.
 */
public interface TraningManagment {
    /**
     * Method responisble for loading traning units from the source provided as a path. 
     * @param pathToTraningFile string object representing the path to the source of traning data
     * @return method returns true if the process of loading traning was successfull, in any other case method returns false 
     */
    public String loadTraning(String pathToTraningFile);

    /**
     * Method processing training. Training is processed by using data contained in training object.  
     */
    public void procesTraining();  

    
    /**
     * Method checks if training was loaded by checking correctnes of its data. Auxiliary method protecting the procesTraining method before unexpected before which would happen if training wouldn't be loaded.  
     * @return method returns true if traning is loaded to the memory. In other case method returns false.
     */
    public boolean checkIfTraningIsLoaded();
}
