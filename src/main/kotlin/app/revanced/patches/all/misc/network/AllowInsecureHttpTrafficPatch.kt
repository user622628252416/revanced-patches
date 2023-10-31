package app.revanced.patches.all.misc.network

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.Patch

@Patch(
    name = "Allow Insecure HTTP Traffic",
    description = "Allows app to connect to the internet without using encrypted HTTPS. Make sure you are aware of security implications.",
    use = false
)
@Suppress("unused")
object AllowInsecureHttpTrafficPatch : ResourcePatch() {
    private const val USES_CLEARTEXT_TRAFFIC_FLAG = "android:usesCleartextTraffic "
    override fun execute(context: ResourceContext) {
        context.xmlEditor["AndroidManifest.xml"].use { editor ->
            val document = editor.file
            val applicationTag = document.getElementsByTagName("application").item(0)

            val usesCleartextTrafficAttribute = applicationTag.attributes.getNamedItem(USES_CLEARTEXT_TRAFFIC_FLAG)

            if (usesCleartextTrafficAttribute != null) {
                if (usesCleartextTrafficAttribute.nodeValue != "true") {
                    usesCleartextTrafficAttribute.nodeValue = "true"
                }
            }
            // Copied from [ExportAllActivitiesPatch]:
            // Reason why the attribute is added in the case it does not exist:
            // https://github.com/revanced/revanced-patches/pull/1751/files#r1141481604
            else document.createAttribute(USES_CLEARTEXT_TRAFFIC_FLAG)
                .apply { value = "true" }
                .let(applicationTag.attributes::setNamedItem)

            return@use
        }
    }
}