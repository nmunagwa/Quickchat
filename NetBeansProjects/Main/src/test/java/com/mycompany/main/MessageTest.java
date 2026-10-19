package com.mycompany.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTest {

    static final String MSG1 = "Hi Mike, can you join us for dinner tonight?";
    static final String MSG2 = "Hi Keegan, did you receive the payment?";

    @BeforeEach
    void setUp() {
        Message.resetAll();
    }

    @Test
    void messageLengthSuccess() {
        assertEquals("Message ready to send.", Message.checkMessageLength(MSG1));
    }

    @Test
    void messageLengthFailure() {
        String longText = "a".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.checkMessageLength(longText));
    }

    @Test
    void recipientCorrectlyFormatted() {
        Message m = new Message("+27718693002", MSG1);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    void recipientIncorrectlyFormatted() {
        Message m = new Message("08575975889", MSG2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.",
                m.checkRecipientCell());
    }

    @Test
    void messageHashIsCorrect() {
        Message m = new Message("0012345678", "+27718693002", MSG1);
        assertEquals("00:0:HITONIGHT", m.getMessageHash());
    }

    @Test
    void remainingHashesInLoop() {
        String[] ids = {"0012345678", "0812345678"};
        String[] texts = {MSG1, MSG2};
        String[] expected = {"00:0:HITONIGHT", "08:1:HIPAYMENT"};
        for (int i = 0; i < texts.length; i++) {
            Message m = new Message(ids[i], "+27718693002", texts[i]);
            assertEquals(expected[i], m.getMessageHash());
        }
    }

    @Test
    void messageIdIsCreated() {
        Message m = new Message("+27718693002", MSG1);
        assertEquals(10, m.getMessageID().length());
        assertTrue(m.checkMessageID());
    }

    @Test
    void messageIdTooLongFails() {
        Message m = new Message("12345678901", "+27718693002", MSG1);
        assertFalse(m.checkMessageID());
    }

    @Test
    void messageSentOptions() {
        Message m1 = new Message("+27718693002", MSG1);
        Message m2 = new Message("08575975889", MSG2);
        Message m3 = new Message("+27718693002", MSG1);
        assertEquals("Message successfully sent.", m1.sentMessage(1));
        assertEquals("Press 0 to delete the message.", m2.sentMessage(2));
        assertEquals("Message successfully stored.", m3.sentMessage(3));
    }

    @Test
    void totalMessagesSent() {
        new Message("+27718693002", MSG1).sentMessage(1);   // sent
        new Message("08575975889", MSG2).sentMessage(2);    // discarded
        assertEquals(1, Message.returnTotalMessages());
        assertTrue(Message.printMessages().contains(MSG1));
        assertFalse(Message.printMessages().contains(MSG2));
    }
}