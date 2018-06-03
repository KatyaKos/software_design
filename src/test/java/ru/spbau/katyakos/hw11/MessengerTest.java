package ru.spbau.katyakos.hw11;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MessengerTest {

    private String user1 = "Peter Pan";
    private String user2 = "Captain Hook";
    private int port1 = 6660;
    private int port2 = 6661;
    private ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
    private ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
    private String message = "Hey, Hook, I have your treasure chest!";

    @Test
    public void sendMessageTest() throws IOException {
        Messenger messenger1 = new Messenger(user1, port1, "localhost", port2, new PrintStream(stream1));
        Messenger messenger2 = new Messenger(user2, port2, "localhost", port1, new PrintStream(stream2));
        messenger1.sendMessage(message);
        String[] received = stream2.toString().split("\n");
        assertTrue(2 == received.length);
        assertEquals(message, received[1].trim());
        String[] header = received[0].split("\t");
        assertTrue(2 == header.length);
        assertEquals("Author: " + user1, header[1]);
        messenger1.shutDown();
        messenger2.shutDown();
    }

    @Test(expected = IOException.class)
    public void noBindTest() throws IOException {
        Messenger messenger1 = new Messenger(user1, port1, "localhost", port2, new PrintStream(stream1));
        Messenger messenger2 = new Messenger(user2, port1, "localhost", port2, new PrintStream(stream2));
        messenger1.shutDown();
        messenger2.shutDown();
    }

    @Test
    public void noReceiverTest() throws IOException {
        Messenger messenger1 = new Messenger(user1, port1, "localhost", port2, new PrintStream(stream1));
        assertFalse(messenger1.sendMessage(message));
        messenger1.shutDown();
    }
}