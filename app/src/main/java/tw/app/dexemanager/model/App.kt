package tw.app.dexemanager.model

import com.google.firebase.firestore.Exclude
import tw.app.dexemanager.database.AppType

data class App(
    var name: String = "",
    var type: AppType,
    var description: String = "",
    var apkFile: String = "",
    var apkIcon: String = "",
    var version: String = "",

    @get:Exclude
    @set:Exclude
    var isInstalled: Boolean = false
) {
    constructor() : this(
        name = "",
        type = AppType.MANAGER,
        description = "",
        apkFile = "",
        apkIcon = "",
        version = "",
        isInstalled = false
    )
}
