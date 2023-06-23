package app.revanced.patches.music.misc.gms.patch.resource

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patches.music.misc.gms.shared.Constants.MUSIC_PACKAGE_NAME
import app.revanced.patches.music.misc.gms.shared.Constants.REVANCED_MUSIC_APP_NAME
import app.revanced.patches.music.misc.gms.shared.Constants.REVANCED_MUSIC_PACKAGE_NAME
import app.revanced.patches.music.misc.gms.shared.Constants.SPOOFED_PACKAGE_NAME
import app.revanced.patches.music.misc.gms.shared.Constants.SPOOFED_PACKAGE_SIGNATURE
import app.revanced.util.gms.GmsCoreManifestHelper
import app.revanced.util.gms.GmsCoreResourceHelper

class GmsCoreResourcePatch : ResourcePatch {
    override fun execute(context: ResourceContext) {
        // update manifest
        GmsCoreResourceHelper.patchManifest(
            context,
            MUSIC_PACKAGE_NAME,
            REVANCED_MUSIC_PACKAGE_NAME,
            REVANCED_MUSIC_APP_NAME
        )

        // add metadata to the manifest
        GmsCoreManifestHelper.addSpoofingMetadata(
            context,
            SPOOFED_PACKAGE_NAME,
            SPOOFED_PACKAGE_SIGNATURE
        )
    }
}