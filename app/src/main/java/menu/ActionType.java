package menu;

public enum ActionType {
  LOAD_TRANING("Load traning"),
  START_TRANING("Start traning"),
  EXIT("Exit application");

  private final String operationName;

  private ActionType(String operationName) {
    this.operationName = operationName;
  }

  public static ActionType convertIntegerInputToActionType(int input) {
      switch (input) {
        case 1 -> { return LOAD_TRANING; } 
        case 2 -> { return START_TRANING; }
        case 3 -> { return EXIT; }
        default -> {return null; }
    }
  }

 public String getOperationName() {
  return operationName;
 } 
   
}
