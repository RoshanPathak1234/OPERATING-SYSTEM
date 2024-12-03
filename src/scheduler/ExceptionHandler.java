package scheduler;

public class ExceptionHandler {
    
    // Handle MissingStrategyException
    public static void handleMissingStrategyException(MissingStrategyException e) {
        System.err.println("Error: " + e.getMessage());
    }
    
    // Handle IllegalArgumentException
    public static void handleIllegalArgumentException(IllegalArgumentException e) {
        System.err.println("Invalid argument: " + e.getMessage());
    }
    
    // Handle general exceptions
    public static void handleGeneralException(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
    }
}

class MissingStrategyException extends RuntimeException {
    public MissingStrategyException(String message) {
        super(message);
    }
}

