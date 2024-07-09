package traning;

import java.util.*;
import java.io.*;

public class Traning implements TraningManagment{
    private static final String INCORECT_INPUT_TEMPLET = "%s has been set to %d, because the value read from file was inapproprited or too big.\n", CORRECT_INPUT_VALUE = ""; 
    private static final byte DEFAULT_SERIES_NUMBER = 1, DEFAULT_EXERCISES_NUMBER = 1;
    private static final long DEFAULT_SERIES_REST_TIME = 60;
    private static final int DEFAULT_EXERCISES_REST_TIME = 15;

    private String variableName;
    private byte series; 
    private long restTimeBetweenSets; 
    private byte exercisesNumber;
    private int restTimeBetweenExercises; 
    private List<Exercise> exercises = null;   

    @Override
    public boolean loadTraning(String path) {
        FileInputStream traningSchedule = openTraningFile(path);
        if (traningSchedule == null) 
            return false;
        return true;
    }

    @Override
    public void procesTraining() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesTraining'");
    }

    private FileInputStream openTraningFile(String path) {
        try {
            return new FileInputStream(path);
        } catch(Exception e) {
            return null;
        }
    }

    private String extractTraninig(FileInputStream fis) {
        StringBuilder extractionResult = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis)); 
        byte iterator = 0;
        try {
            extractionResult.append(setSeries(reader.readLine())); 
            extractionResult.append(setRestTimeBetweenExercises(reader.readLine()));
            extractionResult.append(setExercisesNumber(reader.readLine()));
            extractionResult.append(setRestTimeBetweenExercises(reader.readLine()));
            while (reader.ready() && iterator < exercisesNumber) {
                 
            }
        } catch (IOException e) {

        }



        return extractionResult.toString();
    }

    private long convertStringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch(Exception e) {
            return -1;
        }
    }

    private String setSeries(String value) {
        variableName = "Series number";
        long series = convertStringToLong(value);
        if (series > Byte.MAX_VALUE) {
            this.series = Byte.MAX_VALUE;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, Byte.MAX_VALUE);
        }
        else if (series > 0) 
            this.series = (byte) series;
        else{
            this.series = DEFAULT_SERIES_NUMBER;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, DEFAULT_SERIES_NUMBER); 
        }
        return CORRECT_INPUT_VALUE; 
    }

    private String setRestTimeBetweenSeries(String restTimeSeries) {
        variableName = "Rest time between series";
        long restTimeSeriesSec = convertStringToLong(restTimeSeries);
        if (restTimeSeriesSec >= Long.MAX_VALUE) {
            this.restTimeBetweenSets = Long.MAX_VALUE;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, Long.MAX_VALUE);
        }
        else if (restTimeSeriesSec > 0)
            this.restTimeBetweenSets = (long) restTimeSeriesSec;
        else {
            this.restTimeBetweenSets = DEFAULT_SERIES_REST_TIME;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, DEFAULT_SERIES_REST_TIME);
        }
        return CORRECT_INPUT_VALUE; 
    }

    private String setExercisesNumber(String value) {
        variableName = "Exercises number";
        long exercisesNumber = convertStringToLong(value);
        if (exercisesNumber > Byte.MAX_VALUE) {
            this.exercisesNumber = Byte.MAX_VALUE;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, Byte.MAX_VALUE);
        }
        else if (exercisesNumber > 0)
            this.exercisesNumber = (byte) exercisesNumber;
        else {
            this.exercisesNumber = DEFAULT_EXERCISES_NUMBER;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, DEFAULT_EXERCISES_NUMBER);
        }
        return CORRECT_INPUT_VALUE;
    } 

    private String setRestTimeBetweenExercises(String restTimeExercises) {
        variableName = "Rest time between exercises";
        long restTimeExercisesSec = convertStringToLong(restTimeExercises); 
        System.out.println(restTimeExercisesSec);
        if (restTimeExercisesSec >= Integer.MAX_VALUE) {
            this.restTimeBetweenExercises = Integer.MAX_VALUE; 
            return String.format(INCORECT_INPUT_TEMPLET, variableName, Integer.MAX_VALUE);
        }
        else if(restTimeExercisesSec > 0)
            this.restTimeBetweenExercises = (int) restTimeExercisesSec;
        else {
            this.restTimeBetweenExercises = DEFAULT_EXERCISES_REST_TIME;
            return String.format(INCORECT_INPUT_TEMPLET, variableName, DEFAULT_EXERCISES_REST_TIME);
        }
        return CORRECT_INPUT_VALUE;
    }

    

}