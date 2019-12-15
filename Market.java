/**
 * The market of nav
 *
 * Notice that Market EXTENDS Location, and calls super in its constructor,
 * doCommand, and other Overridden methods. This lets a Market use the 
 * features that Location provides, and add to them.
 * 
 * @author Thomas French
 */
public class Market extends Location
{
    private int numTurns;
    private boolean bone;
    private Location respawn;
    
    /**
     * Constructor for objects of class Market
     */
    public Market()
    {
        // Initialize the location
        super("a marketplace",
                 "There are crowds bustling trying to buy the latest merchandise.\n"
                 + "There are two paths in front of you. One on the left, and the other is on the right."); // Set up the Location's parameters 
        
        // initialise the instance variables for this path
        numTurns = 0;
        bone = true;
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
            && (words[0].equals("TAKE") || words[0].equals("GET") || words[0].equals("GO"))) {
            words[0] = words[1];
        }
        switch (words[0]) {
            // If the user picks up the bone
            case "BONE":
                player.giveItem("BONE");
                System.out.println("You have picked up a bone. As you hold it you feel slobber on it.");
                System.out.println("You give the dog guarding the path the bone, he looks content enough to let you pass.");
                bone = false;
                result = "TAKE";
                break;
                
            case "WEST":
                if (bone)
                {
                    System.out.println("The dog will not let you pass");
                }
                else
                {
                    player.moveTo(connectedLocation(words[0]));
                    result = "MOVE";
                }
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
        
        // If the player has spent two turns in here, the dog attacks!
        if (bone)
        {
            if (numTurns > 2) {
                System.out.println("The dog guarding the left path runs.\n"
                                    + " He jumps out fangs bared.\n"
                                    + " You are torn apart as people watch.\n"
                                    + " Everything goes dark.");
        
                player.setLocation(respawn);
            }
            else if (numTurns > 1) {
                System.out.println("The dog looks at you and growls.\n" 
                                    + " You get the feeling that he is angry with you.");
                
            }
            else if (numTurns > 0) {
                System.out.println("The dog looks at you.");
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
        
        // If the player hasn't picked up the bone, report its presence
        if (bone) {
            System.out.println("There is a bone lying in front of you as a ferocious looking dog blocking the left path looks at you hungrily.");
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
        if (bone) { // only display if there is an item to take
            System.out.println("TAKE [item]");
            if (bone) {
                System.out.println("    Bone");
            }
        }
    }
}
