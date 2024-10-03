package traning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TraningTest {
    private static String tooLargeInput, variableName;
    private static TrainingConstants traning = new TrainingConstants();
    private static Method[] privMethods = TrainingConstants.class.getDeclaredMethods(); 
    private static Field[] privFields = TrainingConstants.class.getDeclaredFields();

    @Test 
    public void constructorDataConsistentTest() {
        TrainingConstants traning = new TrainingConstants();
        Field field = findPrivateField("series");
        try {
            assertEquals(DEFAULT_VALUES.SERIES_NUMBER.byteValue(), field.getByte(traning));
            field = findPrivateField("restTimeBetweenSets") ;
            assertEquals(DEFAULT_VALUES.SERIES_REST_TIME, field.getLong(traning));
            field = findPrivateField("exercisesNumber");
            assertEquals(DEFAULT_VALUES.EXERCISES_NUMBER, field.getByte(traning));
            field = findPrivateField("restTimeBetweenExercises");
            assertEquals(DEFAULT_VALUES.EXERCISES_REST_TIME, field.getInt(traning));
        } catch(IllegalAccessException exception) {
            fail("Illegal Access Excpetion");
        }

    }
    @Test
    public void loadTraningTest() {
        String pathToTraningSchedule = "home/ciamcio/";
        String loadingResult = traning.loadTraning(pathToTraningSchedule);
        assertEquals(RESULT.LOADING_FAIL, loadingResult);
        pathToTraningSchedule = "\0";
        loadingResult = traning.loadTraning(pathToTraningSchedule);
        assertEquals(RESULT.LOADING_FAIL, loadingResult);
        loadingResult = traning.loadTraning(PATH.TO_CORRECT_TRAINING_WITH_INCORECT_NAME);
        assertEquals(RESULT.LOADING_FAIL, loadingResult);
        loadingResult = traning.loadTraning(PATH.TO_CORRECT_TRAINING);
        assertEquals(RESULT.LOADING_SUCCESS, loadingResult);
        loadingResult = traning.loadTraning(PATH.TO_INCORRECT_TRANING);
        assertEquals(RESULT.INCORRECT + RESULT.LOADING_SUCCESS, loadingResult);
    }

    @Test
    public void checkIfTraningIsLoadedTest() {
        assertFalse(traning.checkIfTraningIsLoaded());
        traning.loadTraning(PATH.TO_CORRECT_TRAINING);
        assertTrue(traning.checkIfTraningIsLoaded());
    }

    @Test
    public void openTraningFileTest() {
        FileInputStream testFile = null;
        Method openTrainingFile = findPrivateMethod("openTrainingFileStream"); 
        if (openTrainingFile == null)
            fail("Method not found");
        openTrainingFile.setAccessible(true);
        try {
            testFile = (FileInputStream) openTrainingFile.invoke(traning, Paths.get("src", "test","resources", "correctTrainingMock_TRANING.txt").toAbsolutePath());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(testFile);

        try {
            testFile = (FileInputStream) openTrainingFile.invoke(traning, Paths.get("src", "test","resources", "incorrectTrainingMock_Traning.txt").toAbsolutePath());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(testFile); 

        try {
            testFile = (FileInputStream) openTrainingFile.invoke(traning, Paths.get("src", "test","resources", "definitelyThereIsNoSuchFile.txt").toAbsolutePath());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNull(testFile); 

        openTrainingFile.setAccessible(false);
        openTrainingFile = null;
    }

    @Test
    public void extractTraningTest() {
        String result = "";  
        FileInputStream fis = createFileStream(PATH.TO_CORRECT_TRAINING);
        Method extractTraning = findPrivateMethod("extractTraninig"); 
        if (extractTraning == null)
            fail("Method not found");
        extractTraning.setAccessible(true);
        try {
            result = (String) extractTraning.invoke(traning, fis);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);

        fis = createFileStream(PATH.TO_INCORRECT_TRANING);
        try {
            result = (String) extractTraning.invoke(traning, fis);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.INCORRECT, result);

        extractTraning.setAccessible(false);
        extractTraning = null;
    }

    @Test
    public void convertStringToLongTest() {
        String validInput = "1", invalidInput = "word";
        long result = 0;
        Method convertMethod = findPrivateMethod("convertStringToLong");
        if (convertMethod == null)
            fail("Method not found");
        convertMethod.setAccessible(true);
        try {
            result = (Long) convertMethod.invoke(traning, validInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(1, result);

        try {
            result = (Long) convertMethod.invoke(traning, invalidInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(-1, result);

        convertMethod.setAccessible(false);
        convertMethod = null;
    }

    @Test
    public void manageExercisesListTest() {
        Method manageExercise = findPrivateMethod("manageExercisesList"); 
        if (manageExercise == null) 
            fail("Method not found");
        manageExercise.setAccessible(true);
        try {
            manageExercise.invoke(traning);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(traning.getExercisesList());
        List<Exercise> previousExerciseTraning = traning.getExercisesList();
        try {
            manageExercise.invoke(traning);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(previousExerciseTraning, traning.getExercisesList());

        manageExercise.setAccessible(false);
        manageExercise = null;
    } 

    @Test
    public void timeCounterTest() {
        boolean functionResult = false;
        long timeToPass = 5, systemTimeDifference = 0;
        Method timeCounter = findPrivateMethod("timeCounter");
        if (timeCounter == null)
            fail("Method not found");
        timeCounter.setAccessible(true);
        try {
            long startSystemTime = System.currentTimeMillis();
            functionResult = (Boolean) timeCounter.invoke(traning, timeToPass);
            systemTimeDifference = System.currentTimeMillis() - startSystemTime;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertTrue(functionResult);
        assertTrue(timeToPass * DEFAULT_VALUES.MILLISECONDS_IN_SECOND - (DEFAULT_VALUES.MILLISECONDS_IN_SECOND/2) < systemTimeDifference && systemTimeDifference < timeToPass * DEFAULT_VALUES.MILLISECONDS_IN_SECOND + (DEFAULT_VALUES.MILLISECONDS_IN_SECOND / 2));

        timeCounter.setAccessible(false);
        timeCounter = null;
    }

    @Test
    public void printingTimeCounter() {
        boolean functionResult = false;
        long timeToPass = 5, systemTimeDifference = 0;
        Method printingTimeCounter = findPrivateMethod("printingTimeCounter"); 
        if (printingTimeCounter == null)
            fail("Method not found");
        printingTimeCounter.setAccessible(true);
        try {
            long startSystemTime = System.currentTimeMillis();
            functionResult = (Boolean) printingTimeCounter.invoke(traning, timeToPass);
            systemTimeDifference = System.currentTimeMillis() - startSystemTime;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertTrue(timeToPass* DEFAULT_VALUES.MILLISECONDS_IN_SECOND - (DEFAULT_VALUES.MILLISECONDS_IN_SECOND / 2) < systemTimeDifference && systemTimeDifference < timeToPass * DEFAULT_VALUES.MILLISECONDS_IN_SECOND + (DEFAULT_VALUES.MILLISECONDS_IN_SECOND / 2));
        assertTrue(functionResult);

        printingTimeCounter.setAccessible(false);
        printingTimeCounter = null;
    }

    @Test
    public void timeCountersTerminatorTest() {
        boolean methodCompleted = false;
        Method timeCountersTerminator = findPrivateMethod("timeCountersTerminator");
        if (timeCountersTerminator == null)
            fail("Method not found");
        timeCountersTerminator.setAccessible(true);
        try {
            timeCountersTerminator.invoke(traning, true);
            methodCompleted = true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            fail(e.getCause());
        }  
        assertTrue(methodCompleted);
        assertThrows(InvocationTargetException.class, () -> timeCountersTerminator.invoke(traning, false));

        timeCountersTerminator.setAccessible(false);
    }

    @Test
    public void validateIfPathContainsTraningSyntaxTest() {
      Method validateIfPathContainsTrainingSyntax = findPrivateMethod("validateIfPathContainsTrainingSyntax"); 
      boolean methodResult = false;
      if (validateIfPathContainsTrainingSyntax == null)
        fail("Method not found");
      validateIfPathContainsTrainingSyntax.setAccessible(true);
          try {
            methodResult = (Boolean) validateIfPathContainsTrainingSyntax.invoke(traning, PATH.TO_CORRECT_TRAINING);
            assertTrue(methodResult);

            methodResult = (Boolean) validateIfPathContainsTrainingSyntax.invoke(traning, PATH.TO_CORRECT_TRAINING_WITH_INCORECT_NAME);
            assertFalse(methodResult);

            methodResult = (Boolean) validateIfPathContainsTrainingSyntax.invoke(traning, PATH.TO_INCORRECT_TRANING);
            assertTrue(methodResult);
          } catch (IllegalAccessException | InvocationTargetException e) {
              fail(e.getCause());
          }
    }


    @Test
    public void setSeriesTest() {
        String result = RESULT.CORRECT; 
        variableName = "Series number";
        tooLargeInput = String.valueOf(Byte.MAX_VALUE + 1);
        Method setSeries = findPrivateMethod("setSeries");
        if (setSeries == null)
            fail("Method not found");
        setSeries.setAccessible(true);
        try {
            result = (String) setSeries.invoke(traning, INPUT.INNCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, DEFAULT_VALUES.SERIES_NUMBER), result);
        try {
            result = (String) setSeries.invoke(traning, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);
        try {
            result = (String) setSeries.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, Byte.MAX_VALUE), result);

        setSeries.setAccessible(false);
        setSeries = null;
    }

    @Test
    public void setRestTimeBetweenSeriesTest() {
        String result = RESULT.CORRECT;
        variableName = "Rest time between series"; // Possible optimalization by creating enum class 
        tooLargeInput = String.valueOf(Long.MAX_VALUE); // there is a problem with converting number because the converter is not working properly
        Method setRestTimeBetweenSeries = findPrivateMethod("setRestTimeBetweenSeries");

        if (setRestTimeBetweenSeries == null)
            fail("Method not found");
        setRestTimeBetweenSeries.setAccessible(true);
        try {
            result = (String) setRestTimeBetweenSeries.invoke(traning, INPUT.INNCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, DEFAULT_VALUES.SERIES_REST_TIME), result);

        try {
            result = (String) setRestTimeBetweenSeries.invoke(traning, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);

        try {
            result = (String) setRestTimeBetweenSeries.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, Long.MAX_VALUE), result);

        setRestTimeBetweenSeries.setAccessible(false);
        setRestTimeBetweenSeries = null;
    }

    @Test
    public void setExercisesNumberTest() {
        String result = RESULT.CORRECT; 
        variableName = "Exercises number";
        tooLargeInput = String.valueOf(Byte.MAX_VALUE + 1);
        Method setExercisesNumber = findPrivateMethod("setExercisesNumber");

        if (setExercisesNumber == null) 
            fail("Method not found");
        setExercisesNumber.setAccessible(true);

        try {
            result = (String) setExercisesNumber.invoke(traning, INPUT.INNCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, DEFAULT_VALUES.EXERCISES_NUMBER), result);

        try {
            result = (String) setExercisesNumber.invoke(traning, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);
        try {
            result = (String) setExercisesNumber.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, Byte.MAX_VALUE), result);

        setExercisesNumber.setAccessible(false);
        setExercisesNumber = null;
    }

    @Test
    public void setRestTimeBetweenExercisesTest() {
        String result = RESULT.CORRECT; 
        variableName = "Rest time between exercises";
        tooLargeInput = String.valueOf(Integer.MAX_VALUE);
        Method setRestTimeExercises = findPrivateMethod("setRestTimeBetweenExercises");

        if (setRestTimeExercises == null) 
            fail("Method not found");
        setRestTimeExercises.setAccessible(true);

        try {
            result = (String) setRestTimeExercises.invoke(traning, INPUT.INNCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, DEFAULT_VALUES.EXERCISES_REST_TIME), result);

        try {
            result = (String) setRestTimeExercises.invoke(traning, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);

        try {
            result = (String) setRestTimeExercises.invoke(traning, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLET, variableName, Integer.MAX_VALUE), result);

        setRestTimeExercises.setAccessible(false);
        setRestTimeExercises = null;
    }

    @Test
    public void closeStreamTest() {
        boolean result = false; 
        BufferedReader reader = null;

        Method closeStream = findPrivateMethod("closeStream");
        if (closeStream == null)
            fail("Method not found");
        closeStream.setAccessible(true);
        try {
            reader = new BufferedReader(new FileReader(PATH.TO_CORRECT_TRAINING));
        } catch(Exception e) {
            fail(e.getCause());
        }
        try {
            result = (boolean) closeStream.invoke(traning, reader);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertTrue(result);

        closeStream.setAccessible(false);
        closeStream = null;
    }

    @Test
    public void geenratePathTest() {
        Path resultPath = null;
        Method generatePath = findPrivateMethod("generatePath"); 
        if (generatePath == null)
            fail("Method not found");
        generatePath.setAccessible(true); 
        try {
            resultPath = (Path) generatePath.invoke(traning, PATH.TO_CORRECT_TRAINING);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(resultPath);
        String invalidPathStr = PATH.TO_CORRECT_TRAINING;
        invalidPathStr = "\0";
        try {
            resultPath = (Path) generatePath.invoke(traning, invalidPathStr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNull(resultPath);

        generatePath.setAccessible(false);
        generatePath = null;
    }


    Method findPrivateMethod(String methodName) {
        for (Method method : privMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

    FileInputStream createFileStream(String path) {
        try {
            return new FileInputStream(path);
        } catch(IOException exception) {
            return null;
        }
    }

    Field findPrivateField(String fieldName) {
        for (Field field : privFields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }


}
