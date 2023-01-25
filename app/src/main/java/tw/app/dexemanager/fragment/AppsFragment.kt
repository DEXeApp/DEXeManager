package tw.app.dexemanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import tw.app.dexemanager.database.AppsDatabase
import tw.app.dexemanager.database.AppsDatabaseListener
import tw.app.dexemanager.databinding.FragmentAppsBinding
import tw.app.dexemanager.model.App
import kotlin.coroutines.CoroutineContext

class AppsFragment : Fragment(), CoroutineScope {
    /*------------------------------------------*/
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    /*------------------------------------------*/

    private var _binding: FragmentAppsBinding? = null
    private val binding get() = _binding!!

    private lateinit var appsDatabase: AppsDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {
        appsDatabase = AppsDatabase(requireContext(), object : AppsDatabaseListener {
            override fun onAppsReceived(apps: MutableList<App>) {
                loadApps(apps)
            }

            override fun onError(exception: Exception) {

            }
        })
    }

    private fun loadApps(apps: MutableList<App>) {
        val linearManager = LinearLayoutManager(requireContext())
        binding.appsRecyclerView.layoutManager = linearManager
    }
}