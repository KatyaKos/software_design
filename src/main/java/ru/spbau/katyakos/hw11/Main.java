package ru.spbau.katyakos.hw11;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Messenger messenger;
        try{
            String author = args[0];
            int port = Integer.parseInt(args[1]);
            String peerAddress = args[2];
            int peerPort = Integer.parseInt(args[3]);
            messenger = new Messenger(author, port, peerAddress, peerPort, System.out);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.err.println("Please, provide four argument! \'author port connection_address connection_port\'");
            return;
        } catch (IOException exception) {
            System.err.println("Failed to bind.");
            return;
        }
        String input;
        LineNumberReader lnr = new LineNumberReader(new InputStreamReader(System.in));
        while (!(input = lnr.readLine()).equals(":quit")) {
            if (!messenger.sendMessage(input)) {
                System.err.println("\nCouldn't deliver your message. Please, try again later.\n");
            }
        }
    }
}