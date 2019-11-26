/*
 * see https://stackoverflow.com/questions/15260282/converting-a-char-into-java-keyevent-keycode
 */
package ca.frar.keystroke;

import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 *
 * @author ed
 */
public class KeyStroke {
    private int code;
    private boolean isShifted;

    public KeyStroke(int keyCode, boolean shift) {
        code = keyCode;
        isShifted = shift;
    }

    public void stroke(Robot robot) {
        if (isShifted) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        robot.keyPress(code);
        robot.keyRelease(code);

        if (isShifted) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
}
