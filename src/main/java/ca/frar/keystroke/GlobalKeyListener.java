package ca.frar.keystroke;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener, Runnable{    
    private Consumer<String> onKey;
    
    public GlobalKeyListener(Consumer<String> onKey){
        this.onKey = onKey;
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public void run() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException ex) {
            Logger.getLogger(GlobalKeyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stop(){
        try {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(GlobalKeyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {       
//        System.out.println("press " + e.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {     
//        System.out.println("release " + e.getKeyText(e.getKeyCode()));
        this.onKey.accept(e.getKeyText(e.getKeyCode()));
    }
}
