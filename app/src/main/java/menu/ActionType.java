package menu;

public enum ActionType {
  LOAD_TRANING,
  START_TRANING,
  EXIT;

   public static ActionType convertIntegerInputToActionType(int input) {
      switch (input) {
        case 1 -> { return LOAD_TRANING; } 
        case 2 -> { return START_TRANING; }
        case 3 -> { return EXIT; }
        default -> {return null; }
      }
   }
  
   
}
