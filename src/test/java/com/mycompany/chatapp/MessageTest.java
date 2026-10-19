
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class MessageTest {

    @Test
    public void testMessageIDIsTenDigits() {
        Message message = new Message(
                "0012345678", 0, "+27718693002", "Hi Mike, thanks.");

        assertTrue(message.checkMessageID());
        assertEquals("0012345678", message.getMessageID());
    }

    @Test
    public void testMessageIDInvalid() {
        Message message = new Message(
                "12345", 0, "+27718693002", "Hi Mike, thanks.");

        assertFalse(message.checkMessageID());
    }

    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        Message message = new Message(
                "0012345678", 0, "+27718693002", "Hi Mike, thanks.");

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        Message message = new Message(
                "0012345678", 0, "08575975889", "Hi Keegan, thanks.");

        assertTrue(message.checkRecipientCell().contains(
                "incorrectly formatted"));
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "0012345678", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        assertEquals("00:0:HITONIGHT", message.getMessageHash());
    }

    @Test
    public void testMessageUnder250Characters() {
        Message message = new Message(
                "0012345678", 0, "+27718693002", "Hi Mike, thanks.");

        assertTrue(message.checkMessageLength());
        assertEquals("Message ready to send.",
                message.messageLengthStatus());
    }

    @Test
    public void testMessageOver250Characters() {
        String longText = "A".repeat(251);

        Message message = new Message(
                "0012345678", 0, "+27718693002", longText);

        assertFalse(message.checkMessageLength());
        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.messageLengthStatus());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                "0012345678", 0, "+27718693002", "Hi Mike, thanks.");

        assertEquals("Message successfully sent.",
                message.SentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                "0012345679", 1, "+27718693002", "Hi Mike, thanks.");

        assertEquals("Press 0 to delete the message.",
                message.SentMessage(2));
    }

    @Test
    public void testStoreMessage() throws Exception {
        Path file = Path.of("messages.json");
        Files.deleteIfExists(file);

        Message message = new Message(
                "0012345680", 2, "+27718693002", "Hi Mike, thanks.");

        assertEquals("Message successfully stored.",
                message.SentMessage(3));
        assertTrue(Files.exists(file));
        assertTrue(Files.readString(file).contains("0012345680"));

        Files.deleteIfExists(file);
    }
}

