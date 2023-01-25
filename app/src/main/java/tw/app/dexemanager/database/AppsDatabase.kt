package tw.app.dexemanager.database

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import tw.app.dexemanager.model.App
import tw.app.dexemanager.util.Constants
import tw.app.dexemanager.R.string as AppString

class AppsDatabase(
    private var context: Context,
    private val listener: AppsDatabaseListener
) {
    private val database = FirebaseFirestore.getInstance().collection(Constants.APPS_DATABASE)

    suspend fun getApps() {
        var appsList: MutableList<App> = arrayListOf()
        var query = database.get().await()

        if (query == null || query.isEmpty) {
            listener.onError(Exception(getString(AppString.query_is_empty_error)))
            return
        }

        for (document in query) {
            val app = document.toObject(App::class.java)
            if (app.type.getType() != AppType.MANAGER.getType())
                appsList.add(app)
        }

        listener.onAppsReceived(appsList)
    }

    suspend fun getApp(type: AppType) {
        var query = database.whereEqualTo("type", type.getType()).get().await()

        if (query == null || query.isEmpty) {
            listener.onError(Exception(getString(AppString.query_is_empty_error)))
            return
        }

        var app = query.documents[0].toObject(App::class.java)

        if (app != null)
            listener.onAppReceived(app)
        else
            listener.onError(Exception(getString(AppString.app_object_is_null_error)))
    }

    private fun getString(resId: Int): String {
        return context.getString(resId)
    }
}

enum class AppType(_type: String) {
    MANAGER("DEXE_APP_MANAGER"),
    VIDEO_PLAYER("DEXE_APP_VIDEO_PLAYER"),
    WIDGETS("DEXE_APP_WIDGETS");

    private var type = ""

    init {
        type = _type
    }

    fun setType(_type: String) {
        type = _type
    }

    fun getType(): String {
        return type
    }
}

interface AppsDatabaseListener {
    fun onAppsReceived(apps: MutableList<App>) {}

    fun onAppReceived(app: App) {}

    fun onError(exception: Exception) {}
}