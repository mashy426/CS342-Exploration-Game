#############################################################################
# CS342 Term Project Part III:                                              #
# Addition of Characters and Inheritance                                    #
# Name:   Shyam Patel                                                       #
# NetID:  spate54                                                           #
# Date:   Oct 24, 2018                                                      #
#############################################################################

This is an exploration-type game, with an interconnected network of places.
The program consists of some basic Place, Direction, Character and Artifact
classes, and a Game class to contain them. A GameTester class is also
included, which consists of the program's main() method to run and test the
game. GameTester takes command-line arguments of (1) the GDF pathname and
(2) the number of players that wish to play the game, and creates instances
of Game, Place, Direction, Character and Artifact classes to implement the
game. If the number of players is requested to be greater than what is
provided in the game data, the player(s) will be prompted to provide their
name(s). This version of the game has been updated to support GDF 4.0, which
adds characters, but is also backward compatible with previous GDF versions.
In the case that an older version is accessed, the player(s) will once again
be prompted to enter their information. In addition to players, the game
adds support for NPCs (non-player characters), who randomly traverse through
places, and randomly get and drop artifacts as they pass. The game consists
of 28 classes in total and adds inheritance. For instance, the Player and NPC
classes extend the Character class. The abstract Move class utilizes the
command design pattern to encapsulate the various command behaviors, and is
extended by the Commands, Drop, Get, Go, Inventory, Look, Quit and Use
subclasses. Among these, the DropNPC and DropPlayer subclasses extend Drop,
the GetNPC and GetPlayer subclasses extend Get, and the GoNPC and GoPlayer
subclasses extend Go. The DecisionMaker interface is implemented by the AI
and UI classes to modulate and return these moves.

#############################################################################
#  e.g., To run game with 2 players: java GameTester "MystiCity 31.gdf" 2   #
#############################################################################

When the infinite loop in the play() method of the Game class runs, the
current place is displayed, along with its description and corresponding
directions, characters and/or artifacts. Using commands, the player can:
  (1) Traverse thru locked and unlocked directions in various directions,
  (2) Get and drop artifacts, and
  (3) Use key artifacts to unlock locked directions.

The following are the eight (8) case-insensitive commands the player may
enter to operate the program:

   i. 'QUIT' or 'EXIT' – Quit the game

  ii. 'COMMANDS' – Display a list of commands

 iii. 'LOOK' – Redisplay the current place

  iv. 'GO XXX' (where XXX is a direction) – Check to see if the current place
      has an unlocked direction corresponding to the requested direction and,
      if so, update the current place to the one arrived at by following the
      corresponding direction, and then display it. If not, the program will
      print an error indicating whether (1) the requested direction is
      invalid or (2) the requested direction is locked.

      This command accepts any of the following 72 case-insensitive variants:
        'GO NORTH'              'GO SOUTH'              'GO EAST'
        'GO N'                  'GO S'                  'GO E'
        'NORTH'                 'SOUTH'                 'EAST'
        'N'                     'S'                     'E'

        'GO WEST'               'GO UP'                 'GO DOWN'
        'GO W'                  'GO U'                  'GO D'
        'WEST'                  'UP'                    'DOWN'
        'W'                     'U'                     'D'

        'GO NORTHEAST'          'GO NORTHWEST'          'GO SOUTHEAST'
        'GO NE'                 'GO NW'                 'GO SE'
        'NORTHEAST'             'NORTHWEST'             'SOUTHEAST'
        'NE'                    'NW'                    'SE'

        'GO SOUTHWEST'          'GO NORTH-NORTHEAST'    'GO NORTH-NORTHWEST'
        'GO SW'                 'GO NNE'                'GO NNW'
        'SOUTHWEST'             'NORTH-NORTHEAST'       'NORTH-NORTHWEST'
        'SW'                    'NNE'                   'NNW'

        'GO EAST-NORTHEAST'     'GO WEST-NORTHWEST'     'GO EAST-SOUTHEAST'
        'GO ENE'                'GO WNW'                'GO ESE'
        'EAST-NORTHEAST'        'WEST-NORTHWEST'        'EAST-SOUTHEAST'
        'ENE'                   'WNW'                   'ESE'

        'GO WEST-SOUTHWEST'     'GO SOUTH-SOUTHEAST'    'GO SOUTH-SOUTHWEST'
        'GO WSW'                'GO SSE'                'GO SSW'
        'WEST-SOUTHWEST'        'SOUTH-SOUTHEAST'       'SOUTH-SOUTHWEST'
        'WSW'                   'SSE'                   'SSW'

   v. 'GET <ARTIFACT>' - Get an artifact from the current place and store it
      in the player's collection of artifacts. If an artifact is not found,
      or locked because it is too heavy to move, an error message will be
      printed. The 'GET' command has been enhanced to accept key words of
      an artifact. For example, if the current place holds an artifact named
      "leather bag" and the user simply enters 'GET BAG', the program will
      assume the player desires to obtain the leather bag. If the place has
      two or more artifacts that match the user's request, the user will be
      asked to select from a list of matching choices. For example, if the
      place has a brass key and an ivory key, and the user enters 'GET KEY',
      the user will be prompted to enter '1' to get the ivory key or '2' to
      get the brass key. All artifacts currently in possession of the player
      will be visible in the user's inventory (see viii.). As soon as an
      artifact is added to the player's collection of artifacts, the player
      will be prompted to view his or her inventory, to which they can reply
      with 'YES' or 'NO'.

  vi. 'DROP <ARTIFACT>' - Drop an artifact from the player's collection of
      artifacts and leave it in the current place. If an artifact is not
      found in the player's inventory, an error message will be printed. The
      'DROP' command has also been enhanced to accept key words of artifacts
      and, when there are multiple artifacts that match the user's request,
      the user will be prompted to select from a list of matching choices.

 vii. 'USE <ARTIFACT>' - Use a key artifact to toggle any matching locks on
      all directions in the current place. If the key pattern of the artifact
      matches the lock pattern of a locked direction, the direction will be
      unlocked. Likewise, if a matching lock is found and it is unlocked, it
      will be locked. If, however, the key pattern of the artifact does not
      match the lock pattern of a locked direction, the program will print an
      error message. Errors will also be printed if attempting to use a key
      when no locked directions exist for a given place, or if attempting to
      use a non-key artifact. Like the 'GET' and 'DROP' commands, the 'USE'
      command has been enhanced to search for key words of an artifact. That
      is, if there are multiple matches, the user will be requested to select
      from a list of a choices. For example, if the user possesses all three
      keys, entering 'USE KEY' will prompt the user to enter '1' to use the
      brass key, '2' to use the ivory key, or '3' to use the golden key.

viii. 'INVE' or 'INVENTORY' - List all artifacts currently in possession of
      the player, along with the total value and weight of all artifacts.
