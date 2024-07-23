package traning;

import java.util.*;

class Exercise {
    private static final String DEFAULT_NAME = "None exercise name", DEFAULT_DESCRIPTION = "Lack of description";
    private static final long DEFAULT_EXECUTION_TIME = 45;
    private static final byte EXERCISE_INFORMATION_IN_LINE = 3;

    private String name;
    private long executionTime;
    private String description;

    /**
     * Constructor creates the exercise object based on the string object passed as an argument which describes object  
     * @param exercisesDescription line describing the object variables 
     */
    protected Exercise(String exercisesDescription) {
        List<String> exerciseData = new ArrayList<String>(Arrays.asList(exercisesDescription.split(",")));
        if (exerciseData.size() != EXERCISE_INFORMATION_IN_LINE) 
            lineExerciseInformationSizeDifference(exerciseData);
        setName(exerciseData.get(0));
        setExecutionTime(Long.parseLong(exerciseData.get(1)));
        setDescription(exerciseData.get(2));
    }

    /**
     * Function validates number of information contained in line, in case of missing infomration list is filled with empty records.   
     * @param exerciseData list containing the single line representing the one exercise 
     * @return integer representing the amount of missing data in the line representing exercise
     */
    private int lineExerciseInformationSizeDifference(List<String> exerciseData) {
        int sizeDifference = EXERCISE_INFORMATION_IN_LINE - exerciseData.size();  
        for (int i = 0; i < sizeDifference; i++)
            exerciseData.add("");
        return sizeDifference;
    }

// GETTERS
    protected String getName() {
        return name;
    }
    protected long getExecutionTime() {
        return executionTime;
    } 
    protected String getDescription() {
        return description;
    }

// SETTERS
    /**
     * Name variable exercise setter for exerciese object. If the vlaue passed as an argument is not empty the exercise's name is set.
     * @param name arguemnt containing exercise name provided by the user 
     */
    protected void setName(String name) {
        if (!name.equals(""))
            this.name = name;
        else
            this.name = DEFAULT_NAME;
    }

    /**
     * Execution time variable setter for exerciese object. If the argument is within the range of zero to Integer max then the execution time is set.  
     * @param executionTime arguemnt containing execution time provided by the user 
     */
    protected void setExecutionTime(long executionTime) {
        if (executionTime < Integer.MAX_VALUE && executionTime > 0)
            this.executionTime = executionTime;
        else 
            this.executionTime = DEFAULT_EXECUTION_TIME;
    }

    /**
     * Exercise description setter for exercisese object. If the arguemnt is empty, the description will be set to default value. In other case the description is set to the value of argument.  
     * @param description argument describing the exercise provided as an arguemnt
     */
    protected void setDescription(String description) {
        if (!description.equals("") || description == null)  
            this.description = description;
        else
            this.description = DEFAULT_DESCRIPTION;
        
    }
}