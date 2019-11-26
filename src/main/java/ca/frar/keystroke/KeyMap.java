/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.frar.keystroke;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author ed
 */
public class KeyMap extends HashMap<Character, KeyStroke> {

    public KeyMap() {
        put('\n', new KeyStroke(KeyEvent.VK_ENTER, false));
        put('\t', new KeyStroke(KeyEvent.VK_TAB, false));
        put('\r', new KeyStroke(KeyEvent.VK_HOME, false));
        put(' ', new KeyStroke(KeyEvent.VK_SPACE, false));
        put('!', new KeyStroke(KeyEvent.VK_1, true));
        put('"', new KeyStroke(KeyEvent.VK_QUOTE, true));
        put('#', new KeyStroke(KeyEvent.VK_3, true));
        put('$', new KeyStroke(KeyEvent.VK_4, true));
        put('%', new KeyStroke(KeyEvent.VK_5, true));
        put('&', new KeyStroke(KeyEvent.VK_7, true));
        put('\'', new KeyStroke(KeyEvent.VK_QUOTE, false));
        put('(', new KeyStroke(KeyEvent.VK_9, true));
        put(')', new KeyStroke(KeyEvent.VK_0, true));
        put('*', new KeyStroke(KeyEvent.VK_8, true));
        put('+', new KeyStroke(KeyEvent.VK_EQUALS, true));
        put(',', new KeyStroke(KeyEvent.VK_COMMA, false));
        put('-', new KeyStroke(KeyEvent.VK_MINUS, false));
        put('.', new KeyStroke(KeyEvent.VK_PERIOD, false));
        put('/', new KeyStroke(KeyEvent.VK_SLASH, false));
        
        for (int i = (int) '0'; i <= (int) '9'; i++) {
            put((char) i, new KeyStroke(i, false));
        }
        
        put(':', new KeyStroke(KeyEvent.VK_SEMICOLON, true));
        put(';', new KeyStroke(KeyEvent.VK_SEMICOLON, false));
        put('<', new KeyStroke(KeyEvent.VK_COMMA, true));
        put('=', new KeyStroke(KeyEvent.VK_EQUALS, false));
        put('>', new KeyStroke(KeyEvent.VK_PERIOD, true));
        put('?', new KeyStroke(KeyEvent.VK_SLASH, true));
        put('@', new KeyStroke(KeyEvent.VK_2, true));
        
        for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
            put((char) i, new KeyStroke(i, true));
        }
        
        put('[', new KeyStroke(KeyEvent.VK_OPEN_BRACKET, false));
        put('\\', new KeyStroke(KeyEvent.VK_BACK_SLASH, false));
        put(']', new KeyStroke(KeyEvent.VK_CLOSE_BRACKET, false));
        put('^', new KeyStroke(KeyEvent.VK_6, true));
        put('_', new KeyStroke(KeyEvent.VK_MINUS, true));
        put('`', new KeyStroke(KeyEvent.VK_BACK_QUOTE, false));
        
        for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
            put((char) (i + ((int) 'a' - (int) 'A')), new KeyStroke(i, false));
        }
        
        put('{', new KeyStroke(KeyEvent.VK_OPEN_BRACKET, true));
        put('|', new KeyStroke(KeyEvent.VK_BACK_SLASH, true));
        put('}', new KeyStroke(KeyEvent.VK_CLOSE_BRACKET, true));
        put('~', new KeyStroke(KeyEvent.VK_BACK_QUOTE, true));
    }
}
