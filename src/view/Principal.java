package view;

import controller.CavaleiroThread;
import model.Pilha;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Principal {

    public static Semaphore semaforoPorta = new Semaphore(1);
    public static Semaphore semaforoTocha = new Semaphore(1);
    public static Semaphore semaforoPedra = new Semaphore(1);
    public static Pilha portaStack = new Pilha();

    public static void main(String[] args) {
        Boolean portaEncontrada = false;
        for (int i = 0; i < 4; i++) {
            int rd = ThreadLocalRandom.current().nextInt(0, 100);
            if (rd % 2 == 0 && !portaEncontrada) {
                portaStack.push(1);
                portaEncontrada = true;
            } else {
                portaStack.push(0);
            }
        }

        Thread thread = new CavaleiroThread(1, semaforoPorta, semaforoTocha, semaforoPedra, portaStack);
        Thread thread2 = new CavaleiroThread(2, semaforoPorta, semaforoTocha, semaforoPedra, portaStack);
        Thread thread3 = new CavaleiroThread(3, semaforoPorta, semaforoTocha, semaforoPedra, portaStack);
        Thread thread4 = new CavaleiroThread(4, semaforoPorta, semaforoTocha, semaforoPedra, portaStack);
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
