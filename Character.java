/* CS342 Term Project Part III: Addition of Characters and Inheritance
 * Name:   Shyam Patel
 * NetID:  spate54
 * Date:   Oct 24, 2018
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;


// Character abstract class to encapsulate attributes and methods for
// Character objects, and is extended by Player and NPC subclasses
public abstract class Character {
    // private attributes
    protected int                 ID;          // character ID
    protected String              name;        // character name
    protected String              description; // character description
    protected Place               currPlace;   // current place of character
    protected DecisionMaker       dm;          // decision maker
    protected ArrayList<Artifact> artifacts;   // collection of artifacts
    protected static TreeMap<Integer, Character> characters;// static collection


    // initialize static attributes
    static { characters = new TreeMap<Integer, Character>(); }


    // primary constructor for Character class
    public Character(Scanner sc, int ver) {
        if (ver < 4) return;                           // unsupported version
        try {
            artifacts     = new ArrayList<Artifact>(); // allocate artifacts
            Scanner s     = new Scanner(CleanLineScanner.getCleanLine(sc));
            int place     = s.nextInt();               // get place ID
            s.close();                                 // close scanner

            if (place > 0)                             // place > 0 :
                currPlace = Place.getPlaceByID(place); //   set given  place
            else                                       // place = 0 :
                currPlace = Place.getRandomPlace();    //   set random place

            s             = new Scanner(CleanLineScanner.getCleanLine(sc));
            ID            = s.nextInt();                      // set ID
            name          = CleanLineScanner.getCleanLine(s); // set name
            s.close();                                        // close scanner

            s             = new Scanner(CleanLineScanner.getCleanLine(sc));
            int num       = s.nextInt();               // get # of lines
            s.close();                                 // close scanner

            description   = "";
            for (int i = 0; i < num; i++) {            // set desc :
                if (i < num - 1)                       //   preceding line
                    description += CleanLineScanner.getCleanLine(sc) + "\n";
                else                                   //   last line
                    description += CleanLineScanner.getCleanLine(sc);
            }//end for...

            currPlace.addCharacter(this);              // add to place
            if (!characters.containsKey(ID))           // non-duplicate :
                characters.put(ID, this);              //   add to collection
        } catch (Exception e) { e.printStackTrace(); } // exception
    }//end class constructor


    // overloaded constructor for Character class to support pre-4.0
    // backward compatibility
    public Character(int _id, String _name, String _desc) {
        artifacts   = new ArrayList<Artifact>(); // allocate artifacts

        ID          = _id;                       // set ID
        name        = _name;                     // set name
        description = _desc;                     // set desc
        setCurrentPlace(Place.getEntryPlace());  // set current place

        currPlace.addCharacter(this);            // add to place
        if (!characters.containsKey(ID))         // non-duplicate :
            characters.put(ID, this);            //   add to collection
    }//end overloaded constructor


    // return character ID
    public int      ID()                       { return ID;               }
    // return character name
    public String   name()                     { return name;             }
    // return character description
    public String   description()              { return description;      }
    // return character's current place name
    public String   placeName()                { return currPlace.name(); }
    // return character's current place
    public Place    getCurrentPlace()          { return currPlace;        }
    // set    character's current place
    public void     setCurrentPlace(Place p)   { currPlace = p;           }
    // add artifact to collection of artifacts
    public void     addArtifact(Artifact a)    { artifacts.add(a);        }
    // remove artifact from collection of artifacts
    public void     removeArtifact(Artifact a) { artifacts.remove(a);     }
    // return artifact from collection of artifacts
    public Artifact getArtifact()              {
        if (artifacts.size() > 0) return artifacts.get(0);
        else                      return null;
    }
    // abstract method : make move
    public abstract boolean makeMove();


    // return character associated with given ID
    public static Character getCharacterByID(int ID) {
        return characters.get(ID);
    }//end getCharacterByID()


    // display character info for use in game play
    public void display() {
        // print name + desc
        UI.printFormat(String.format("In your midst is %s.\n%s",
                       name().replace("The ", "the ").replace("A ", "a "),
                       description()));
    }//end display()


    // debugging : print character info
    public void print() {
        // print ID, name + desc
        UI.printFormat(String.format("C%-5s%s\n%s", ID(), name(),
                       description().replaceAll("(?m)^", "      ")));
    }//end print()
}//end Character abstract class
