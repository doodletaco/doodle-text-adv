/**
 * A Path is a special location that has a limited number of moves before
 * the trees attack.
 *
 * Notice that Path EXTENDS Location, and calls super in its constructor,
 * doCommand, and other Overridden methods. This lets a Path use the 
 * features that Location provides, and add to them.
 * 
 * @author dahlem.brian
 */
public class Path extends Location
{
    private int numTurns;
    private boolean rock;
    private Location respawn;
    
    /**
     * Constructor for objects of class Path
     */
    public Path()
    {
        // Initialize the location
        super("a dead end",
                 "The path you followed seems to have ended.\n"
                 + " You hear a strange whispering in the trees."); // Set up the Location's parameters 
        
        // initialise the instance variables for this path
        numTurns = 0;
        rock = true;
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
            // If the user picks up the rock
            case "ROCK":
                player.giveItem("ROCK");
                System.out.println("You have picked up a rock. Good for you.");
                rock = false;
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
        
        // If the player has spent two turns in here, the trees attack!
        if (numTurns > 2) {
            System.out.println("The roar in the trees has become a screech.\n"
                                + " Suddenly branches swing down towards you.\n"
                                + " You attempt to dodge them but are scooped up and carried away.\n"
                                + " Everything goes dark.");

            player.setLocation(respawn);
        }
        else if (numTurns > 1) {
            System.out.println("The whispering in the trees has grown into a roar.\n" 
                                + " You get the feeling that the trees are angry with you.");
            
        }
        else if (numTurns > 0) {
            System.out.println("The whispering in the trees grows louder.");
        }      
        
        numTurns = numTurns + 1;        
    }
    
    /**
     * Called whenever the player looks
     */
    @Override
    public void look() {
        super.look(); // Use the location's look
        
        // If the player hasn't picked up the rock, report its presence
        if (rock) {
            System.out.println("There is a rock lying in front of you.");
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
        if (rock) { // only display if there is an item to take
            System.out.println("TAKE [item]");
        }
    }
}
