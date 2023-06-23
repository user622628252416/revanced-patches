package app.revanced.util.gms

import app.revanced.patcher.data.ResourceContext
import app.revanced.util.resources.ResourceUtils.mergeStrings

/**
 * Helper class for applying resource patches needed for the gms-core-support patches.
 */
internal object GmsCoreResourceHelper {

    /**
     * Add necessary strings to the strings.xml file.
     *
     * @param context The resource context.
     * @param stringsHost The file which hosts the strings.
     */
    fun addStrings(context: ResourceContext, stringsHost: String = "gmscore/host/values/strings.xml") =
        context.mergeStrings(stringsHost)

    /**
     * Patch the manifest to work with GmsCore.
     *
     * @param context The resource context.
     * @param fromPackageName The original package name.
     * @param toPackageName The package name to accept.
     * @param toName The new name of the app.
     */
    fun patchManifest(
        context: ResourceContext,
        fromPackageName: String,
        toPackageName: String,
        toName: String
    ) {
        val manifest = context["AndroidManifest.xml"].readText()
        context["AndroidManifest.xml"].writeText(
            manifest.replace(
                "package=\"$fromPackageName",
                "package=\"$toPackageName"
            ).replace(
                "android:label=\"@string/app_name",
                "android:label=\"$toName"
            ).replace(
                "android:label=\"@string/app_launcher_name",
                "android:label=\"$toName"
            ).replace(
                "android:authorities=\"$fromPackageName",
                "android:authorities=\"$toPackageName"
            ).replace(
                "$fromPackageName.permission.C2D_MESSAGE",
                "$toPackageName.permission.C2D_MESSAGE"
            ).replace(
                "$fromPackageName.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION",
                "$toPackageName.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION"
            ).replace(
                "com.google.android.c2dm",
                "${Constants.GMS_VENDOR}.android.c2dm"
            ).replace(
                "</queries>",
                "<package android:name=\"${Constants.GMS_VENDOR}.android.gms\"/></queries>"
            )
        )
    }
}