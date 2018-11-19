package com.jcrew.helper;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ShellExecutor {
    public static void executeShell(String command) {
        String host = PropertyLoader.getPuttyHostName();
        String username = PropertyLoader.getPuttyUsername();
        String password = PropertyLoader.getPuttyPassword();
        executeShell(host, username, password, command);
    }

    public static void executeShell(String host, String username, String password, String command) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, 22);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream input = channel.getInputStream();
            channel.connect();

            System.out.println("Channel Connected to machine " + host + " server with command: " + command);

            try {
                InputStreamReader inputReader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(inputReader);
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                bufferedReader.close();
                inputReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
