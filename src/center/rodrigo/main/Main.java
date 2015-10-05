package center.rodrigo.main;

import java.io.IOException;
import com.leapmotion.leap.Controller;
import center.rodrigo.core.Core;

public class Main {

    public static void main(String[] args) {

        Core core = new Core();
        Controller controller = new Controller();

        controller.addListener(core);

        System.out.println("Press Enter to quit...");
        
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.removeListener(core);
    }
}
