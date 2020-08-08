package br.com.weslleymaciel.gamesecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.weslleymaciel.gamesecommerce.common.models.Banner
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.data.repository.GamesRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

class GamesViewModel {

    private val gamesRepository = GamesRepository()
    private var listBanner = MutableLiveData<List<Banner>>()
    private var listGame = MutableLiveData<List<Game>>()
    private var game = MutableLiveData<Game>()
    private var isSaved = MutableLiveData<Boolean>()

    fun getBanners(): LiveData<List<Banner>> {
        val compositeDisposable = CompositeDisposable()

        listBanner.value = null
        gamesRepository.getBanners(object: SingleObserver<List<Banner>> {
            override fun onSuccess(response: List<Banner>) {
                listBanner.value = response
                compositeDisposable.clear()
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }
            override fun onError(e: Throwable) {
                compositeDisposable.clear()
            }
        })
        return listBanner
    }

    fun getSpotlight(): LiveData<List<Game>> {
        val compositeDisposable = CompositeDisposable()

        listGame.value = null
        gamesRepository.getSpotlight(object: SingleObserver<List<Game>> {
            override fun onSuccess(response: List<Game>) {
                listGame.value = response
                compositeDisposable.clear()
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }
            override fun onError(e: Throwable) {
                compositeDisposable.clear()
            }
        })
        return listGame
    }

    fun getGameDetail(id: Number): LiveData<Game> {
        val compositeDisposable = CompositeDisposable()

        listGame.value = null
        gamesRepository.getGameDetail(id, object: SingleObserver<Game> {
            override fun onSuccess(response: Game) {
                game.value = response
                compositeDisposable.clear()
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }
            override fun onError(e: Throwable) {
                compositeDisposable.clear()
            }
        })
        return game
    }

    fun searchGame(term: String): LiveData<List<Game>> {
        val compositeDisposable = CompositeDisposable()

        listGame.value = null
        gamesRepository.searchGame(term, object: SingleObserver<List<Game>> {
            override fun onSuccess(response: List<Game>) {
                listGame.value = response
                compositeDisposable.clear()
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }
            override fun onError(e: Throwable) {
                compositeDisposable.clear()
            }
        })
        return listGame
    }

    fun checkout(): LiveData<Boolean> {
        val compositeDisposable = CompositeDisposable()

        isSaved.value = false
        gamesRepository.checkout(object: SingleObserver<ResponseBody> {
            override fun onSuccess(response: ResponseBody) {
                response?.let {
                    isSaved.value = true
                }
                compositeDisposable.clear()
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }
            override fun onError(e: Throwable) {
                compositeDisposable.clear()
            }
        })
        return isSaved
    }
}