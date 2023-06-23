package app.revanced.patches.youtube.misc.gms.patch.bytecode

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.shared.fingerprints.WatchWhileActivityFingerprint
import app.revanced.patches.youtube.layout.buttons.cast.patch.HideCastButtonPatch
import app.revanced.patches.youtube.misc.fix.playback.patch.ClientSpoofPatch
import app.revanced.patches.youtube.misc.gms.annotations.GmsCoreCompatibility
import app.revanced.patches.youtube.misc.gms.fingerprints.*
import app.revanced.patches.youtube.misc.gms.patch.resource.GmsCoreResourcePatch
import app.revanced.patches.youtube.misc.gms.shared.Constants.PACKAGE_NAME
import app.revanced.patches.youtube.misc.gms.shared.Constants.REVANCED_PACKAGE_NAME
import app.revanced.util.gms.GmsCoreBytecodeHelper

@Patch
@DependsOn(
    [
        GmsCoreResourcePatch::class,
        HideCastButtonPatch::class,
        ClientSpoofPatch::class
    ]
)
@Name("GmsCore support")
@Description("Allows YouTube to run without root and under a different package name.")
@GmsCoreCompatibility
class GmsCoreBytecodePatch : BytecodePatch(
    listOf(
        ServiceCheckFingerprint,
        GooglePlayUtilityFingerprint,
        CastDynamiteModuleFingerprint,
        CastDynamiteModuleV2Fingerprint,
        CastContextFetchFingerprint,
        PrimeFingerprint,
        WatchWhileActivityFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {
        // apply common GmsCore patch
        GmsCoreBytecodeHelper.patchBytecode(
            context, arrayOf(
                GmsCoreBytecodeHelper.packageNameTransform(
                    PACKAGE_NAME,
                    REVANCED_PACKAGE_NAME
                )
            ),
            GmsCoreBytecodeHelper.PrimeMethodTransformationData(
                PrimeFingerprint,
                PACKAGE_NAME,
                REVANCED_PACKAGE_NAME
            ),
            listOf(
                ServiceCheckFingerprint,
                GooglePlayUtilityFingerprint,
                CastDynamiteModuleFingerprint,
                CastDynamiteModuleV2Fingerprint,
                CastContextFetchFingerprint
            )
        )

        // inject the notice for GmsCore
        GmsCoreBytecodeHelper.injectNotice(WatchWhileActivityFingerprint)
    }
}
