/**
 * A Tavern is a special location that has a limited number of moves before
 * the trees attack.
 *
 * Notice that Tavern EXTENDS Location, and calls super in its constructor,
 * doCommand, and other Overridden methods. This lets a Tavern use the 
 * features that Location provides, and add to them.
 * 
 * @author Thomas French
 */
public class Tavern extends Location
{
    private int numTurns;
    private boolean strangeMan;
    private Location respawn;
    
    /**
     * Constructor for objects of class Tavern
     */
    public Tavern()
    {
        // Initialize the location
        super("the Rhyming Orange Tavern",
                 "It seems very lively.\n"
                 + " You wonder if some sort of event is going on."); // Set up the Location's parameters 
        
        // initialise the instance variables for this path
        numTurns = 0;
        strangeMan = true;
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
            && words[0].equals("TALK") && words[1].equals("TO")) {
           words[0] = words[2]; 
        }
        
        switch (words[0]) {
            // If the user talks to the strange man
            case "STRANGEMAN":
                player.giveItem("STRANGEMAN");
                System.out.println("You realize that the strange man is from the empire and ask him for a way to stop Salame, he is afraid of you and tells you that the ancient sword Excalibur rests in the dungeon North of Buko.");
                System.out.println("He also tells you that Salame resides South of Buko.");
                strangeMan = false;
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
        
        // If the player has spent two turns in here, the man stabs you!
        if (numTurns > 2) {
            System.out.println("The strange man gets up.\n"
                                + " He charges you with a knife in his hand.\n"
                                + " He keeps stabbing you in the chest until you can't breathe.\n"
                                + " Everything goes dark.");

            player.setLocation(respawn);
        }
        else if (numTurns > 1) {
            System.out.println("The strange man looks you in the eye.\n" 
                                + " You get the feeling that the strange man knows who you are.");
            
        }
        else if (numTurns > 0) {
            System.out.println("The strange man looks at you.");
        }      
        
        numTurns = numTurns + 1;        
    }
    
    /**
     * Called whenever the player looks
     */
    @Override
    public void look() {
        super.look(); // Use the location's look
        
        // If the player hasn't talked to the strange man, report its presence
        if (strangeMan) {
            System.out.println("There is a strange man sitting at the bar dressed in all black.");
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
        if (strangeMan) { // only display if there is an item to take
            System.out.println("TALK [person]");
            if (strangeMan) {
                System.out.println("    StrangeMan");
            }
        }
    }
}
