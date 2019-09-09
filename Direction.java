/* CS342 Term Project Part III: Addition of Characters and Inheritance
 * Name:   Shyam Patel
 * NetID:  spate54
 * Date:   Oct 24, 2018
 */

import java.util.Scanner;


// Direction class to encapsulate attributes and methods for Direction objects
public class Direction {
    // private attributes
    private int     ID;          // direction ID
    private DirType dir;         // direction type
    private Place   from;        // direction source
    private Place   to;          // direction destination
    private boolean isLocked;    // locked status
    private int     lockPattern; // direction lock pattern


    // DirType enumerator class to keep track of direction types
    private enum DirType {
        NONE ("NONE",            "NONE"),
        N    ("NORTH",           "N"   ),
        S    ("SOUTH",           "S"   ),
        E    ("EAST",            "E"   ),
        W    ("WEST",            "W"   ),
        U    ("UP",              "U"   ),
        D    ("DOWN",            "D"   ),
        NE   ("NORTHEAST",       "NE"  ),
        NW   ("NORTHWEST",       "NW"  ),
        SE   ("SOUTHEAST",       "SE"  ),
        SW   ("SOUTHWEST",       "SW"  ),
        NNE  ("NORTH-NORTHEAST", "NNE" ),
        NNW  ("NORTH-NORTHWEST", "NNW" ),
        ENE  ("EAST-NORTHEAST",  "ENE" ),
        WNW  ("WEST-NORTHWEST",  "WNW" ),
        ESE  ("EAST-SOUTHEAST",  "ESE" ),
        WSW  ("WEST-SOUTHWEST",  "WSW" ),
        SSE  ("SOUTH-SOUTHEAST", "SSE" ),
        SSW  ("SOUTH-SOUTHWEST", "SSW" );

        // private attributes
        private String text, abbreviation;

        // constructor for DirType enumerator
        DirType(String t, String a) {
            text         = t;
            abbreviation = a;
        }//end constructor

        // return stored direction text
        public String   toString()        { return text;         }
        // return stored direction abbreviation
        public String   toAbbreviation()  { return abbreviation; }
        // return true if passed string matches stored text or abbreviation
        private boolean match(String dir) {
            return dir.equals(text) || dir.equals(abbreviation);
        }//end match()
    }//end DirType enumerator


    // constructor for Direction class
    public Direction(Scanner sc, int ver) {
        if (ver < 1) return;                             // unsupported version
        try {
            Scanner s = new Scanner(CleanLineScanner.getCleanLine(sc));
            ID        = s.nextInt();                     // set ID
            from      = Place.getPlaceByID(s.nextInt()); // set source
            dir       = DirType.valueOf(s.next());       // set direction type
            int dest  = s.nextInt();                     // get dest ID

            if (dest > 0) {                              // dest > 0 :
                to    = Place.getPlaceByID(dest);        //   set dest
                unlock();                                //   set unlocked
            }
            else {                                       // dest < 0 :
                to    = Place.getPlaceByID(-dest);       //   set dest
                lock();                                  //   set locked
            }

            lockPattern = s.nextInt();                   // set lock pattern
            s.close();                                   // close scanner
            from.addDirection(this);                     // add to source
        } catch (Exception e) { e.printStackTrace(); }   // exception
    }//end class constructor


    // return direction ID
    public int     ID()              { return ID;                   }
    // return direction abbreviation
    public String  abbreviation()    { return dir.toAbbreviation(); }
    // return direction source
    public Place   source()          { return from;                 }
    // return direction destination
    public Place   destination()     { return to;                   }
    // return direction destination name
    public String  destinationName() { return to.name();            }
    // return direction lock pattern
    private int    pattern()         { return lockPattern;          }
    // return true if direction has lock
    public boolean hasLock()         { return pattern() != 0;       }
    // return true if direction is locked
    public boolean isLocked()        { return isLocked;             }
    // return true if direction destination isn't nowhere or exit
    public boolean isValid()         { return to.ID() != 0 && to.ID() != 1; }
    // return true if passed string matches stored direction
    public boolean match(String str) { return dir.match(str);       }
    // lock direction
    public void    lock()            { isLocked = true;             }
    // unlock direction
    public void    unlock()          { isLocked = false;            }


