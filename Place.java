/* CS342 Term Project Part III: Addition of Characters and Inheritance
 * Name:   Shyam Patel
 * NetID:  spate54
 * Date:   Oct 24, 2018
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


// Place class to encapsulate attributes and methods for Place objects
public class Place {
    // private attributes
    private int                  ID;              // place ID
    private String               name;            // place name
    private String               description;     // place description
    private ArrayList<Direction> directions;      // collection of directions
    private ArrayList<Character> characters;      // collection of characters
    private ArrayList<Artifact>  artifacts;       // collection of artifacts
    private static Place         entryPlace;      // static entry place
    private static TreeMap<Integer, Place> places;// static collection of places


    // initialize static attributes
    static {
        entryPlace = null;
        places     = new TreeMap<Integer, Place>();
    }//end static


    // primary constructor for Place class that reads from gdf
    public Place(Scanner sc, int ver) {
        if (ver < 1) return;                           // unsupported version
        try {
            directions = new ArrayList<Direction>();   // allocate directions
            characters = new ArrayList<Character>();   // allocate characters
            artifacts  = new ArrayList<Artifact>();    // allocate artifacts

            Scanner s  = new Scanner(CleanLineScanner.getCleanLine(sc));
            ID         = s.nextInt();                      // set ID
            name       = CleanLineScanner.getCleanLine(s); // set name
            s.close();                                     // close scanner

            s          = new Scanner(CleanLineScanner.getCleanLine(sc));
            int num    = s.nextInt();                  // get # of lines
            s.close();                                 // close scanner

            description  = "";
            for (int i = 0; i < num; i++) {            // set desc :
                if (i < num - 1)                       //   preceding line
                    description += CleanLineScanner.getCleanLine(sc) + "\n";
                else                                   //   last line
                    description += CleanLineScanner.getCleanLine(sc);
            }

            if (!places.containsKey(ID))               // non-duplicate :
                places.put(ID, this);                  //   add to collection
            if (entryPlace == null) entryPlace = this; // set entry place
        } catch (Exception e) { e.printStackTrace(); } // exception
    }//end primary constructor


    // overloaded constructor for Place class
    public Place(int _id, String _name, String _desc) {
        directions  = new ArrayList<Direction>(); // allocate directions
        characters  = new ArrayList<Character>(); // allocate characters
        artifacts   = new ArrayList<Artifact>();  // allocate artifacts

        ID          = _id;                        // set ID
        name        = _name;                      // set name
        description = _desc;                      // set desc

        if (!places.containsKey(ID))              // non-duplicate :
            places.put(ID, this);                 //   add to collection
    }//end overloaded constructor


    // return place ID
    public int    ID()                       { return ID;             }
    // return place name
    public String name()                     { return name;           }
    // return place description
    public String description()              { return description;    }
    // add direction to collection of directions
    public void addDirection(Direction d)    { directions.add(d);     }
    // add character to collection of characters
    public void addCharacter(Character c)    { characters.add(c);     }
    // add artifact to collection of artifacts
    public void addArtifact(Artifact a)      { artifacts.add(a);      }
    // remove character from collection of characters
    public void removeCharacter(Character c) { characters.remove(c);  }
    // remove artifact from collection of artifacts
    public void removeArtifact(Artifact a)   { artifacts.remove(a);   }
    // return place associated with given ID
    public static Place getPlaceByID(int ID) { return places.get(ID); }
    // return entry place
    public static Place getEntryPlace()      { return entryPlace;     }
    // return artifact from collection of artifacts
    public Artifact getArtifact()            {
        if (artifacts.size() > 0) return artifacts.get(0);
        else                      return null;
    }//end getArtifact()


    // return random place other than nowhere and exit
    public static Place getRandomPlace() {
        // make list of place IDs
        ArrayList<Integer> IDs = new ArrayList<Integer>(places.keySet());
        // get random ID from list (excluding nowhere and exit)
        int randomID = IDs.get(2 + new Random().nextInt(IDs.size() - 2));

        return places.get(randomID);   // return place
    }//end getRandomPlace()


    // check if place has valid unlocked direction corresponding to passed
    // string and, if so, return destination
    public Place followDirection(String str) {
        for (Direction d : directions) // iterate thru directions :
            if (d.match(str))          //   match found :
                return d.follow();     //     return dest

        System.out.printf("Sorry, can't go %s.\n",
                          Direction.matchDirection(str));
        return this;                   // no match : return this
    }//end followDirection()


    // return random valid unlocked direction
    public Place getRandomDestination() {
        Collections.shuffle(directions);      // shuffle directions
        for (Direction d : directions)        // iterate thru directions :
            if (!d.isLocked() && d.isValid()) //   unlocked :
                return d.follow();            //     return dest

        return this;                          // none found : return this
    }//end getRandomDestination()


    // check if place has valid artifact corresponding to passed string
    // and, if so, return list of matches
    public ArrayList<Artifact> followArtifact(String str) {
        ArrayList<Artifact> matches = new ArrayList<Artifact>();

        // iterate thru place's collection of artifacts
        for (Artifact a : artifacts) {
            if (a.match(str)) {     // exact match found :
                matches.add(a);     //   add it to list of matches,
                return matches;     //   and return it
            }//end if...
        }//end for...

        // iterate thru place's collection of artifacts
        for (Artifact a : artifacts) {
            if (a.matchTokens(str)) // potential match found :
                matches.add(a);     //   add it to list of matches
        }//end for...

        return matches;             // return all matches
    }//end followArtifact()


    // pass artifact to useKey() method of all directions present in place
    public boolean useKey(Artifact a) {
        boolean hasLock = false;         // lock status

        for (Direction d : directions) { // iterate thru directions :
            if (hasLock)                 //   has lock :
                d.useKey(a);             //     use key
            else                         //   doesn't have lock :
                hasLock = d.useKey(a);   //     use key + update status
        }//end for...

        return hasLock;                  // return status
    }//end useKey()


    // display place for play() method in Game class
    public void display(Character ch) {
        UI.printHeader(String.format("PLAYER %d: %s",  // name + player #
                       ((Player) ch).playerNum(), ch.name()));

        if (name().matches("Room.*"))                  // place is room
            UI.printFormat(String.format("%s, you\'re in %s!\n%s",
                           ch.name().replace("The ", "").replace("A ", ""),
                           name(), description()));
        else                                           // place isn't room
            UI.printFormat(String.format("%s, you\'re in the %s!\n%s",
                           ch.name().replace("The ", "").replace("A ", ""),
                           name(), description()));

        for (Character c : characters)   // iterate thru characters :
            if (c != ch) c.display();    //   display
        for (Artifact  a : artifacts)    // iterate thru artifacts  :
            a.display();                 //   display

        UI.printDivider(2);              // print divider
    }//end display()


    // debugging : print place information, including directions,
    //             characters and artifacts
    private void print() {
        if (name().matches("Room.*"))    // place is room
            UI.printHeader(String.format("%s", name()));
        else                             // place isn't room
            UI.printHeader(String.format("%s (P%d)", name(), ID()));

        UI.printFormat(String.format("%s", description()));

        for (Direction d : directions)   // iterate thru directions :
            d.print();                   //   print debugging info
        for (Character c : characters)   // iterate thru characters :
            c.print();                   //   print debugging info
        for (Artifact  a : artifacts)    // iterate thru artifacts  :
            a.print();                   //   print debugging info

        UI.printFormat(" ");             // newline
    }//end print()


    // debugging : print all places, including directions,
    //             characters and artifacts
    public static void printAll() {
        System.out.printf("\n\n");       // newline

        // iterate thru static collection of places
        for (Map.Entry<Integer, Place> pair : places.entrySet()) {
            Integer ID = pair.getKey();  // get place ID
            if (ID != 0 && ID != 1)      // not nowhere or exit :
                pair.getValue().print(); //   print place info
        }//end for...

        UI.printDivider(1);              // divider
    }//end printAll()
}//end Place class
