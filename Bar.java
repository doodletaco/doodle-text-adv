
/**
 * A location where you can speak with a friend, and grab your pack.
 *
 * @author doodletaco
 * @version 1.0
 */
public class Bar extends Location
{
    // instance variables - replace the example below with your own
    private boolean person1;
    private boolean person2;

    /**
     * Constructor for objects of class Bar
     */
    public Bar()
    {
        // initialize location
        super("the Drowning Turtle Tavern", "It's specialty is turtle soup."
              + "\nThose around you are gossiping and seem open to conversation.");
        
        //initialize instance variables
        person1 = true;
        person2 = true;
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
            case "PERSON1":
                System.out.println("You approach a person who looks like they are well travelled about Salame." +
                                  "He looks over you, and replies \"I dunno, honestly, but going Eastwards to Buko would be smart." +
                                  "you are horribly unequipped.\"");
                person1 = false;
                player.giveItem("GOWEST");
                if (!player.hasItem("ARMOR"))
                {
                    System.out.println("\"I heard there was some armor on this island, you should get that first, though.\"");
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
     * Called whenever the player looks
     */
    @Override
    public void look()
    {
        super.look(); // Use the location's look
        
        // If the player hasn't picked up the rock, report its presence
        if (person1)
        {
            System.out.println("One of the men talking stand out, and he doesn't currently seem too Occupied.");
        }
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
        
        if (person1)
        {
            // only display if there is a person to talk to
            System.out.println("TALK [person]");
            if (person1)
            {
                System.out.println("    PERSON1");
            }
        }
    }
}