/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keystroke;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;
import javax.swing.KeyStroke;

/**
 *
 * @author ed
 */
public class KeyStrokeGenerator {

    private int strokePause = 200;
    private final Robot robot;

    KeyStrokeGenerator(String script) throws InterruptedException, AWTException {
        this.robot = new Robot();

        String[] split = script.split("\n");
        for (String part : split) {
            this.process(part);
        }
    }

    private void process(String part) throws InterruptedException {
        int index = part.indexOf(":");
        String command = part.substring(0, index);
        String value = part.substring(index + 1);
        System.out.println("[" + command + "], [" + value + "]");

        switch (command) {
            case "pause":
                int milli = Integer.parseInt(value.trim());
                Thread.sleep(milli);
                break;
            case "text":
                value = value.trim();
                for (char c : value.toCharArray()) {
                    robot.keyPress(Character.toUpperCase(c));
                    robot.keyRelease(Character.toUpperCase(c));
                    robot.delay(this.strokePause);
                }
                robot.keyPress(KeyEvent.VK_ENTER);
        }
    }
}
