package menu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.*;
import java.util.*;

class MenuTest {
    private static Menu menu = Menu.getInstance();
    private static Menu mockitoMenu = Mockito.spy(menu);
    private static final Method[] privMethods = Menu.class.getDeclaredMethods();

    @Test 
    void singletonMenuTest() {
        assertNotNull(menu);
        Menu testMenu = Menu.getInstance(); 
        assertEquals(menu, testMenu);
    }

    @Test
    void handleInputTest() {
        String userInput = "1\n";
        int resultInput = -1;
        Method handleInput = findPrivateMethod("handleInput");
        if (handleInput == null) 
           fail("Method not found"); 
        handleInput.setAccessible(true);
        try {
            resultInput = (Integer) handleInput.invoke(menu, new ByteArrayInputStream(userInput.getBytes()));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(1, resultInput);
        userInput = "word\n";
        try {
            resultInput = (Integer) handleInput.invoke(menu, new ByteArrayInputStream(userInput.getBytes()));
        } catch(Exception e) {
            fail("Method shouldn't throw an exception");
        }
        assertEquals(-1, resultInput);
        handleInput.setAccessible(false);
        handleInput = null;
    }

    @Test
    void getIntegerInputTest() {
        String userIntegerInput = "1\n";
        int resultInteger = -1;
        Method getIntegerInput = findPrivateMethod("getIntegerInput");
        if (getIntegerInput == null)
            fail("Method not found");
        getIntegerInput.setAccessible(true);
        try {
            resultInteger = (Integer) getIntegerInput.invoke(menu, new ByteArrayInputStream(userIntegerInput.getBytes()));
        } catch(Exception e) {
            fail(e.getCause());
        }
        assertEquals(1, resultInteger);
        userIntegerInput = "word\n";
        try {
            resultInteger = (Integer) getIntegerInput.invoke(menu, new ByteArrayInputStream(userIntegerInput.getBytes()));
        } catch(Exception e) {
            fail("Method shouldn't throw an exception");
        }
        assertEquals(0, resultInteger);
        getIntegerInput.setAccessible(false);
        getIntegerInput = null;
    }

    @Test
    void getInputTest() {
        String result = "";
        String userInput = "word";
        Method getInput = findPrivateMethod("getInput");
        if (getInput == null)
            fail("Method not found");
        getInput.setAccessible(true);
        try {
            result = (String) getInput.invoke(menu, new ByteArrayInputStream(userInput.getBytes()));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        } 
        assertEquals(userInput, result);
    }

    @Test
    void validateInputTest() {
        int validInput = 1, negativeInput = -1, zeroInput  = 0, oversizeInput = Integer.MAX_VALUE;
        List<Boolean> validationResults = new ArrayList<>(); 
        Method validateInput = findPrivateMethod("validateInput");
        if (validateInput == null)
            fail("Method not found");
        validateInput.setAccessible(true);
        try {
            validationResults.add((Boolean) validateInput.invoke(menu, validInput));
            validationResults.add((Boolean) validateInput.invoke(menu, negativeInput));
            validationResults.add((Boolean) validateInput.invoke(menu, zeroInput));
            validationResults.add((Boolean) validateInput.invoke(menu, oversizeInput));
        } catch(Exception e) {
            fail("Method shouldn't throw an expception");      
        }

        assertEquals(true, validationResults.get(0));
        assertEquals(false, validationResults.get(1));
        assertEquals(false, validationResults.get(2));
        assertEquals(false, validationResults.get(3));
        validateInput.setAccessible(false);
        validateInput = null;
    }

    @Test
    void performeMenuActionTest() {
        int loadingInput = 1, processingInput = 2,  closingInput = 3;
        boolean result;
        doReturn(false).when(mockitoMenu).performeMenuAction(loadingInput);
        result = mockitoMenu.performeMenuAction(loadingInput);
        assertEquals(false, result);
        result = mockitoMenu.performeMenuAction(processingInput);
        assertFalse(result);
        result = mockitoMenu.performeMenuAction(closingInput);
        assertEquals(true, result);
    }


    @Test
    void closeStreamTest() {
        boolean result = false; 
        BufferedReader reader = null;

        Method closeStream = findPrivateMethod("closeStream");
        if (closeStream == null)
            fail("Method not found");
        closeStream.setAccessible(true);
        try {
            reader = new BufferedReader(new FileReader("/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/correctTraningMock.txt"));
        } catch(Exception e) {
            fail(e.getCause());
        }
        try {
            result = (boolean) closeStream.invoke(menu, reader);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getCause());
        }
        assertEquals(true, result);
    }

    @Test
    void getPathToTraningTest() {
        String path = "/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/correctTraningMock.txt";
        doReturn(path).when(mockitoMenu).getPathToTraning();
        String result = mockitoMenu.getPathToTraning();
        assertEquals(path, result);
        path = "/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/incorrectTraninigMock.txt";
        doReturn(path).when(mockitoMenu).getPathToTraning();
        result = mockitoMenu.getPathToTraning();        
        assertEquals(path, result);
    }


    Method findPrivateMethod(String methodName) {
        for (Method method : privMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

}