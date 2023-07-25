package lt.paulius.weatherforecastapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            openSearchFragment()
        }
    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.commit {
            replace(
                R.id.fragmentContainerView,
                fragment,
                tag
            )
            setReorderingAllowed(true)
        }
    }

    private fun openSearchFragment() {
        setCurrentFragment(SearchFragment.newInstance(), SearchFragment.TAG)
    }


}