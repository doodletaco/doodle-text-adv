    import java.util.Scanner;
    
    /**
     * The control class for a Text Adventure
     * 
     * @author dahlem.brian
     */
    public class TextAdventure {
        private Scanner scan;
        private Player player;
        private Location[] room;
        
        /**
         * Create the world of the text adventure
         */
        public TextAdventure() {
            // Initialize the scanner to get user input
            scan = new Scanner(System.in);
            
            // Prompt for the player's name
            System.out.println("\fWelcome adventurer! Please state your name.");
            System.out.print("> ");
            
            String name = scan.nextLine();
            
            // Confirm the player's name and create the player
            System.out.println("Hello, noble adventurer " + name + ".");        
            player = new Player(name);
            
            // Build the locations in this world
            makeRooms();
        }
        
        /**
         * Build all of the locations in this adventure
         */
        private void makeRooms() {
            // Create an array to hold all of the locations
            room = new Location[3];
            
            // Create the rooms
            room[0] = new Location("a clearing", 
                                     "Trees tower above you in all directions.\n"
                                        + " As you examine the ground, you can make out the faint trace of a path heading to the South.");        
            room[1] = new Path();
            
            room[2] = new Location("the busseling port of Nav",
                                        "There is a market to your east. \n"
                                        + "You can see faint ruins in the West, and an outpost in the South.");
          // Connect the rooms
            room[0].connectTo(room[1], "South"); // You can go North from room0 to room1
            room[1].connectTo(room[0], "North"); // You can go South from room1 to room0
            room[1].connectTo(room[0], "Die"); // If you die in room1, you go to room0
            room[1].connectTo(room[2], "South");
            room[2].connectTo(room[1], "North");
            room[2].connectTo(room[1], "Die");
        }
        
        /**
         * The main loop for the text adventure
         */
        public void start() {
            Location currentLocation = room[0];
            player.moveTo(currentLocation);        
    
            while (true) {
                // Display a prompt
                System.out.print("> ");
                
                // Get the user's command
                String command = scan.nextLine();
                command = command.trim().toUpperCase();
                
                // Handle a quit command
                if (command.equals("QUIT") || command.equals("Q")) {
                    break;
                }
                
                // Handle any other command
                String result = currentLocation.doCommand(command, player);
                if (result.equals("DEATH")) {
                    player.moveTo(room[0]);
                }
                
                // Update the currentLocation in case the player has moved due 
                // to the command
                currentLocation = player.getLocation();
                currentLocation.turnDone(player);
                            
                // Update the currentLocation in case the player has moved
                // after their turn ended
                currentLocation = player.getLocation();
            }
            
            // If the loop ends, quit.
            System.out.println("Fairwell good sir!");
        }
        
        /**
         * The main entry point for the text adventure program.
         * 
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            // Create the adventure game
            TextAdventure game = new TextAdventure();
            
            // Start the game
            game.start();              
        }
}