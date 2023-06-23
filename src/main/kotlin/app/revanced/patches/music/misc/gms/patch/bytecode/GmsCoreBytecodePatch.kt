package app.revanced.patches.music.misc.gms.patch.bytecode

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.music.annotations.MusicCompatibility
import app.revanced.patches.music.misc.gms.fingerprints.*
import app.revanced.patches.music.misc.gms.patch.resource.GmsCoreResourcePatch
import app.revanced.patches.music.misc.gms.shared.Constants.MUSIC_PACKAGE_NAME
import app.revanced.patches.music.misc.gms.shared.Constants.REVANCED_MUSIC_PACKAGE_NAME
import app.revanced.patches.youtube.misc.gms.shared.Constants
import app.revanced.util.gms.GmsCoreBytecodeHelper

@Patch
@DependsOn([GmsCoreResourcePatch::class])
@Name("GmsCore support")
@Description("Allows YouTube to run without root and under a different package name.")
@MusicCompatibility
class GmsCoreBytecodePatch : BytecodePatch(
    listOf(
        ServiceCheckFingerprint,
        GooglePlayUtilityFingerprint,
        CastDynamiteModuleFingerprint,
        CastDynamiteModuleV2Fingerprint,
        CastContextFetchFingerprint,
        PrimeFingerprint,
    )
) {
    // NOTE: the previous patch also replaced the following strings, but it seems like they are not needed:
    // - "com.google.android.gms.chimera.GmsIntentOperationService",
    // - "com.google.android.gms.phenotype.internal.IPhenotypeCallbacks",
    // - "com.google.android.gms.phenotype.internal.IPhenotypeService",
    // - "com.google.android.gms.phenotype.PACKAGE_NAME",
    // - "com.google.android.gms.phenotype.UPDATE",
    // - "com.google.android.gms.phenotype",
    override fun execute(context: BytecodeContext) = GmsCoreBytecodeHelper.patchBytecode(
        context,
        arrayOf(
            GmsCoreBytecodeHelper.packageNameTransform(
                Constants.PACKAGE_NAME,
                Constants.REVANCED_PACKAGE_NAME
            )
        ),
        GmsCoreBytecodeHelper.PrimeMethodTransformationData(
            PrimeFingerprint,
            MUSIC_PACKAGE_NAME,
            REVANCED_MUSIC_PACKAGE_NAME
        ),
        listOf(
            ServiceCheckFingerprint,
            GooglePlayUtilityFingerprint,
            CastDynamiteModuleFingerprint,
            CastDynamiteModuleV2Fingerprint,
            CastContextFetchFingerprint
        )
    )
}
