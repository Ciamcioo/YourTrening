package traning;

import java.util.*;

class Exercise {
    private static final String DEFAULT_NAME = "None exercise name", DEFAULT_DESCRIPTION = "Lack of description";
    private static final long DEFAULT_EXECUTION_TIME = 45;
    private static final byte EXERCISE_INFORMATION_IN_LINE = 3;

    private String name;
    private long executionTime;
    private String description;

    protected Exercise(String exercisesDescription) {
        List<String> exerciseData = new ArrayList<String>(Arrays.asList(exercisesDescription.split(",")));
        if (exerciseData.size() != EXERCISE_INFORMATION_IN_LINE) 
            fillExercisesInformationSizeDifference(exerciseData);
        setName(exerciseData.get(0));
        setExecutionTime(Long.parseLong(exerciseData.get(1)));
        setDescription(exerciseData.get(2));
    }

    protected String getName() {
        return name;
    }
    protected long getExecutionTime() {
        return executionTime;
    } 
    protected String getDescription() {
        return description;
    }

    protected void setName(String name) {
        if (!name.equals(""))
            this.name = name;
        else
            this.name = DEFAULT_NAME;
    }
    protected void setExecutionTime(long executionTime) {
        if (executionTime < Integer.MAX_VALUE && executionTime > 0)
            this.executionTime = executionTime;
        else 
            this.executionTime = DEFAULT_EXECUTION_TIME;
    }
    protected void setDescription(String description) {
        if (!description.equals("")) 
            this.description = description;
        else
            this.description = DEFAULT_DESCRIPTION;
        
    }

    private int fillExercisesInformationSizeDifference(List<String> exerciseData) {
        int sizeDifference = EXERCISE_INFORMATION_IN_LINE - exerciseData.size();  
        for (int i = 0; i < sizeDifference; i++)
            exerciseData.add("");
        return sizeDifference;
    }
}