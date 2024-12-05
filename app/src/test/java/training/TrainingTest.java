package training;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TrainingTest {
    private static String tooLargeInput, variableName;
    private static Training training = new Training();
    private static Method[] privMethods = Training.class.getDeclaredMethods(); 
    private static Field[] privFields = Training.class.getDeclaredFields();

    @Test 
    public void constructorDataConsistentTest() {
        Training training = new Training();
        Field field = findPrivateField("series");
        try {
            assertEquals(DEFAULT_VALUES.SERIES_NUMBER.byteValue(), field.getByte(training));
            field = findPrivateField("restTimeBetweenSets") ;
            assertEquals(DEFAULT_VALUES.SERIES_REST_TIME, field.getLong(training));
            field = findPrivateField("exercisesNumber");
            assertEquals(DEFAULT_VALUES.EXERCISES_NUMBER, field.getByte(training));
            field = findPrivateField("restTimeBetweenExercises");
            assertEquals(DEFAULT_VALUES.EXERCISES_REST_TIME, field.getInt(training));
        } catch(IllegalAccessException exception) {
            fail("Illegal Access Exception");
        }

    }
    @Test
    public void loadTrainingTest() {
        String pathToTrainingSchedule = "home/ciamcio/";
        String loadingResult = training.loadTraining(pathToTrainingSchedule);
        assertEquals(RESULT.LOADING_FAIL, loadingResult);
        pathToTrainingSchedule = "\0";
        loadingResult = training.loadTraining(pathToTrainingSchedule);
        assertEquals(RESULT.LOADING_FAIL, loadingResult);
        loadingResult = training.loadTraining(PATH.TO_CORRECT_TRAINING_WITH_INCORRECT_NAME);
        assertEquals(RESULT.LOADING_FAIL, loadingResult);
        loadingResult = training.loadTraining(PATH.TO_CORRECT_TRAINING);
        assertEquals(RESULT.LOADING_SUCCESS, loadingResult);
        loadingResult = training.loadTraining(PATH.TO_INCORRECT_TRAINING);
        assertEquals(RESULT.INCORRECT + RESULT.LOADING_SUCCESS, loadingResult);
    }

    @Test
    public void checkIfTrainingIsLoadedTest() {
        Training training = new Training();
        assertFalse(training.checkIfTrainingIsLoaded());
        training.loadTraining(PATH.TO_CORRECT_TRAINING);
        assertTrue(training.checkIfTrainingIsLoaded());
    }

    @Test
    public void openTrainingFileTest() {
        FileInputStream testFile = null;
        Method openTrainingFile = findPrivateMethod("openTrainingFileStream"); 
        if (openTrainingFile == null)
            fail("Method not found");
        openTrainingFile.setAccessible(true);
        try {
            testFile = (FileInputStream) openTrainingFile.invoke(training, Paths.get("src", "test","resources", "correctTrainingMock_TRAINING.txt").toAbsolutePath());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(testFile);

        try {
            testFile = (FileInputStream) openTrainingFile.invoke(training, Paths.get("src", "test","resources", "incorrectTrainingMock_Training.txt").toAbsolutePath());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(testFile); 

        try {
            testFile = (FileInputStream) openTrainingFile.invoke(training, Paths.get("src", "test","resources", "definitelyThereIsNoSuchFile.txt").toAbsolutePath());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNull(testFile); 

        openTrainingFile.setAccessible(false);
        openTrainingFile = null;
    }

    @Test
    public void extractTrainingTest() {
        String result = "";  
        FileInputStream fis = createFileStream(PATH.TO_CORRECT_TRAINING);
        Method extractTraining = findPrivateMethod("extractTraining"); 
        if (extractTraining == null)
            fail("Method not found");
        extractTraining.setAccessible(true);
        try {
            result = (String) extractTraining.invoke(training, fis);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);

        fis = createFileStream(PATH.TO_INCORRECT_TRAINING);
        try {
            result = (String) extractTraining.invoke(training, fis);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.INCORRECT, result);

        extractTraining.setAccessible(false);
        extractTraining = null;
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
            result = (Long) convertMethod.invoke(training, validInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(1, result);

        try {
            result = (Long) convertMethod.invoke(training, invalidInput);
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
            manageExercise.invoke(training);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(training.getExercisesList());
        List<Exercise> previousExerciseTraining = training.getExercisesList();
        try {
            manageExercise.invoke(training);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(previousExerciseTraining, training.getExercisesList());

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
            functionResult = (Boolean) timeCounter.invoke(training, timeToPass);
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
            functionResult = (Boolean) printingTimeCounter.invoke(training, timeToPass);
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
            timeCountersTerminator.invoke(training, true);
            methodCompleted = true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            fail(e.getCause());
        }  
        assertTrue(methodCompleted);
        assertThrows(InvocationTargetException.class, () -> timeCountersTerminator.invoke(training, false));

        timeCountersTerminator.setAccessible(false);
    }

    @Test
    public void validateIfPathContainsTrainingSyntaxTest() {
      Method validateIfPathContainsTrainingSyntax = findPrivateMethod("validateIfPathContainsTrainingSyntax"); 
      boolean methodResult = false;
      if (validateIfPathContainsTrainingSyntax == null)
        fail("Method not found");
      validateIfPathContainsTrainingSyntax.setAccessible(true);
          try {
            methodResult = (Boolean) validateIfPathContainsTrainingSyntax.invoke(training, PATH.TO_CORRECT_TRAINING);
            assertTrue(methodResult);

            methodResult = (Boolean) validateIfPathContainsTrainingSyntax.invoke(training, PATH.TO_CORRECT_TRAINING_WITH_INCORRECT_NAME);
            assertFalse(methodResult);

            methodResult = (Boolean) validateIfPathContainsTrainingSyntax.invoke(training, PATH.TO_INCORRECT_TRAINING);
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
            result = (String) setSeries.invoke(training, INPUT.INCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, DEFAULT_VALUES.SERIES_NUMBER), result);
        try {
            result = (String) setSeries.invoke(training, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);
        try {
            result = (String) setSeries.invoke(training, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, Byte.MAX_VALUE), result);

        setSeries.setAccessible(false);
        setSeries = null;
    }

    @Test
    public void setRestTimeBetweenSeriesTest() {
        String result = RESULT.CORRECT;
        variableName = "Rest time between series"; // Possible optimization by creating enum class 
        tooLargeInput = String.valueOf(Long.MAX_VALUE); // there is a problem with converting number because the converter is not working properly
        Method setRestTimeBetweenSeries = findPrivateMethod("setRestTimeBetweenSeries");

        if (setRestTimeBetweenSeries == null)
            fail("Method not found");
        setRestTimeBetweenSeries.setAccessible(true);
        try {
            result = (String) setRestTimeBetweenSeries.invoke(training, INPUT.INCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, DEFAULT_VALUES.SERIES_REST_TIME), result);

        try {
            result = (String) setRestTimeBetweenSeries.invoke(training, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);

        try {
            result = (String) setRestTimeBetweenSeries.invoke(training, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, Long.MAX_VALUE), result);

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
            result = (String) setExercisesNumber.invoke(training, INPUT.INCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, DEFAULT_VALUES.EXERCISES_NUMBER), result);

        try {
            result = (String) setExercisesNumber.invoke(training, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);
        try {
            result = (String) setExercisesNumber.invoke(training, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, Byte.MAX_VALUE), result);

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
            result = (String) setRestTimeExercises.invoke(training, INPUT.INCORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, DEFAULT_VALUES.EXERCISES_REST_TIME), result);

        try {
            result = (String) setRestTimeExercises.invoke(training, INPUT.CORRECT);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(RESULT.CORRECT, result);

        try {
            result = (String) setRestTimeExercises.invoke(training, tooLargeInput);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(String.format(RESULT.OUTPUT_TEMPLATE, variableName, Integer.MAX_VALUE), result);

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
            result = (boolean) closeStream.invoke(training, reader);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertTrue(result);

        closeStream.setAccessible(false);
        closeStream = null;
    }

    @Test
    public void generatePathTest() {
        Path resultPath = null;
        Method generatePath = findPrivateMethod("generatePath"); 
        if (generatePath == null)
            fail("Method not found");
        generatePath.setAccessible(true); 
        try {
            resultPath = (Path) generatePath.invoke(training, PATH.TO_CORRECT_TRAINING);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertNotNull(resultPath);
        String invalidPathStr = PATH.TO_CORRECT_TRAINING;
        invalidPathStr = "\0";
        try {
            resultPath = (Path) generatePath.invoke(training, invalidPathStr);
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
