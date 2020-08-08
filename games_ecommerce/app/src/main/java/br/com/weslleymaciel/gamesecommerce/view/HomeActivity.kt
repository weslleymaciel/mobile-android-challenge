package br.com.weslleymaciel.gamesecommerce.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.Banner
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.view.adapters.BannerAdapter
import br.com.weslleymaciel.gamesecommerce.view.adapters.SpotlightAdapter
import br.com.weslleymaciel.gamesecommerce.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity() {
    private val viewModel = GamesViewModel()

    private val observerBanner = Observer<List<Banner>?> {
        it?.let {
            loadBanners(it)
        }
    }

    private val observerSpotlight = Observer<List<Game>?> {
        it?.let {
            loadGames(it)
        }
    }

    fun loadBanners(banners: List<Banner>){
        val fragments = mutableListOf<Fragment>()

        for(banner in banners){
            fragments.add(BannerFragment(banner))
        }

        configureViewPager(fragments)
    }

    fun loadGames(games: List<Game>){
        configureSpotlight(games)
    }

    private fun configureSpotlight(games: List<Game>){
        rvSpotlight.layoutManager = GridLayoutManager(this, 2)
        rvSpotlight.adapter = SpotlightAdapter(games, {
                gameId -> {

            }})
    }

    private fun configureViewPager(fragments: List<Fragment>) {
        vpBanner.adapter = BannerAdapter(supportFragmentManager, fragments)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if (vpBanner.currentItem < fragments.size - 1 ){
                    vpBanner.currentItem += 1
                }else{
                    vpBanner.currentItem = 0
                }
                mainHandler.postDelayed(this, 3000)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel.getBanners().observe(this, observerBanner)
        viewModel.getSpotlight().observe(this, observerSpotlight)
    }

    override fun onResume() {
        super.onResume()
    }
}