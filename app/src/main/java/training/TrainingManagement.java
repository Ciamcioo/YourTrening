package training;

/**
 * TrainingManagement interface defining the functionality of trainings.
 */
public interface TrainingManagement {
    /**
     * Method responsible for loading training units from the source provided as a path. 
     * @param pathToTrainingFile string object representing the path to the source of training data
     * @return method returns true if the process of loading training was successful, in any other case method returns false 
     */
    public String loadTraining(String pathToTrainingFile);

    /**
     * Method processing training. Training is processed by using data contained in training object.  
     */
    public void processTraining();  

    
    /**
     * Method checks if training was loaded by checking correctness of its data. Auxiliary method protecting the processTraining method before unexpected before which would happen if training wouldn't be loaded.  
     * @return method returns true if training is loaded to the memory. In other case method returns false.
     */
    public boolean checkIfTrainingIsLoaded();
}
