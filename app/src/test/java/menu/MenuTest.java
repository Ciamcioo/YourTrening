package menu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.*;
import java.util.*;

class MenuTest {
    private static Menu menu = Menu.getInstance();
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
            resultInput = (Integer) handleInput.invoke(menu, new InputStreamReader(new ByteArrayInputStream(userInput.getBytes())));
        } catch(Exception e) {
            fail("Method shouldn't throw an exception");
        }
        assertEquals(1, resultInput);
        userInput = "word\n";
        try {
            resultInput = (Integer) handleInput.invoke(menu, new InputStreamReader(new ByteArrayInputStream(userInput.getBytes())));
        } catch(Exception e) {
            fail("Method shouldn't throw an exception");
        }
        assertEquals(-1, resultInput);
        handleInput.setAccessible(false);
        handleInput = null;
    }

    @Test
    void getInputTest() {
        String userInput = "1\n";
        int resultInput = -1;
        Method getInput = findPrivateMethod("getInput");
        if (getInput == null)
            fail("Method not found");
        getInput.setAccessible(true);
        try {
            resultInput = (Integer) getInput.invoke(menu, new InputStreamReader(new ByteArrayInputStream(userInput.getBytes())));
        } catch(Exception e) {
            fail("Method shouldn't throw an excpetion");
        }
        assertEquals(1, resultInput);
        userInput = "word\n";
        try {
            resultInput = (Integer) getInput.invoke(menu, new InputStreamReader(new ByteArrayInputStream(userInput.getBytes())));
        } catch(Exception e) {
            fail("Method shouldn't throw an exception");
        }
        assertEquals(0, resultInput);
        getInput.setAccessible(false);
        getInput = null;
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
        boolean result = false;
        int defaultInput = 1, closingActionInput = 3;
        Method performeMenuAction = findPrivateMethod("performeMenuAction");
        if (performeMenuAction == null)
            fail("Method not found");
        performeMenuAction.setAccessible(true);
        try {
            result = (Boolean) performeMenuAction.invoke(menu, defaultInput);
        } catch(Exception e) {
            fail("Method shouldn't throw an exception");
        }
        assertEquals(false, result);
        try {
            result = (Boolean) performeMenuAction.invoke(menu, closingActionInput);
        } catch(Exception e) {
            fail("Method shouldn't throw an excpetion");
        }
        assertEquals(true, result);
        performeMenuAction.setAccessible(false);
        performeMenuAction = null;
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
            fail(e.getMessage());
        }
        try {
            result = (boolean) closeStream.invoke(menu, reader);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertEquals(true, result);
    }


    Method findPrivateMethod(String methodName) {
        for (Method method : privMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

}