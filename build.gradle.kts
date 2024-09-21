plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.vk.id.sdk) apply true
    alias(libs.plugins.ksp) apply false
}

vkidManifestPlaceholders {
    init(
        clientId = "52094217",
        clientSecret = "IznNur14CZR0dHcBkTrz",
    )
}
