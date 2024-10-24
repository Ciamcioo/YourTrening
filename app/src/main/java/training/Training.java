package training;

import java.util.*;
import yourtraining.TerminalApp;
import java.io.*;
import java.nio.file.*;

/**
 * Training class representing the training unit. Handling the functionality of training: loading, saving and processing. 
 */
public class Training implements TrainingManagement{
    private static final String INCORRECT_INPUT_TEMPLATE = "%s has been set to %d, because the value read from file was inappropriate or too big.\n", 
                                CORRECT_INPUT_VALUE = "",
                                EXTRACT_TRAINING_ERROR_MESSAGE = "During extracting the data from a file ocurred an error, operation couldn't been finished\n",  
                                LOADING_FAIL = "TRAINING LOADING FAILED\n",
                                LOADING_SUCCESS = "TRAINING LOADING SUCCESSFUL\n",
                                PROCESSING_TRAINING_ERROR_MESSAGE = "Error during processing training",
                                VALID_TRAINING_SYNTAX = "_training.txt";
    private static final byte DEFAULT_SERIES_NUMBER = 1, DEFAULT_EXERCISES_NUMBER = 1;
    private static final long DEFAULT_SERIES_REST_TIME = 60;
    private static final int DEFAULT_EXERCISES_REST_TIME = 15;

    private String variableName;
    private byte series; 
    private long restTimeBetweenSets; 
    private byte exercisesNumber;
    private int restTimeBetweenExercises; 
    private List<Exercise> exercises = null;   

    /**
     * Default constructor handling the initialization of object to avoid any security risks. 
     */
    public Training() {
        this.series = DEFAULT_SERIES_NUMBER;
        this.restTimeBetweenSets = DEFAULT_SERIES_REST_TIME;
        this.exercisesNumber = DEFAULT_EXERCISES_NUMBER;
        this.restTimeBetweenExercises = DEFAULT_EXERCISES_REST_TIME;
    }

    @Override
    public String loadTraining(String pathStr) {
        boolean isTrainingFile = validateIfPathContainsTrainingSyntax(pathStr);
        Path path = generatePath(pathStr);
        if (path == null || !isTrainingFile) 
            return LOADING_FAIL;
        FileInputStream trainingSchedule = openTrainingFileStream(path);
        String extractionResult = extractTraining(trainingSchedule);
       if (extractionResult == null)
            return EXTRACT_TRAINING_ERROR_MESSAGE + LOADING_FAIL;
        else if(!extractionResult.equals(""))
            return extractionResult + LOADING_SUCCESS;
        return LOADING_SUCCESS;
    }

    @Override
    public void processTraining() {
        if (!checkIfTrainingIsLoaded())  
            return;
        System.out.println("TRAINING STARTED");
        System.out.println("Training will start in 15 seconds...");
        timeCountersTerminator(timeCounter(10));
        timeCountersTerminator(printingTimeCounter(5)); 
        TerminalApp.clearTerminal();
        for (int series_iteration = 1; series_iteration <= series; series_iteration++) {
            System.out.println("Series number: " + series_iteration);
            for (Exercise exercise : exercises) {
                System.out.println(exercise.getName()); 
                System.out.println(exercise.getDescription());
                timeCountersTerminator(timeCounter(5));
                System.out.println("Exercise " + exercise.getName() + " starts");
                timeCountersTerminator(printingTimeCounter(exercise.getExecutionTime()));
                TerminalApp.clearTerminal();
                System.out.println("Exercise " + exercise.getName() + " ends. Get rest");
                timeCountersTerminator(printingTimeCounter(restTimeBetweenExercises));
                TerminalApp.clearTerminal();
            }
            System.out.println("Series ends. Get rest.");
            timeCountersTerminator(printingTimeCounter(restTimeBetweenSets));
            TerminalApp.clearTerminal();
        }
        System.out.println("TRAINING ENDED");
    }

    @Override
    public boolean checkIfTrainingIsLoaded() {
        if (series <= 0 || restTimeBetweenExercises <= 0 || restTimeBetweenSets <= 0 || exercisesNumber <= 0)
            return false;
        if (exercises == null)
            return false;
        if (exercisesNumber != exercises.size())
            return false;
        return true;
    }

