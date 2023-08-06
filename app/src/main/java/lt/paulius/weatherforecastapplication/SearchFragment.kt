package lt.paulius.weatherforecastapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.SearchFragmentDirections.Companion.actionSearchFragmentToForecastFragment
import lt.paulius.weatherforecastapplication.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            val cityName = binding.enterCityName.text.toString().trim()
            if (cityName.isNotEmpty()) {
                val action = actionSearchFragmentToForecastFragment(cityName)
                view.findNavController().navigate(action)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a valid city name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SearchFragment()
        const val TAG = "search_fragment"
    }
}