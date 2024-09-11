package controller;

import model.Pilha;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class CavaleiroThread extends Thread {
    private final int threadId;
    private final Semaphore semaphore;
    private final Semaphore semaphoreTocha;
    private final Semaphore semaphorePedra;
    private static Pilha portaStack;
    private long distance;

    public static boolean availableBoost = true;
    public static boolean availablePedraBoost = true;

    public Boolean hasBoost = false;
    public Boolean hasPedraBoost = false;

    public CavaleiroThread(int threadId, Semaphore semaphore, Semaphore semaphoreTocha, Semaphore semaphorePedra, Pilha portaStack) {
        this.threadId = threadId;
        this.semaphore = semaphore;
        this.semaphoreTocha = semaphoreTocha;
        this.semaphorePedra = semaphorePedra;
        CavaleiroThread.portaStack = portaStack;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getDistance() {
        return this.distance;
    }

    @Override
    public void run() {
        try {
            while (getDistance() < 2000) {
                if (getDistance() < 500) {
                    caminhar();
                }
                else if (getDistance() > 1500) {
                    pedra();
                    caminhar();
                }
                else {
                    tocha();
                    caminhar();
                }
            }
            porta();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void caminhar() throws InterruptedException {
        setDistance(getDistance() + getSpeed());
        System.out.println("Thread " + threadId + getAttributes() + " percorreu " + getDistance() + " (" + getSpeed() + " m/50ms)");
        Thread.sleep(50);
    }

    private long getSpeed() {
        int speed = ThreadLocalRandom.current().nextInt(2, 5);
        if (this.hasBoost) speed += 2;
        if (this.hasPedraBoost) speed += 2;
        return speed;
    }

    public void tocha() {
        if (availableBoost) {
            try {
                semaphoreTocha.acquire();
                System.out.println("Thread " + threadId + " pegou a tocha");
                this.hasBoost = true;
            } catch (InterruptedException ignored) {

            } finally {
                semaphoreTocha.release();
                availableBoost = false;
            }
        }
    }

    public void pedra() {
        if (availablePedraBoost && !hasBoost) {
            try {
                semaphorePedra.acquire();
                System.out.println("Thread " + threadId + " pegou a pedra!");
                this.hasPedraBoost = true;
            } catch (InterruptedException ignored) {

            } finally {
                semaphorePedra.release();
                availablePedraBoost = false;
            }
        }
    }

    public void porta() throws InterruptedException {
        try {
            semaphore.acquire();
            int escolha = portaStack.pop();
            if (escolha == 0) {
                System.out.println("Thread " + threadId + " foi devorado!");
            }
            else {
                System.out.println("Thread " + threadId + " encontrou a saída!");
            }
        } catch (InterruptedException ignored) {

        } finally {
            semaphore.release();
        }
    }

    private String getAttributes() {
        String result = "";
        if (this.hasBoost) result += " [TOCHA]";
        if (this.hasPedraBoost) result += " [PEDRA]";

        return result;
    }

}
