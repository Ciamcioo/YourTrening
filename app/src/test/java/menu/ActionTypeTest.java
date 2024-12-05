package menu;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * ActionTypeTest
 */
public class ActionTypeTest {

  @Test
  public void convertIntegerInputToActionTypeTest() {
    try {
      Method convertIntegerInputToActionType = ActionType.class.getDeclaredMethod("convertIntegerInputToActionType", int.class);
      if (convertIntegerInputToActionType == null)
        fail("Method shouldn't be null");
      convertIntegerInputToActionType.setAccessible(true);
      ActionType resultActionType = (ActionType) convertIntegerInputToActionType.invoke(ActionType.class, 1);
      assertEquals(ActionType.LOAD_TRAINING, resultActionType);
      resultActionType = (ActionType) convertIntegerInputToActionType.invoke(ActionType.class, 2);
      assertEquals(ActionType.START_TRAINING, resultActionType);
      resultActionType = (ActionType) convertIntegerInputToActionType.invoke(ActionType.class, 3);
      assertEquals(ActionType.EXIT, resultActionType);
      resultActionType = (ActionType) convertIntegerInputToActionType.invoke(ActionType.class, new Random().nextInt());
      assertNull(resultActionType); 
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
      fail(e.getCause());
    } 
  }    
}
