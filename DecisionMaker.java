/* CS342 Term Project Part III: Addition of Characters and Inheritance
 * Name:   Shyam Patel
 * NetID:  spate54
 * Date:   Oct 24, 2018
 */


// DecisionMaker interface to encapsulate decision-making behaviors
// for UI and AI classes
public interface DecisionMaker {
    // abstract method : return move associated with player or NPC
    Move getMove(Character c);
}//end DecisionMaker interface
