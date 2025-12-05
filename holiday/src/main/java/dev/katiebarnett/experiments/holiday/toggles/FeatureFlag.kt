package dev.katiebarnett.experiments.holiday.toggles

import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlag(val name: String, val enabled: Boolean, val id: String)