    /**
     * Method tries to open new stream to the file which path is provided as an argument.
     * If the operation is unsuccessful method returns null pointer.  
     * @param path path to the file provided in String object 
     * @return in case of success reference to newly created stream, in other case null pointer 
     */
    private FileInputStream openTrainingFileStream(Path trainingPath) {
        try {
            return new FileInputStream(trainingPath.toFile());
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Method extracts data provided from file stream. Based on the results of individual extractions whole extraction result 
     * String objet is build which provide information about obstacles in extraction of data from the stream. 
     * @param fis file input stream provided as an argument from which data will be extracted
     * @return String object containing information about unsuccessful stages of fetching data
     */
    private String extractTraining(FileInputStream fis) {
        StringBuilder extractionResult = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis)); 
        try {
            extractionResult.append(setSeries(reader.readLine())); 
            extractionResult.append(setRestTimeBetweenSeries(reader.readLine()));
            extractionResult.append(setExercisesNumber(reader.readLine()));
            extractionResult.append(setRestTimeBetweenExercises(reader.readLine()));
            manageExercisesList(); 
            for (int exercisesIterator = 0; reader.ready() && exercisesIterator < exercisesNumber; exercisesIterator++) {
                exercises.add(new Exercise(reader.readLine()));
            }
        } catch (IOException e) {
            return null; 
        } finally {
           closeStream(reader); 
        }
        return extractionResult.toString();
    }

