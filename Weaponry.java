/**
 * THe weaponry of the market. you can buy weapons
 *
 * Notice that Weaponry EXTENDS Location, and calls super in its constructor,
 * doCommand, and other Overridden methods. This lets a Weaponry use the 
 * features that Location provides, and add to them.
 * 
 * @author Thomas French
 */
public class Weaponry extends Location
{
    private int numTurns;
    private boolean note;
    private Location respawn;
    
    /**
     * Constructor for objects of class Weaponry
     */
    public Weaponry()
    {
        // Initialize the location
        super("a weaponry",
                 "You see a large weapon room filled with all sorts of weapons and armor.\n"
                 + " The air feels ominous because there is no one in the room."); // Set up the Location's parameters 
        
        // initialise the instance variables for this path
        numTurns = 0;
        note = true;
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
            // If the user picks up the note
            case "NOTE":
                player.giveItem("NOTE");
                System.out.println("You have picked up the note, it reads as follows: \"The key to protection lays in the faint ruins to the West.\"");
                note = false;
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
     * Called whenever the player looks
     */
    @Override
    public void look() {
        super.look(); // Use the location's look
        
        // If the player hasn't picked up the note, report its presence
        if (note) {
            System.out.println("There is a note on a fancy piece of armor.");
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
        if (note) { // only display if there is an item to take
            System.out.println("TAKE [item]");
            if(note) {
                System.out.println("    Note");
            }
        }
    }
}
