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
import java.util.function.Consumer;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.xml.sax.SAXException;

/**
 *
 * @author ed
 */
public class KeyStrokeGenerator implements Runnable {
    private int defaultDelay = 100;
    private int crlfDelay = 500;

    private final Robot robot;
    private final Query script;
    private final KeyMap keyMap = new KeyMap();
    private String awaiting = "";
    GlobalKeyListener globalKeyListener;

    List<Query> currentChildren = null;
    int currentIndex = 0;
    boolean running = false;

    Consumer<String> nextCommand = null;
    private final Consumer<String> onKey;
    
    KeyStrokeGenerator(String script) throws InterruptedException, AWTException, SAXException, NativeHookException {
        this.robot = new Robot();
        this.script = new Query(script);

        robot.setAutoWaitForIdle(false);

        onKey = new Consumer<>() {
            @Override
            public void accept(String keyText) {
                System.out.println(keyText + ", " + awaiting);
                if (keyText.equals(awaiting) || awaiting.equals("any")) {
                    awaiting = "";
                    continueProcess();
                }
            }
        };

        globalKeyListener = new GlobalKeyListener(onKey);

        // Get the logger for "org.jnativehook" and set the level to warning.
        java.util.logging.Logger log = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        log.setLevel(java.util.logging.Level.OFF);

        // Don't forget to disable the parent handlers.
        log.setUseParentHandlers(false);
    }

    @Override
    public void run() {
        if (nextCommand != null) nextCommand.accept("");
        globalKeyListener.run();
        this.running = true;
        currentChildren = this.script.children().split();
        currentIndex = 0;        
        this.continueProcess();
    }

    public void stop() {
        this.running = false;
        if (!awaiting.isEmpty()) this.onKey.accept(awaiting);
    }

    private void continueProcess() {
        while (running && awaiting.isEmpty() && currentIndex < currentChildren.size()) {            
            this.process(currentChildren.get(currentIndex));
            currentIndex++;
        }

        if (running == false || currentIndex >= currentChildren.size()) {
            System.out.println("stopping");
            this.globalKeyListener.stop();
            if (nextCommand != null) nextCommand.accept("");
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
            case "crlf":
                processCRLF(child);
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

    private void processCRLF(Query child) {
        robot.keyPress(KeyEvent.VK_ENTER);
    }
    
    private void processType(Query child) {
        if (nextCommand != null) nextCommand.accept("");
        int delay = this.defaultDelay;
        if (child.hasAttribute("delay")) {
            delay = Integer.parseInt(child.attribute("delay"));
        }

        String[] split = child.text().split("\n");
        for (String value : split) {
            for (char c : value.toCharArray()) {
                KeyStroke keyStroke = keyMap.get(c);
                keyStroke.stroke(robot);
                robot.delay(delay);
            }
        }

        String crlf = child.attribute("crlf");

        if (crlf.equals("await")) {
            if (nextCommand != null) nextCommand.accept("awaiting <Enter>");
            this.awaiting = "Enter";
        } else if (!child.attribute("crlf").equals("false")) {
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
        if (nextCommand != null) nextCommand.accept("awaiting <" + key + ">");
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