    // toggle direction lock and return true if unlocked
    private boolean toggle() {
        if (isLocked()) { // locked :
            unlock();     //   set unlocked
            return true;  //   return true
        }
        else {            // unlocked :
            lock();       //   set locked
            return false; //   return false
        }
    }//end toggle()


    // if direction is unlocked, return direction destination
    public Place follow() {
        if (!isLocked())                                  // unlocked :
            return destination();                         //   return dest

        else {                                            // locked :
            if (destination() == Place.getPlaceByID(0))   //   dest is nowhere
                System.out.printf("Sorry, the door to nowhere " +
                                  "is locked forever.\n");
            else if (destinationName().matches("Room.*")) //   dest is room
                System.out.printf("Sorry, the door to %s is locked.\n",
                                  destinationName());
            else                                          //   dest isn't room
                System.out.printf("Sorry, the door to the %s is locked.\n",
                                  destinationName());

            return source();                              //   return source
        }//end else...
    }//end follow()


    // return matching direction string
    public static String matchDirection(String str) {
        // iterate thru DirType enumerator constants
        for (DirType dt : DirType.values())         // iterate thru DirTypes :
            if (str.equals(dt.toString()) || str.equals(dt.toAbbreviation()))
                return dt.toString().toLowerCase(); //   return match

        return "\'" + str.toLowerCase() + "\'";     // return no match
    }//end matchDirection()


    // return true if passed string has valid direction
    public static boolean isDirection(String str) {
        // iterate thru DirType enumerator constants
        for (DirType dt : DirType.values()) // iterate thru DirTypes :
            if (str.equals(dt.toString()) || str.equals(dt.toAbbreviation()))
                return true;                //   return true

        return false;                       // return false
    }//end isDirection()


    // pass artifact to useKey() method of all directions present in place
    public boolean useKey(Artifact a) {
        if (hasLock()) {                                 // has lock :
            if (a.matchKey(pattern())) {                 //   patterns match :
                if (toggle())                            //     unlock if locked
                    System.out.printf("You\'ve used the %s to unlock the door ",
                                      a.name().toLowerCase());
                else                                     //     lock if unlocked
                    System.out.printf("You\'ve used the %s to lock the door ",
                                      a.name().toLowerCase());
                if (destinationName().matches("Room.*")) //     dest is room
                    System.out.printf("to %s.\n",     destinationName());
                else                                     //     dest isn't room
                    System.out.printf("to the %s.\n", destinationName());
            }//end if...
            else {                                       //   no match
                System.out.printf("Sorry, the %s doesn\'t unlock the door ",
                                  a.name().toLowerCase());
                if (destinationName().matches("Room.*")) //     dest is room
                    System.out.printf("to %s.\n",     destinationName());
                else                                     //     dest isn't room
                    System.out.printf("to the %s.\n", destinationName());
            }
            return true;
        }//end if...                                     // dest is nowhere
        else if (isLocked() && destination() == Place.getPlaceByID(0)) {
            System.out.printf("Sorry, the %s can\'t unlock the door " +
                              "to nowhere.\n", a.name().toLowerCase());
            return true;
        }//end else if...
        return false;                                    // has no lock
    }//end useKey()


    // debugging : print direction information
    public void print() {
        if (!isLocked())         // print ID, direction + dest
            UI.printFormat(String.format("D%-5s%3s to %s",
                           ID(), abbreviation(), destinationName()));
        else if (pattern() == 0) // +locked
            UI.printFormat(String.format("D%-5s%3s to %s, locked",
                           ID(), abbreviation(), destinationName()));
        else                     // +lock pattern
            UI.printFormat(String.format("D%-5s%3s to %s, locked (%d)",
                           ID(), abbreviation(), destinationName(), pattern()));
    }//end print()
}//end Direction class
