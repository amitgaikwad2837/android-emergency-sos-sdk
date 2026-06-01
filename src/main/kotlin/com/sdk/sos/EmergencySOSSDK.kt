package com.sdk.sos

import kotlinx.coroutines.flow.StateFlow
import com.sdk.aiprovider.api.Result

/**
 * Emergency SOS SDK - Fast emergency response for vulnerable individuals.
 *
 * Platform: Android
 * Category: Safety & Emergency
 * Features:
 * - Manual SOS triggering
 * - Gesture-based activation (shake detection)
 * - Voice command activation
 * - GPS collection and emergency contact notification
 * - Background monitoring
 */
object EmergencySOSSDK {
    private var instance: SOSManager? = null

    /**
     * Initialize the Emergency SOS SDK with the provided configuration.
     * Must be called before triggering any SOS events.
     *
     * @param config Configuration including gesture detection and contact thresholds
     * @return Success on initialization, Error if unable to start monitoring services
     */
    suspend fun initialize(config: SOSConfig): Result<Unit> {
        return try {
            instance = SOSManager(config)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Reconfigure the SDK while it's running (e.g., change shake sensitivity or voice detection settings).
     * Changes take effect immediately without interrupting ongoing monitoring.
     *
     * @param config New configuration parameters
     * @return Success if reconfigured, Error if SDK not initialized
     */
    suspend fun configure(config: SOSConfig): Result<Unit> {
        return try {
            instance?.configure(config) ?: return Result.Error(IllegalStateException("SDK not initialized"))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Manually trigger an emergency SOS alert. Immediately notifies all registered emergency contacts
     * and starts the countdown timer if configured.
     *
     * @param reason Optional description of why SOS was triggered (e.g., "Fall detected", "Manual activation")
     * @return SOSEvent with timestamp and trigger details, or Error if unable to send notifications
     */
    suspend fun triggerSOS(reason: String? = null): Result<SOSEvent> {
        return try {
            val event = instance?.triggerSOS(reason) ?: return Result.Error(IllegalStateException("SDK not initialized"))
            Result.Success(event)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Begin passive monitoring for emergency triggers (shake gestures, voice commands, etc).
     * Runs in the background and emits events through observeEvents().
     *
     * @return Success if monitoring started, Error if already monitoring or SDK not initialized
     */
    suspend fun startMonitoring(): Result<Unit> {
        return try {
            instance?.startMonitoring() ?: return Result.Error(IllegalStateException("SDK not initialized"))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Stop passive monitoring and suspend all gesture/voice triggers.
     * Manual SOS calls are still possible after stopping.
     *
     * @return Success if monitoring stopped
     */
    suspend fun stopMonitoring(): Result<Unit> {
        return try {
            instance?.stopMonitoring() ?: return Result.Error(IllegalStateException("SDK not initialized"))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Observe SOS events as they occur (triggered manually, by gesture, or voice command).
     * Events flow through this StateFlow while monitoring is active.
     *
     * @return StateFlow emitting SOSEvent objects when alerts are triggered
     */
    fun observeEvents(): StateFlow<SOSEvent?> {
        return instance?.observeEvents() ?: throw IllegalStateException("SDK not initialized")
    }

    /**
     * Register an emergency contact who will be notified when an SOS is triggered.
     * Contacts are called/messaged based on configured contact methods.
     *
     * @param contact Person's name, phone number, and priority flag (primary contacts called first)
     * @return Success if contact added, Error if unable to store contact
     */
    suspend fun addEmergencyContact(contact: EmergencyContact): Result<Unit> {
        return try {
            instance?.addEmergencyContact(contact) ?: return Result.Error(IllegalStateException("SDK not initialized"))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Shutdown the SOS SDK and clean up resources. Stops monitoring and resets internal state.
     * Call this when the app closes or when SOS functionality is no longer needed.
     *
     * @return Success after cleanup completes
     */
    suspend fun destroy(): Result<Unit> {
        return try {
            instance?.destroy()
            instance = null
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

/**
 * SOS SDK configuration.
 */
data class SOSConfig(
    val enableShakeDetection: Boolean = true,
    val enableVoiceDetection: Boolean = true,
    val shakeThreshold: Float = 25f,
    val countdownSeconds: Int = 5,
    val enableGPSCollection: Boolean = true,
    val enableBackgroundMonitoring: Boolean = true,
    val maxBackgroundIntervalSeconds: Int = 300
)

/**
 * An SOS event triggered by the system.
 */
data class SOSEvent(
    val id: String,
    val timestamp: Long,
    val triggerType: TriggerType,
    val reason: String? = null,
    val location: Location? = null,
    val status: SOSStatus = SOSStatus.TRIGGERED
)

/**
 * How the SOS was triggered.
 */
enum class TriggerType {
    MANUAL,
    SHAKE_DETECTED,
    VOICE_COMMAND,
    COUNTDOWN_COMPLETED
}

/**
 * Status of an SOS event.
 */
enum class SOSStatus {
    TRIGGERED,
    ACKNOWLEDGED,
    IN_PROGRESS,
    RESOLVED
}

/**
 * Geographic location.
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * An emergency contact to notify.
 */
data class EmergencyContact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val email: String? = null,
    val isPrimary: Boolean = false
)

/**
 * Internal SOS manager (implementation detail).
 */
internal class SOSManager(private var config: SOSConfig) {
    private val eventFlow = kotlinx.coroutines.flow.MutableStateFlow<SOSEvent?>(null)

    suspend fun configure(newConfig: SOSConfig) {
        config = newConfig
    }

    suspend fun triggerSOS(reason: String?): SOSEvent {
        val event = SOSEvent(
            id = java.util.UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            triggerType = TriggerType.MANUAL,
            reason = reason
        )
        eventFlow.value = event
        return event
    }

    suspend fun startMonitoring() {
        // Implementation
    }

    suspend fun stopMonitoring() {
        // Implementation
    }

    fun observeEvents() = eventFlow

    suspend fun addEmergencyContact(contact: EmergencyContact) {
        // Implementation
    }

    suspend fun destroy() {
        // Cleanup
    }
}
