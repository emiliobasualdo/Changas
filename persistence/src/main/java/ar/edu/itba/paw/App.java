package ar.edu.itba.paw;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Genera el de user_owns
        // 20 usuarios son dueños de 100 changas
        Random r = new Random();
        for (int i = 1; i < 101; i++) {
            System.out.println(String.format("(%d, %d),", r.nextInt(20)+1, i));
        }

        System.out.println();
        // Genera el de user_inscribed
        // 40 usuario están inscriptos en 60 changas y toman 4 posibles estados
        String[] state = {"emitted", "settled", "done", "closed"};
        for (int i = 1; i < 61; i++) {
            System.out.println(String.format("(%d, %d, '%s'),", r.nextInt(40)+1, r.nextInt(60)+1, state[r.nextInt(4)] ));
        }

    }
}
