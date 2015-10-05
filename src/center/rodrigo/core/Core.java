package center.rodrigo.core;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

public class Core extends Listener {

    private Frame frame;

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onFrame(Controller controller) {
        frame = controller.frame();
        GestureList gestures = frame.gestures();

        // Maos
        for (Hand hand : frame.hands()) {

            Arm arm = hand.arm();
            Vector normal = hand.palmNormal();
            Vector direction = hand.direction();

            System.out.println(hand.isLeft() ? "Mao Esquerda" : "Mao Direita");

//            System.out.println("\n id: " + hand.id()
//                            + "\n Palma: " + hand.palmPosition());

//            System.out.println("\n pitch: " + Math.toDegrees(direction.pitch())
//                            + "\n roll: " + Math.toDegrees(normal.roll())
//                            + "\n yaw: " + Math.toDegrees(direction.yaw()));

//            System.out.println("\n Braço: " + arm.direction()
//                            + "\n Pulso: " + arm.wristPosition()
//                            + "\n Cotovelo: " + arm.elbowPosition());

            // Dedos
            for (Finger finger : hand.fingers()) {
                
//                 System.out.println(finger.type() 
//                                 + " id: " + finger.id()
//                                 + ", length: " + finger.length()
//                                 + "mm, width: " + finger.width() + "mm");

                // Ossos
                for (Bone.Type boneType : Bone.Type.values()) {
                    
                    Bone bone = finger.bone(boneType);
                    
//                    System.out.println("    " + bone.type()
//                                    + ", start: " + bone.prevJoint()
//                                    + ", end: " + bone.nextJoint()
//                                    + ", direction: " + bone.direction());
                }
            }
        }

        // Objetos
        for (Tool tool : frame.tools()) {
//            System.out.println("  Tool id: " + tool.id()
//                            + ", position: " + tool.tipPosition()
//                            + ", direction: " + tool.direction());
        }

        
        for (int i = 0; i < gestures.count(); i++) {
            
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
            
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);
                    String sentido;
                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 2)
                        sentido = "horario";
                    else
                        sentido = "anti-horario";
                    
                    double sweptAngle = 0;
                    if (circle.state() != State.STATE_START) {
                        CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
                        sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
                    }
    
//                    System.out.println("id: " + circle.id()
//                                        + " " + sentido
//                                        + ", " + circle.state()
//                                        + "\n progress: " + circle.progress()
//                                        + ", radius: " + circle.radius()
//                                        + ", angle: " + Math.toDegrees(sweptAngle));
                    break;
                    
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
//                    System.out.println("  Swipe id: " + swipe.id()
//                                    + ", " + swipe.state()
//                                    + ", position: " + swipe.position()
//                                    + ", direction: " + swipe.direction()
//                                    + ", speed: " + swipe.speed());
                    break;
                    
                case TYPE_SCREEN_TAP:
                    ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
//                    System.out.println("  Screen Tap id: " + screenTap.id()
//                                    + ", " + screenTap.state()
//                                    + ", position: " + screenTap.position()
//                                    + ", direction: " + screenTap.direction());
                    break;
                    
                case TYPE_KEY_TAP:
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
//                    System.out.println("  Key Tap id: " + keyTap.id()
//                                    + ", " + keyTap.state()
//                                    + ", position: " + keyTap.position()
//                                    + ", direction: " + keyTap.direction());
                    break;
                    
                default:
                    System.out.println("Unknown gesture type.");
                    break;
            }
        }
    }
}
