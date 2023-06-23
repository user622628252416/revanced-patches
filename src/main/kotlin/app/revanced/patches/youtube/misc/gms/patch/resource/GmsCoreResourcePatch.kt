package app.revanced.patches.youtube.misc.gms.patch.resource

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patches.shared.settings.preference.impl.Preference
import app.revanced.patches.shared.settings.preference.impl.StringResource
import app.revanced.patches.youtube.misc.gms.shared.Constants.PACKAGE_NAME
import app.revanced.patches.youtube.misc.gms.shared.Constants.REVANCED_APP_NAME
import app.revanced.patches.youtube.misc.gms.shared.Constants.REVANCED_PACKAGE_NAME
import app.revanced.patches.youtube.misc.gms.shared.Constants.SPOOFED_PACKAGE_NAME
import app.revanced.patches.youtube.misc.gms.shared.Constants.SPOOFED_PACKAGE_SIGNATURE
import app.revanced.patches.youtube.misc.settings.bytecode.patch.SettingsPatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsResourcePatch
import app.revanced.util.gms.Constants.GMS_VENDOR
import app.revanced.util.gms.GmsCoreManifestHelper
import app.revanced.util.gms.GmsCoreResourceHelper

@DependsOn([SettingsResourcePatch::class])
class GmsCoreResourcePatch : ResourcePatch {
    override fun execute(context: ResourceContext) {
        SettingsPatch.addPreference(
            Preference(
                StringResource("gms_core_settings", "GmsCore Settings"),
                StringResource("gms_core_settings_summary", "Settings for GmsCore"),
                Preference.Intent("$GMS_VENDOR.android.gms", "", "org.microg.gms.ui.SettingsActivity")
            )
        )
        SettingsPatch.renameIntentsTargetPackage(REVANCED_PACKAGE_NAME)

        // update manifest
        GmsCoreResourceHelper.patchManifest(
            context,
            PACKAGE_NAME,
            REVANCED_PACKAGE_NAME,
            REVANCED_APP_NAME
        )

        // add metadata to manifest
        GmsCoreManifestHelper.addSpoofingMetadata(
            context,
            SPOOFED_PACKAGE_NAME,
            SPOOFED_PACKAGE_SIGNATURE
        )

        // add strings
        GmsCoreResourceHelper.addStrings(context)
    }
}