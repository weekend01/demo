import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Que11 {

    private final int numPhilosophers;
    private final Fork[] forks;
    private final Philosopher[] philosophers;

    // Constructor initializes the philosophers and forks
    public Que11(int numPhilosophers) {
        this.numPhilosophers = numPhilosophers;
        forks = new Fork[numPhilosophers];
        philosophers = new Philosopher[numPhilosophers];
        
        // Initialize forks
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork();
        }
        
        // Initialize philosophers
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numPhilosophers]);
            new Thread(philosophers[i]).start();
        }
    }

    // Fork class represents a single fork
    private static class Fork {
        private final Lock lock = new ReentrantLock();
        
        public void pickUp() throws InterruptedException {
            lock.lock();  // Lock the fork
        }

        public void putDown() {
            lock.unlock();  // Unlock the fork
        }
    }

    // Philosopher class represents a philosopher who thinks and eats
    private static class Philosopher implements Runnable {
        private final int id;
        private final Fork leftFork;
        private final Fork rightFork;

        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    eat();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle interruption
            }
        }

        // Philosopher is thinking for a random time
        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking.");
            Thread.sleep((long) (Math.random() * 1000));  // Simulate thinking time
        }

        // Philosopher is eating for a random time after picking up forks
        private void eat() throws InterruptedException {
            leftFork.pickUp();  // Pick up left fork
            rightFork.pickUp();  // Pick up right fork

            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep((long) (Math.random() * 1000));  // Simulate eating time

            rightFork.putDown();  // Put down right fork
            leftFork.putDown();   // Put down left fork
        }
    }

    public static void main(String[] args) {
        new Que11(5); // Start with 5 philosophers
    }
}
