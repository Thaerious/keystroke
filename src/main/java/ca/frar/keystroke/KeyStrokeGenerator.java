/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.frar.keystroke;

import ca.frar.query.Query;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.xml.sax.SAXException;

/**
 *
 * @author ed
 */
public class KeyStrokeGenerator {

    private int defaultDelay = 150;

    private final Robot robot;
    private final Query script;
    private final KeyMap keyMap = new KeyMap();
    private String awaiting = "";
    GlobalKeyListener keyListener;

    List<Query> currentChildren = null;
    int currentIndex = 0;

    KeyStrokeGenerator(String script) throws InterruptedException, AWTException, SAXException, NativeHookException {
        this.robot = new Robot();
        this.script = new Query(script);

        keyListener = new GlobalKeyListener() {
            @Override
            public void onKey(String keyText) {
                System.out.println(keyText);
                if (keyText.equals(awaiting) || awaiting.equals("any")) {
                    awaiting = "";
                    continueProcess();
                }
            }
        };

        // Get the logger for "org.jnativehook" and set the level to warning.
        java.util.logging.Logger log = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        log.setLevel(java.util.logging.Level.OFF);

        // Don't forget to disable the parent handlers.
        log.setUseParentHandlers(false);
    }

    public void start() throws InterruptedException, NativeHookException {
        this.keyListener.start();
        currentChildren = this.script.children().split();
        currentIndex = 0;
        this.continueProcess();
    }

    private void continueProcess() {
        while (awaiting.isEmpty() && currentIndex < currentChildren.size()) {
            this.process(currentChildren.get(currentIndex));
            currentIndex++;
        }

        if (currentIndex >= currentChildren.size()) {
            try {
                this.keyListener.stop();
            } catch (NativeHookException ex) {
//                log.getLogger(KeyStrokeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void process(Query child) {
        System.out.println(child);

        switch (child.tagName()) {
            case "pause":
                processPause(child);
                break;
            case "type":
                processType(child);
                break;
            case "stroke":
                processStroke(child);
                break;
            case "await":
                processAwait(child);
                break;
            case "set":
                processSet(child);
                break;
        }
    }

    private void processSet(Query child) {
        String name = child.select("name").text();
        String value = child.select("value").text();

        switch (name) {
            case "delay":
                this.defaultDelay = Integer.parseInt(value);
                break;
        }
    }

    private void processType(Query child) {
        int delay = this.defaultDelay;
        if (child.hasAttribute("delay")) {
            delay = Integer.parseInt(child.attribute("delay"));
        }

        String[] split = child.text().split("\n");
        for (String string : split) {
            String value = string.trim();
            for (char c : value.toCharArray()) {
                KeyStroke keyStroke = keyMap.get(c);
                keyStroke.stroke(robot);
                robot.delay(delay);
            }
        }
        
        System.out.println(child.attribute("crlf"));
        
        if (!child.attribute("crlf").equals("false")) {
            robot.keyPress(KeyEvent.VK_ENTER);
        }
    }

    private void processPause(Query child) {
        int duration = Integer.parseInt(child.text());
        robot.delay(duration);
    }

    private void processAwait(Query child) {
        String key = child.attribute("key");
        this.awaiting = key;
    }

    private void processStroke(Query child) {
        int delay = this.defaultDelay;
        if (child.hasAttribute("delay")) {
            delay = Integer.parseInt(child.attribute("delay"));
        }

        String key = child.attribute("key");
        KeyStroke keyStroke = null;

        if (key.length() == 1) {
            keyStroke = keyMap.get(key.charAt(0));
        } else {
            switch (key) {
                case "esc":
                    keyStroke = new KeyStroke(KeyEvent.VK_ESCAPE, false);
                    break;
            }
        }

        if (keyStroke != null) {
            keyStroke.stroke(robot);
            robot.delay(delay);
        }
    }
}