    /**
     * Method converts String object provided as an argument to Long value.
     * In case of exception method returns default value.  
     * @param value String object which will be converted to long value
     * @return method returns the value converted, in case of exception method returns -1 
     */
    private long convertStringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch(Exception e) {
            return -1;
        }
    }

    /**
     * Method manages the ArrayList object containing exercises if the object is null new 'list' is created, in other case the old one is cleared
     */
    private void manageExercisesList() {
        if (exercises == null)
            exercises = new ArrayList<Exercise>();
        else 
            exercises.clear();
        
    }

    /**
     * Method counts down provided number of seconds 
     * @param seconds time to count down 
     * @return method returns true if there were no exception, in case of exception methods returns false  
     */
    private boolean timeCounter(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     *  Method counts down seconds with printing remaining time on to the default output stream 
     * @param seconds time to count down
     * @return method returns true if there weren't exception, in other case method returns false
     */
    private boolean printingTimeCounter(long seconds) {
        for (long i = seconds; 0 < i; i--) {
            System.out.println(i);
            if (!timeCounter(1))
                return false;
        }
        return true;
    }

    /**
     * Method checks results of counters. In case of counter working incorrectly timer throws RunTimeException terminating the training processing.  
     * @param counterResult result of counter method
     */
    private void timeCountersTerminator(boolean counterResult) {
       if (counterResult)
        return;
       else
        throw new RuntimeException(PROCESSING_TRAINING_ERROR_MESSAGE); 
    }

    /**
     * Method generates Path object based on the string passed as an argument. If the string cannot be converted to Path object null is returned. 
     * @param pathStr String object representing path to a file
     * @return method returns the Path object based on the String argument. If the string cannot be converted to Path method returns null.
     */
    private Path generatePath(String pathStr) {
        try {
            return Paths.get(pathStr);
        } catch(InvalidPathException e) {
            return null;
        }
    }

    /**
     * Method validates if training file contains valid syntax at the end to make sure that the correct file is going to be read.
     * @param path Path to file represented by the String
     * @return Boolean variable which represents the result of the operation
     */
    private boolean validateIfPathContainsTrainingSyntax(String path) {
        if (!path.contains("/"))
          return false;
        return path.toLowerCase().substring(path.lastIndexOf("/")).contains(VALID_TRAINING_SYNTAX);
    }

// SETTERS

    /**
     * Setter for series which should fit in the range from 0 exclusive to max value of byte inclusive.
     * If the value is above the range the series is set to long byte value.
     * In case of value smaller than zero or zero the object's variable is set to default value. 
     * @param seriesNumber value provided for a series in form of a String
     * @return method returns string containing information about the result of setter method.
     */
    private String setSeries(String seriesNumber) {
        variableName = "Series number";
        long series = convertStringToLong(seriesNumber);
        if (series > Byte.MAX_VALUE) {
            this.series = Byte.MAX_VALUE;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, Byte.MAX_VALUE);
        }
        else if (series > 0) 
            this.series = (byte) series;
        else{
            this.series = DEFAULT_SERIES_NUMBER;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_SERIES_NUMBER); 
        }
        return CORRECT_INPUT_VALUE; 
    }

    /**
     * Setter for restTimeBetweenSeries which should fit in the range from 0 exclusive to max value of long inclusive.
     * If the value is above the range the rest time between series is set to long max value.
     * In case of value smaller than zero or zero the object's variable is set to default value. 
     * @param restTimeSeries value provided for a rest time between series in form of a String
     * @return method returns string containing information about the result of setter method.
     */
    private String setRestTimeBetweenSeries(String restTimeSeries) {
        variableName = "Rest time between series";
        long restTimeSeriesSec = convertStringToLong(restTimeSeries);
        if (restTimeSeriesSec >= Long.MAX_VALUE) {
            this.restTimeBetweenSets = Long.MAX_VALUE;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, Long.MAX_VALUE);
        }
        else if (restTimeSeriesSec > 0)
            this.restTimeBetweenSets = (long) restTimeSeriesSec;
        else {
            this.restTimeBetweenSets = DEFAULT_SERIES_REST_TIME;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_SERIES_REST_TIME);
        }
        return CORRECT_INPUT_VALUE; 
    }

    /**
     * Setter for exercisesNumber which should fit in the range from 0 exclusive to max value of byte inclusive.
     * If the value is above the range the exercises number is set to byte max value.
     * In case of value smaller than zero or zero the object's variable is set to default value. 
     * @param value value provided for a exercises number in form of a String
     * @return method returns string containing information about the result of setter method.
     */
    private String setExercisesNumber(String value) {
        variableName = "Exercises number";
        long exercisesNumber = convertStringToLong(value);
        if (exercisesNumber > Byte.MAX_VALUE) {
            this.exercisesNumber = Byte.MAX_VALUE;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, Byte.MAX_VALUE);
        }
        else if (exercisesNumber > 0)
            this.exercisesNumber = (byte) exercisesNumber;
        else {
            this.exercisesNumber = DEFAULT_EXERCISES_NUMBER;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_EXERCISES_NUMBER);
        }
        return CORRECT_INPUT_VALUE;
    } 

    /**
     * Setter for restTimeBetweenExercises which should fit in the range from 0 exclusive to max value of integer inclusive. If the value is above the range the rest time is set to integer max value.
     * In case of value smaller than zero or zero the object's variable is set to default value. 
     * @param restTimeExercises value provided for a restTimeBetweenExercise in form of a String
     * @return method returns string containing the information about the result of setter method. 
     */
    private String setRestTimeBetweenExercises(String restTimeExercises) {
        variableName = "Rest time between exercises";
        long restTimeExercisesSec = convertStringToLong(restTimeExercises); 
        if (restTimeExercisesSec >= Integer.MAX_VALUE) {
            this.restTimeBetweenExercises = Integer.MAX_VALUE; 
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, Integer.MAX_VALUE);
        }
        else if(restTimeExercisesSec > 0)
            this.restTimeBetweenExercises = (int) restTimeExercisesSec;
        else {
            this.restTimeBetweenExercises = DEFAULT_EXERCISES_REST_TIME;
            return String.format(INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_EXERCISES_REST_TIME);
        }
        return CORRECT_INPUT_VALUE;
    }

    /**
     * Method responsible for attempting to close the stream. 
     * @param stream stream implementing closable interface allowing to close it
     * @return returns true if stream was closed correctly, in other case value returned is false
     */
    private boolean closeStream(Closeable stream) {
        try {
            stream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

// GETTERS 
    /**
     * Getter of List containing the exercises of a training unit.
     * @return exercises list
     */
    public List<Exercise> getExercisesList() {
        return exercises;
    }
}
