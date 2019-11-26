package ca.frar.keystroke;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public abstract class GlobalKeyListener implements NativeKeyListener {

    public abstract void onKey(String keyText);
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public void start() throws NativeHookException{
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }
    
    public void stop() throws NativeHookException{
        GlobalScreen.removeNativeKeyListener(this);
        GlobalScreen.unregisterNativeHook();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {       
        System.out.println("press " + e.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {     
        System.out.println("release " + e.getKeyText(e.getKeyCode()));
        this.onKey(e.getKeyText(e.getKeyCode()));
    }
}
