package gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.lang.reflect.*;

import menu.ActionType;
import training.Training;

public class MenuGUITest {
    private MenuGUI menuGui = MenuGUI.getIntsance();
    private static final int HEIGHT = 300, WIDTH = 300, COMPONENTS_NUMBER = 2; 
    private static final Method[] privMethods = MenuGUI.class.getDeclaredMethods();
    private static final Field[] privFields = MenuGUI.class.getDeclaredFields();

    @Test 
    public void getInstanceTest() {
        MenuGUI testMenu = null;
        assertNull(testMenu);
        testMenu = MenuGUI.getIntsance();
        assertTrue(menuGui instanceof MenuGUI);
    }

    @Test
    public void constructorMenuGuiTest() {
        Field field = null;
        try {
          field = findPrivateFields("traning"); 
          assertTrue((Training) field.get(menuGui) instanceof Training);
          field = findPrivateFields("frame");
          assertTrue((JFrame) field.get(menuGui) instanceof JFrame);
          field = findPrivateFields("titleLabel"); 
          assertTrue((JLabel) field.get(menuGui) instanceof JLabel);
          field = findPrivateFields("buttonArea");
          assertTrue((Box) field.get(menuGui) instanceof Box);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            fail(e.getCause());
        }
          
    }
    
    @Test 
    public void basicFrameInitalizationTest() {
        JFrame resultFrame = new JFrame();
        Container startPanel = resultFrame.getContentPane();
        String frameTitle = "Your training";
        final int WIDTH = 300, HEIGHT = 300;
        Method initalizeFrame = findPrivateMethod("initializeFrame");
        if (initalizeFrame == null)
            fail("Method not found");
        initalizeFrame.setAccessible(true);
        try {
            resultFrame = (JFrame) initalizeFrame.invoke(menuGui); 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        
        assertEquals(frameTitle, resultFrame.getTitle());
        assertEquals(new Dimension(2*WIDTH, 2*HEIGHT), resultFrame.getSize());
        assertEquals(new Dimension(WIDTH, HEIGHT), resultFrame.getMinimumSize());
        assertEquals(JFrame.EXIT_ON_CLOSE, resultFrame.getDefaultCloseOperation());
        assertFalse(startPanel.equals(resultFrame.getContentPane()));
        
    }

    @Test
    public void basicPanelInitalizationTest() {
        JPanel resultPanel = new JPanel();
        Method mainPanelInitalization = findPrivateMethod("mainPanelInitalization");
        if (mainPanelInitalization == null)
            fail("Method not found");
        mainPanelInitalization.setAccessible(true);
        try {
            resultPanel = (JPanel) mainPanelInitalization.invoke(menuGui);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        assertTrue(resultPanel.getLayout() instanceof BoxLayout);
    }

    @Test
    public void basicTitleLabelInitalization() {
        JLabel resultLabel = new JLabel();
        String title = "Your traning";
        Method initalizeTitleLabel = findPrivateMethod("initalizeTitleLabel");
        if (initalizeTitleLabel == null)
            fail("Mehtod not found");
        initalizeTitleLabel.setAccessible(true);
        try {
            resultLabel = (JLabel) initalizeTitleLabel.invoke(menuGui);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        assertEquals(new Font("Times New Roman", Font.ITALIC, 24), resultLabel.getFont());
        assertEquals(title, resultLabel.getText());
    }

    @Test
    public void initalizeMainMenuButtonsTest() {
      Box resutlBox = null;
      Method initalizeMainMenuButtons = findPrivateMethod("initalizeMainMenuButtons");
      final int BUTTONS_NUM = 3;
      if (initalizeMainMenuButtons == null)
        fail("Mehotd shouldn't be null");
      initalizeMainMenuButtons.setAccessible(true);
      try {
        resutlBox = (Box) initalizeMainMenuButtons.invoke(menuGui);
      } catch (IllegalAccessException | InvocationTargetException e) {
          fail(e.getCause());
      }
      assertNotNull(resutlBox);
      assertEquals(BUTTONS_NUM, resutlBox.getComponentCount());

    }

    @Test
    public void mainMenuButtonFactoryTest() {
        JButton resultButton =  null; 
        String name = ActionType.LOAD_TRANING.getOperationName();
        Method mainMenuButtonFactory = findPrivateMethod("mainMenuButtonFactory"); 
        if (mainMenuButtonFactory == null)
            fail("Method not found");
        mainMenuButtonFactory.setAccessible(true);
        try {
           resultButton = (JButton) mainMenuButtonFactory.invoke(menuGui, ActionType.LOAD_TRANING); 
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        assertTrue(name.equals(resultButton.getText()));
        assertEquals(1, resultButton.getActionListeners().length);
        assertEquals(new Dimension(WIDTH/5, HEIGHT/10), resultButton.getSize());
    }

    @Test
    public void addComponentsTest() {
        Field field =  null;
        JFrame resultFrame = null;

        try {
          field = MenuGUI.class.getDeclaredField("frame");
        } catch (NoSuchFieldException | SecurityException e) {
          fail(e.getCause());
        }        
        if (field == null) 
          fail("Field shouldn't be null");
        field.setAccessible(true); 
        try {
          resultFrame  = (JFrame) field.get(menuGui);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          fail(e.getCause());
        }
        if (resultFrame == null)
          fail("Frame shouldn't be null");
       assertEquals(COMPONENTS_NUMBER, resultFrame.getContentPane().getComponentCount()); 
    }

    @Test
    public void getTrainingFileTest() {
      JFileChooser mockFileChooser = Mockito.mock(JFileChooser.class) ;
      File mockFile = new File("/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/correctTrainingMock_TRANING.txt");
      File resultFile = null;
      when(mockFileChooser.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
      when(mockFileChooser.getSelectedFile()).thenReturn(mockFile);
      Method getTrainingFile = findPrivateMethod("getTrainingFile"); 
      if (getTrainingFile == null)
        fail("Method not found");
      getTrainingFile.setAccessible(true);
      try {
        resultFile = (File) getTrainingFile.invoke(menuGui, mockFileChooser);
      } catch (IllegalAccessException | InvocationTargetException e) {
        fail(e.getCause());
      }      
      assertNotNull(resultFile);
      assertEquals(mockFile, resultFile);
      when(mockFileChooser.showOpenDialog(any())).thenReturn(JFileChooser.ERROR_OPTION);
      when(mockFileChooser.getSelectedFile()).thenReturn(null);

      try {
        resultFile = (File) getTrainingFile.invoke(menuGui, mockFileChooser);
      } catch (IllegalAccessException | InvocationTargetException exception) {
        fail(exception.getCause());
      }
      assertNull(resultFile);
    } 
      

    /**
     * Method seeks for method which name matches with the passed arguemnt.  
     * @param name Name of the method to find
     * @return Reference to the Method object if there is method with such a name. In other case method returns null.
     */
    private Method findPrivateMethod(String name) {
        for (Method method : privMethods) {
            if (method.getName().equals(name)) 
                return method;
        }
        return null;
    }

    /**
     * Method seeks for field which name matches the passed name arguemtnt. 
     * @param name name of the field
     * @return field that matches the name
     */
    private Field findPrivateFields(String name) {
      for (Field field : privFields) 
          if (field.getName().equals(name)) {
              field.setAccessible(true);
              return field;
          }
      return null;    
      
    }
}
