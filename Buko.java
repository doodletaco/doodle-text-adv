/**
 * A Buko is a special location that has a limited number of moves before
 * the trees attack.
 *
 * Notice that Buko EXTENDS Location, and calls super in its constructor,
 * doCommand, and other Overridden methods. This lets a Buko use the 
 * features that Location provides, and add to them.
 * 
 * @author Thomas French
 */
public class Buko extends Location
{
    private int numTurns;
    private Location respawn;
    
    /**
     * Constructor for objects of class Buko
     */
    public Buko()
    {
        // Initialize the location
        super("the small town of Buko",
                 "There is a lively crowd gathered in the center.\n"
                 + "You hear rumors of a tavern just East of you and you can barely make out a strange mountain in the distance,"
                 + "\nNorth of your current location."); // Set up the Location's parameters 
        
        // initialise the instance variables for this path
        numTurns = 0;
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
    
    public String doCommand(String command, Player player)
    {
        String result = null;        
        
        String[] words = command.split(" ");
        
        // If the user wants to pick up or take an object, the object's name
        // is the command
        if (words.length >= 3 
            && words[0].equals("PICK") && words[1].equals("UP"))
        {
           words[0] = words[2]; 
        }
        if (words.length >= 2
            && (words[0].equals("TAKE") || words[0].equals("GET") || words[0].equals("TALK")
                || words[0].equals("GO")))
        {
            words[0] = words[1];
        }
        
        switch (words[0])
        {
            // If the user wants to go to the mountains
            case "NORTH":
                if (!player.hasItem("STRANGEMAN"))
                {
                    System.out.println("You do not need to go there yet");
                    result = "BLOCK";
                }
                else
                {
                    player.moveTo(connectedLocation(words[0]));
                    result = "MOVE";
                }
        }
        // If we couldn't handle this command,
        if (result == null)
        {
            // Try out the default commands.
            result = super.doCommand(command, player);
        }
                
        return result;
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
     * Called whenever the player asks for help
     */
    @Override
    public void help() {
        // Display generic commands
        super.help();
        }
}
