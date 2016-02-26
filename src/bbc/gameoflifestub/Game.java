package bbc.gameoflifestub;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Created by elliottbrooks on 26/02/2016.
 */
public class Game {

    static int RANDOM_WIDTH = 30;
    static int RANDOM_HEIGHT = 10;
    static float RANDOM_COEFFICIENT = 0.75f;
    static int SIMULATION_SPEED = 100;

    public static void main(String[] args) {
        LifeBuilder lifeBuilder = new LifeBuilder();
        if (args.length != 1 || args[0] == null) {
            System.out.println("File path parameter missing, randomly generating");

            for (int i = 0; i < RANDOM_HEIGHT; i++) {
                String currentLine = "";
                for (int j = 0; j < RANDOM_WIDTH; j++) {
                    currentLine += Math.random() < RANDOM_COEFFICIENT ? "." : "*";
                }
                lifeBuilder.addInitialStateRows(currentLine);
            }
        } else {

            try {
                FileReader fileReader = new FileReader((String) args[0]);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String currentLine;

                while ((currentLine = bufferedReader.readLine()) != null) {
                    lifeBuilder.addInitialStateRows(currentLine);
                }
            } catch (IOException ioe) {
                System.out.println("Error: couldn't read file " + args[0]);
                return;
            }
        }

        if (!lifeBuilder.readyToBuild()) {
            System.out.println("Error: no initial states provided");
            return;
        } else {
            boolean evolving = true;
            Life life = lifeBuilder.build();
            System.out.println(life.toString());
            while (life.canEvolve() && evolving) {
                try {
                    evolving = life.evolve();
                    System.out.println(life.toString());
                    Thread.sleep(SIMULATION_SPEED);
                } catch (InterruptedException ie) {
                    return;
                }
            }
            if(evolving) {
                System.out.println("System has stopped being able to evolve. There are no more live cells.");
            }else{
                System.out.println("System has stopped being able to evolve. A stable arrangement has been reached.");
            }
        }
    }
}
