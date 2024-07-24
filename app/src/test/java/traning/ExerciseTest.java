package traning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ExerciseTest {
    private static final int EXERCISE_INFORMATION_IN_LINE = 3;
    private static final String correctExerciseMock = "Exercises1,30,It is a description of first exercises.";
    private Exercise exercise = new Exercise(correctExerciseMock);
    Method[] exerciseMethods = Exercise.class.getDeclaredMethods(); 

    @Test
    void constructorFromDataLineFetchTest() {
        String  resultName = "", resultDescription = "";
        String incorrectExerciseMock = ",30,It is a description of first exercise."; 
        long resultExecutionTime = -1;
        Method getName = findMethod("getName"), getExecutionTime = findMethod("getExecutionTime"), getDescription = findMethod("getDescription");  
        String[] exerciseSplited = correctExerciseMock.split(",");

        if (getName == null)
            fail("Method getName not found");
        else if (getExecutionTime == null)
            fail("Method getExecutionTime not found");
        else if (getDescription == null)
            fail("Method getDescription not found");

        Exercise exercise = new Exercise("Exercises1,30,It is a description of first exercises.");
        try {
            resultName = (String) getName.invoke(exercise);
            resultExecutionTime = (long) getExecutionTime.invoke(exercise); 
            resultDescription = (String) getDescription.invoke(exercise);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(exerciseSplited[0], resultName);
        assertEquals(exerciseSplited[1], String.valueOf(resultExecutionTime));
        assertEquals(exerciseSplited[2], resultDescription);

        exerciseSplited = incorrectExerciseMock.split(",");
        exercise = new Exercise(incorrectExerciseMock);
        try {
            resultName = (String) getName.invoke(exercise);
            resultExecutionTime = (long) getExecutionTime.invoke(exercise);
            resultDescription = (String) getDescription.invoke(exercise);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        } 
        assertEquals("None exercise name", resultName);
        assertEquals(exerciseSplited[1], String.valueOf(resultExecutionTime));
        assertEquals(exerciseSplited[2], resultDescription);
        
        incorrectExerciseMock = "Exercise1,-1,It is a description of first exercise."; 
        exerciseSplited = incorrectExerciseMock.split(",");
        exercise = new Exercise(incorrectExerciseMock);

        try {
            resultName = (String) getName.invoke(exercise);
            resultExecutionTime = (long) getExecutionTime.invoke(exercise);
            resultDescription = (String) getDescription.invoke(exercise);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        } 
        assertEquals(exerciseSplited[0], resultName);
        assertEquals("45", String.valueOf(resultExecutionTime));
        assertEquals(exerciseSplited[2], resultDescription);
        
        incorrectExerciseMock = "Exercise1,"+Integer.MAX_VALUE+1+","; 
        exerciseSplited = incorrectExerciseMock.split(",");
        exercise = new Exercise(incorrectExerciseMock);

        try {
            resultName = (String) getName.invoke(exercise);
            resultExecutionTime = (long) getExecutionTime.invoke(exercise);
            resultDescription = (String) getDescription.invoke(exercise);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        } 
        assertEquals(exerciseSplited[0], resultName);
        assertEquals("45", String.valueOf(resultExecutionTime));
        assertEquals("Lack of description", resultDescription);

        getName.setAccessible(false);
        getName = null;
    }

    @Test
    void fillExercisesInformationSizeDifferenceTest() {
        int resultSize = 0;
        List<String> exerciseData = new ArrayList<>();
        Method fillExercisesInfromation = findMethod("lineExerciseInformationSizeDifference");
        if (fillExercisesInfromation == null)
            fail("Method not found");
        fillExercisesInfromation.setAccessible(true);
        try {
            resultSize = (int) fillExercisesInfromation.invoke(exercise, exerciseData);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(EXERCISE_INFORMATION_IN_LINE, resultSize);
        assertEquals(EXERCISE_INFORMATION_IN_LINE, exerciseData.size()); 

        exerciseData = new ArrayList<>();
        exerciseData.add("");
        try {
            resultSize = (int) fillExercisesInfromation.invoke(exercise, exerciseData);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(2, resultSize);
        assertEquals(EXERCISE_INFORMATION_IN_LINE, exerciseData.size()); 


        exerciseData = new ArrayList<>();
        exerciseData.add("");
        exerciseData.add("");
        try {
            resultSize = (int) fillExercisesInfromation.invoke(exercise, exerciseData);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(1, resultSize);
        assertEquals(EXERCISE_INFORMATION_IN_LINE, exerciseData.size()); 

        exerciseData = new ArrayList<>();
        exerciseData.add("");
        exerciseData.add("");
        exerciseData.add("");
        try {
            resultSize = (int) fillExercisesInfromation.invoke(exercise, exerciseData);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(0, resultSize);
        assertEquals(EXERCISE_INFORMATION_IN_LINE, exerciseData.size()); 

        fillExercisesInfromation.setAccessible(false);
        fillExercisesInfromation = null;
    } 

    Method findMethod(String methodName) {
        for (Method method : exerciseMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }
}