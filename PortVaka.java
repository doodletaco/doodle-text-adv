
/**
 * PortVaka to go to Nav and make sure one has everything
 *
 * @author doodletaco
 * @version 1.0
 */
public class PortVaka extends Location
{
    // instance variables - replace the example below with your own
    private boolean sailor;

    /**
     * Constructor for objects of class Barracks
     */
    public PortVaka()
    {
        // initialize location
        super("the port", "Approaching the waters, some men are preparing to take you out to sea.");
        
        //initialize instance variables
        sailor = true;
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
            && (words[0].equals("TAKE") || words[0].equals("GET") || words[0].equals("TALK")))
        {
            words[0] = words[1];
        }
        
        switch (words[0])
        {
            case "SAILOR":
                if (player.hasItem("AuthorizationNote"))
                {
                    System.out.println("Let's get goin, hop on the boat when you are prepared." +
                    "\nWe are headin' South.");
                }
                else
                {
                    System.out.println("I believe the Captain has something to give you." +
                        "\nGo get it real quick. We can wait.");
                }
                sailor = false;
                result = "TALK";
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
        
        // Add any other room specific commands here
        
        if (sailor)
        {
            // only display if there is a person to talk to
            System.out.println("TALK [person]");
            if (sailor)
            {
                System.out.println("    Sailor");
            }
        }
    }
}