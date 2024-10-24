package menu;

public enum ActionType {
  LOAD_TRAINING("Load training"),
  START_TRAINING("Start training"),
  EXIT("Exit application");

  private final String operationName;

  private ActionType(String operationName) {
    this.operationName = operationName;
  }

  public static ActionType convertIntegerInputToActionType(int input) {
      switch (input) {
        case 1 -> { return LOAD_TRAINING; } 
        case 2 -> { return START_TRAINING; }
        case 3 -> { return EXIT; }
        default -> {return null; }
    }
  }

 public String getOperationName() {
  return operationName;
 } 
   
}
