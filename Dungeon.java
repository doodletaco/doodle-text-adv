/**
 * A Dungeon is a special location that has a limited number of moves before
 * the rocks fall.
 *
 * Notice that Dungeon EXTENDS Location, and calls super in its constructor,
 * doCommand, and other Overridden methods. This lets a Dungeon use the 
 * features that Location provides, and add to them.
 * 
 * @author Thomas French
 */
public class Dungeon extends Location
{
    private int numTurns;
    private boolean excalibur;
    private Location respawn;
    
    /**
     * Constructor for objects of class Dungeon
     */
    public Dungeon()
    {
        // Initialize the location
        super("are in a dungeon",
                 "There is a staircase leading down into the depths.\n"
                 + " At the bottom of the steps is a large chamber."); // Set up the Location's parameters 
        
        // initialise the instance variables for this path
        numTurns = 0;
        excalibur = true;
    }
    
    /**
     * Establish a connection to another location if the player moves in a
     * given direction.
     */
    @Override
    public void connectTo(Location other, String direction) {
        direction = direction.toUpperCase();
        if (direction.equals("DIE")) {
            respawn = other;
        }
        else {
            // Use the default connectTo for other directions
            super.connectTo(other, direction);
        }
    }
    
    /**
     * Called when the player enters this location.
     */
    @Override
    public void enter(Player player)
    {
        // Reset the turn counter
        numTurns = 0;
        
        super.enter(player); // call the location's enter command
    }
    
    /**
     * Handle a command that the player has typed
     */
    @Override
    public String doCommand(String command, Player player)
    {
        String result = null;        
        
        String[] words = command.split(" ");
        
        // If the user wants to pick up or take an object, the object's name
        // is the command
        if (words.length >= 3 
            && words[0].equals("PICK") && words[1].equals("UP")) {
           words[0] = words[2]; 
        }
        if (words.length >= 2
            && (words[0].equals("TAKE") || words[0].equals("GET"))) {
            words[0] = words[1];
        }
        
        switch (words[0]) {
            // If the user picks up excalibur
            case "EXCALIBUR":
                player.giveItem("EXCALIBUR");
                System.out.println("You open the coffin and take excalibur from the corpse that is holding it.");
                excalibur = false;
                result = "TAKE";
        }
        
        // If we couldn't handle this command,
        if (result == null) {
            // Try out the default commands.
            result = super.doCommand(command, player);
        }
                
        return result;
    }
    
    /**
     * Called when the player's turn is over
     */
    @Override
    public void turnDone(Player player) {
        
        // If the player has spent two turns in here, there is a cave-in!
        if (excalibur)
        {
            if (numTurns > 2) {
                System.out.println("The room that you are in shakes violently.\n"
                                    + " Suddenly Rocks start falling from the ceiling.\n"
                                    + " You attempt to dodge them but are crushed.\n"
                                    + " Everything goes dark.");
    
                player.setLocation(respawn);
            }
            else if (numTurns > 1) {
                System.out.println("Cave begins to shake.\n" 
                                    + " You get the feeling that it might collapse.");
                
            }
            else if (numTurns > 0) {
                System.out.println("Pebbles fall from ceiling.");
            }      
            
            numTurns = numTurns + 1;        
        }
    }
    
    /**
     * Called whenever the player looks
     */
    @Override
    public void look() {
        super.look(); // Use the location's look
        
        // If the player hasn't picked up the excalibur, report its presence
        if (excalibur) {
            System.out.println("There is a coffin enscribed: \"Here lies Excalibur.\".");
        }
    }
    
    /**
     * Called whenever the player asks for help
     */
    @Override
    public void help() {
        // Display generic commands
        super.help();
        
        // Add any other room specific commands here
        if (excalibur) { // only display if there is an item to take
            System.out.println("TAKE [item]");
            if(excalibur) {
                System.out.println("    Excalibur");
            }
        }
    }
}
