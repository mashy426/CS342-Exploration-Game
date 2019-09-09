/* CS342 Term Project Part III: Addition of Characters and Inheritance
 * Name:   Shyam Patel
 * NetID:  spate54
 * Date:   Oct 24, 2018
 */

import java.util.Scanner;


// CleanLineScanner class to parse thru and return clean lines
public final class CleanLineScanner {
    // return next clean line from scanner
    public static String getCleanLine(Scanner sc) {
        String str = "";                      // empty line

        while (str.length() == 0) {           // get next line until empty
            if (sc.hasNextLine()) {           //   has next line :
                str = sc.nextLine();          //     get next line

                if (str.matches(".*//.*"))    //     has comments :
                    str = str.split("//")[0]; //       remove comments

                str = str.trim();             //     trim whitespace
            }//end if...
        }//end loop

        return str;                           // return clean line
    }//end getCleanLine()
}//end CleanLineScanner class
