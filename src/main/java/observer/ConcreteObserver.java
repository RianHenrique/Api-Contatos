package observer;

public class ConcreteObserver implements Observer {
    @Override
    public void update(String message) {
        // Lógica de atualização do observador
        System.out.println("Alteração feita no telefone " + message);
    }
}

