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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lt.paulius.weatherforecastapplication.SearchFragmentDirections.Companion.actionSearchFragmentToForecastFragment
import lt.paulius.weatherforecastapplication.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()


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
                viewModel.fetchWeatherData(cityName)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a valid city name",
                    Toast.LENGTH_SHORT
                ).show()
            }
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.weatherData.collect { current ->

                        // val action = actionSearchFragmentToForecastFragment(current) nereikes papildomu kintamuju
                        // jei nuspresiu i nav graph arguments passint visa data class
                        val temperature = current.current.temp_c
                        val action = actionSearchFragmentToForecastFragment(temperature)
                        view.findNavController().navigate(action)
                    }
                }
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