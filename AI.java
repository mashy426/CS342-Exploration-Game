/* CS342 Term Project Part III: Addition of Characters and Inheritance
 * Name:   Shyam Patel
 * NetID:  spate54
 * Date:   Oct 24, 2018
 */

import java.util.Random;


// AI class to encapsulate decision-making behaviors for NPCs
public class AI implements DecisionMaker {
    // select and return random "GET", "DROP" or "GO" move for NPC
    public Move getMove(Character c) {
        int random = new Random().nextInt(3);     // generate # from 0..2

        switch (random) {
            case 0 : return new GetNPC((NPC) c);  // "GET"  command
            case 1 : return new DropNPC((NPC) c); // "DROP" command
            case 2 : return new GoNPC((NPC) c);   // "GO"   command
        }
        return new GoNPC((NPC) c);
    }//end getMove()
}//end AI class
