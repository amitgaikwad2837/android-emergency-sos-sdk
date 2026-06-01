package com.sdk.sos

import org.junit.Test
import kotlin.test.assertEquals

class EmergencySOSSDKTest {
    @Test
    fun testSOSConfigCreation() {
        // Verify SDK configuration can be created with custom settings (gesture and voice detection toggles)
        val config = SOSConfig(
            enableShakeDetection = true,
            enableVoiceDetection = false,
            countdownSeconds = 3
        )
        
        assertEquals(true, config.enableShakeDetection)
        assertEquals(false, config.enableVoiceDetection)
        assertEquals(3, config.countdownSeconds)
    }

    @Test
    fun testSOSEventCreation() {
        // Verify SOS event object properly stores trigger information (manual vs gesture/voice triggered)
        val event = SOSEvent(
            id = "test-sos",
            timestamp = System.currentTimeMillis(),
            triggerType = TriggerType.MANUAL,
            reason = "Test trigger"
        )
        
        assertEquals("test-sos", event.id)
        assertEquals(TriggerType.MANUAL, event.triggerType)
        assertEquals("Test trigger", event.reason)
    }

    @Test
    fun testEmergencyContactCreation() {
        // Verify emergency contact object stores name, phone, and priority flag correctly
        val contact = EmergencyContact(
            id = "contact-1",
            name = "John Doe",
            phoneNumber = "+1234567890",
            isPrimary = true
        )
        
        assertEquals("John Doe", contact.name)
        assertEquals("+1234567890", contact.phoneNumber)
        assertEquals(true, contact.isPrimary)
    }

    @Test
    fun testSOSConfigDefaults() {
        // Verify default configuration enables both shake and voice detection with 5-second countdown
        val config = SOSConfig()
        assertEquals(true, config.enableShakeDetection)
        assertEquals(true, config.enableVoiceDetection)
        assertEquals(5, config.countdownSeconds)
    }

    @Test
    fun testTriggerTypeValues() {
        // Verify all SOS trigger types are properly defined (manual, shake, voice, countdown)
        assertEquals(4, TriggerType.values().size)
        assertTrue(TriggerType.values().contains(TriggerType.MANUAL))
        assertTrue(TriggerType.values().contains(TriggerType.SHAKE_DETECTED))
        assertTrue(TriggerType.values().contains(TriggerType.VOICE_COMMAND))
    }

    @Test
    fun testSOSEventWithLocation() {
        val location = Location(
            latitude = 37.7749,
            longitude = -122.4194,
            accuracy = 10f
        )
        
        val event = SOSEvent(
            id = "sos-with-location",
            timestamp = System.currentTimeMillis(),
            triggerType = TriggerType.MANUAL,
            location = location
        )
        
        assertEquals(37.7749, event.location?.latitude)
        assertEquals(-122.4194, event.location?.longitude)
    }

    @Test
    fun testSOSStatusValues() {
        assertEquals(4, SOSStatus.values().size)
        assertTrue(SOSStatus.values().contains(SOSStatus.TRIGGERED))
        assertTrue(SOSStatus.values().contains(SOSStatus.RESOLVED))
    }

    @Test
    fun testMultipleEmergencyContacts() {
        val contacts = listOf(
            EmergencyContact(id = "1", name = "Primary", phoneNumber = "111", isPrimary = true),
            EmergencyContact(id = "2", name = "Secondary", phoneNumber = "222", isPrimary = false),
            EmergencyContact(id = "3", name = "Tertiary", phoneNumber = "333", isPrimary = false)
        )
        
        assertEquals(3, contacts.size)
        assertEquals(1, contacts.filter { it.isPrimary }.size)
    }
}
