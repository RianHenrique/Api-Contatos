package observer;

public class LoggerObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Log: O estado foi atualizado!");
    }
}
