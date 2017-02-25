package com.sid.demogdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.sid.demogdx.DemoGdx;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Created by Okis on 2017.02.25.
 */

public class JFrameLauncher extends JFrame {

    public JFrameLauncher() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Container container = getContentPane();
        container.setLayout(new BorderLayout());

        final LwjglAWTCanvas canvas = new LwjglAWTCanvas(new DemoGdx());
        container.add(canvas.getCanvas(), BorderLayout.CENTER);

        pack();
        setVisible(true);
        setSize(800, 600);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFrameLauncher();
            }
        });
    }
}
