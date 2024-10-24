package training;

class INPUT {
  static final String INCORRECT = "word";
  static final String CORRECT = String.valueOf(2);
}

class PATH { 
  static final String TO_CORRECT_TRAINING = "/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/correctTrainingMock_TRAINING.txt";
  static final String TO_CORRECT_TRAINING_WITH_INCORRECT_NAME = "/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/correctTrainingMockWithIncorrectFileName.txt";
  static final String TO_INCORRECT_TRAINING = "/home/ciamcio/workspace/javaPrograming/YourTraining/app/src/test/resources/incorrectTrainingMock_Training.txt";
}

class RESULT {
  static final String CORRECT = "";
  static final String INCORRECT = "Series number has been set to 127, because the value read from file was inappropriate or too big.\n" + 
                                  "Rest time between series has been set to 60, because the value read from file was inappropriate or too big.\n" +
                                  "Rest time between exercises has been set to 2147483647, because the value read from file was inappropriate or too big.\n";
  static final String LOADING_FAIL = "TRAINING LOADING FAILED\n";
  static final String LOADING_SUCCESS = "TRAINING LOADING SUCCESSFUL\n";
  static final String OUTPUT_TEMPLATE = "%s has been set to %d, because the value read from file was inappropriate or too big.\n";
} 

class DEFAULT_VALUES {
    static final Byte SERIES_NUMBER = 1, EXERCISES_NUMBER = 1;
    static final Long SERIES_REST_TIME = 60L, EXERCISES_REST_TIME = 15L, MILLISECONDS_IN_SECOND = 1000L;
}
