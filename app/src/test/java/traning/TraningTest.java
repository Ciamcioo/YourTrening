package traning;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.google.j2objc.annotations.Weak;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.*;

public class TraningTest {
    private static final String INCORRECT_STR_INPUT = "word", CORRECT_STR_INPUT = String.valueOf(2);
    private static final String RESULT_TO_INCORRECT_INPUT_TEMPLATE = "%s has been set to %d, because the value read from file was inapproprited or too big.\n";
    private static final String CORRECT_RESULT = "";
    private static final Byte DEFAULT_SERIES_NUMBER = 1, DEFAULT_EXERCISES_NUMBER = 1;
    private static final Long DEFAULT_SERIES_REST_TIME = 60L, DEFAULT_EXERCISES_REST_TIME = 15L;
    private static final String pathToCorrectTraning = "/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/correctTraningMock.txt";
 

    private static String tooLargeInput, variableName;
    private static Traning traning = new Traning();
    private static Method[] privMethods = Traning.class.getDeclaredMethods(); 

    @Test
    public void loadTraning() {
        String pathToTraningSchedule = "empty";
        boolean loadingResult = traning.loadTraning(pathToTraningSchedule);
        assertEquals(false, loadingResult);
        loadingResult = traning.loadTraning(pathToCorrectTraning);
        assertEquals(true, loadingResult);
    }

    @Test
    public void extractTraningTest() {
        String result = "";  
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pathToCorrectTraning);
        } catch (FileNotFoundException e) {
            fail("File should be found");
        }

        Method extractTraning = findPrivateMethod("extractTraning"); 
        if (extractTraning == null)
            fail("Method not found");
        extractTraning.setAccessible(true);

        try {
            result = (String) extractTraning.invoke(traning, fis);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail("Method shouldn't thow an excption");
        }

    }

    @Test
    public void setSeriesTest() {
        String result = CORRECT_RESULT; 
        variableName = "Series number";
        tooLargeInput = String.valueOf(Byte.MAX_VALUE + 1);
        Method setSeries = findPrivateMethod("setSeries");
        if (setSeries == null)
            fail("Method not found");
        setSeries.setAccessible(true);
        try {
            result = (String) setSeries.invoke(traning, INCORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_SERIES_NUMBER), result);
        try {
            result = (String) setSeries.invoke(traning, CORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(CORRECT_RESULT, result);

        try {
            result = (String) setSeries.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, Byte.MAX_VALUE), result);
    }

    @Test
    public void setRestTimeBetweenSeriesTest() {
        String result = CORRECT_RESULT;
        variableName = "Rest time between series"; // Possible optimalization by creating enum class 
        tooLargeInput = String.valueOf(Long.MAX_VALUE); // there is a problem with converting number because the converter is not working properly
        Method setRestTimeBetweenSeries = findPrivateMethod("setRestTimeBetweenSeries");

        if (setRestTimeBetweenSeries == null)
            fail("Method not found");
        setRestTimeBetweenSeries.setAccessible(true);
        try {
            result = (String) setRestTimeBetweenSeries.invoke(traning, INCORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_SERIES_REST_TIME), result);

        try {
            result = (String) setRestTimeBetweenSeries.invoke(traning, CORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(CORRECT_RESULT, result);

        try {
            result = (String) setRestTimeBetweenSeries.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, Long.MAX_VALUE), result);
    }

    @Test
    public void setExercisesNumberTest() {
        String result = CORRECT_RESULT; 
        variableName = "Exercises number";
        tooLargeInput = String.valueOf(Byte.MAX_VALUE + 1);
        Method setExercisesNumber = findPrivateMethod("setExercisesNumber");

        if (setExercisesNumber == null) 
            fail("Method not found");
        setExercisesNumber.setAccessible(true);

        try {
            result = (String) setExercisesNumber.invoke(traning, INCORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_EXERCISES_NUMBER), result);

        try {
            result = (String) setExercisesNumber.invoke(traning, CORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(CORRECT_RESULT, result);

        try {
            result = (String) setExercisesNumber.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, Byte.MAX_VALUE), result);
    }

    @Test
    public void setRestTimeBetweenExercisesTest() {
        String result = CORRECT_RESULT; 
        variableName = "Rest time between exercises";
        tooLargeInput = String.valueOf(Integer.MAX_VALUE);
        Method setRestTimeExercises = findPrivateMethod("setRestTimeBetweenExercises");

        if (setRestTimeExercises == null) 
            fail("Method not found");
        setRestTimeExercises.setAccessible(true);

        try {
            result = (String) setRestTimeExercises.invoke(traning, INCORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, DEFAULT_EXERCISES_REST_TIME), result);

        try {
            result = (String) setRestTimeExercises.invoke(traning, CORRECT_STR_INPUT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(CORRECT_RESULT, result);

        try {
            result = (String) setRestTimeExercises.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(String.format(RESULT_TO_INCORRECT_INPUT_TEMPLATE, variableName, Integer.MAX_VALUE), result);
    }

    @Test
    public void convertStringToIntegerTest() {
        String validInput = "1", invalidInput = "word";
        long result = 0;
        Method convertMethod = findPrivateMethod("convertStringToLong");
        if (convertMethod == null)
            fail("Method not found");
        convertMethod.setAccessible(true);
        try {
            result = (Long) convertMethod.invoke(traning, validInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(1, result);

        try {
            result = (Long) convertMethod.invoke(traning, invalidInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(-1, result);
        convertMethod.setAccessible(false);
        convertMethod = null;
    }

    @Test
    public void openTraningFileTest() {
        FileInputStream testFile = null;
        Method openTraningFile = findPrivateMethod("openTraningFile"); 
        if (openTraningFile == null)
            fail("Method not found");
        openTraningFile.setAccessible(true);
        try {
            testFile = (FileInputStream) openTraningFile.invoke(traning, pathToCorrectTraning);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertNotNull(testFile);

        String copyToCorrectTraning = pathToCorrectTraning;
        copyToCorrectTraning = null;
        try {
            testFile = (FileInputStream) openTraningFile.invoke(traning, copyToCorrectTraning);
        } catch(Exception e) {
            fail("Method shouldn't throw an expcetion.");
        }
        assertNull(testFile); 

        openTraningFile.setAccessible(false);
        openTraningFile = null;
    }

    Method findPrivateMethod(String methodName) {
        for (Method method : privMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

}