
/**
 * A location that acts as connection between the market and the ruins
 *
 * @author doodletaco
 * @version 1.0
 */
public class Nav extends Location
{
    // instance variables - replace the example below with your own
    
    /**
     * Constructor for objects of class Nav
     */
    public Nav()
    {
        // initialize location
        super("the port of Nav" , "The roads are full of people buying and selling goods."
                + "\nThe mountains in the West tower above the horizon."
                + "\nThe market is South of you.");
        
        //initialize instance variables
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
            // If the user picks up the pack
            case "WEST":
                if (player.hasItem("NOTE"))
                {
                    player.moveTo(connectedLocation(words[0]));
                    result = "MOVE";
                }
                else
                {
                    System.out.println("The mountains are that way, but you aren't sure what you are looking for there.");
                    result = "BLOCK";
                }
                break;
                
            case "EAST":
                if (player.hasItem("GOEAST"))
                {
                    player.moveTo(connectedLocation(words[0]));
                    result = "MOVE";
                }
                else
                {
                    System.out.println("There is no reason for you to go that way.");
                    result = "BLOCK";
                }
                break;
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
     * Called whenever the player looks
     */
    @Override
    public void look()
    {
        super.look(); // Use the location's look
    }
    
    /**
     * Called whenever the player asks for help
     */
    @Override
    public void help()
    {
        // Display generic commands
        super.help();
    }
}