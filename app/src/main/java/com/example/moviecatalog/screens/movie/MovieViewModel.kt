package com.example.moviecatalog.screens.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.ReviewModel
import com.example.moviecatalog.domain.ReviewModifyModel
import com.example.moviecatalog.domain.UserShortModel
import com.example.moviecatalog.repository.FavoriteMoviesRepository
import com.example.moviecatalog.repository.MovieRepository
import com.example.moviecatalog.repository.ReviewRepository
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    private val movieRepository = MovieRepository()
    private val favoriteMoviesRepository = FavoriteMoviesRepository()
    private val userRepository = UserRepository()
    private val reviewRepository = ReviewRepository()

    private var movieId = ""

    private val _favoriteInStart = MutableLiveData(false)
    val favoriteInStart: LiveData<Boolean> = _favoriteInStart

    private val _userProf = MutableLiveData<UserShortModel>()
    val userProf: LiveData<UserShortModel> = _userProf

    private val _myReviewInfo = MutableLiveData<ReviewModel>()
    val myReviewInfo: LiveData<ReviewModel> = _myReviewInfo
    fun setMyReviewInfo(value: ReviewModel) {
        _myReviewInfo.value = value
        _textDialogReview.value = value.reviewText
        setActiveStars(value.rating)
        _isAnonymous.value = value.isAnonymous
        status.value = status.value!!.copy(
            userHaveReview = true
        )
    }

    val status = MutableLiveData(MovieScreenStatus())

    private val _stars = MutableLiveData(mutableListOf(false, false, false, false, false, false, false, false, false, false))
    val stars: LiveData<MutableList<Boolean>> = _stars
    fun setActiveStars(index: Int) {
        val newList = MutableList(10) {false}
        for (i in 0..9) {
            newList[i] = i <= index
        }
        _stars.value = newList
        maySave()
    }

    private val _textDialogReview = MutableLiveData("")
    val textDialogReview: LiveData<String> = _textDialogReview
    fun setTextDialogReview(value: String) {
        _textDialogReview.value = value
        maySave()
    }

    private val _isAnonymous = MutableLiveData(false)
    val isAnonymous: LiveData<Boolean> = _isAnonymous
    fun changeIsAnonymous(value: Boolean) {
        _isAnonymous.value = value
    }

    private val _save = MutableLiveData(false)
    val save: LiveData<Boolean> = _save

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog
    fun changeShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    private val _heartEnable = MutableLiveData(true)
    val heartEnable: LiveData<Boolean> = _heartEnable

    private fun maySave() {
        _save.value = _stars.value!![0] != false
    }

    fun initialScreen(id: String) {
        status.value = status.value!!.copy(isStart = false)
        userIdFromProfile()
        movieId = id
        getDetailsRequest()
    }

    private fun getDetailsRequest() {
        viewModelScope.launch {
            favoriteMoviesRepository.getFavorites()
                .collect { result ->
                    result.onSuccess {
                        it.movies?.forEach { favorite ->
                            if (favorite.id == movieId) {
                                status.value = status.value!!.copy(
                                    isFavorite = true
                                )
                                _favoriteInStart.value = status.value!!.isFavorite
                            }
                        }
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
            movieRepository.details(movieId)
                .collect { result ->
                    result.onSuccess {
                       status.value = status.value!!.copy(
                           isLoading = false,
                           movieDetail = it
                       )
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun changeFavorite() {
        _heartEnable.value = false
        viewModelScope.launch {
            if (status.value!!.isFavorite) {
                favoriteMoviesRepository.deleteFavorites(movieId)
                    .collect { result ->
                        result.onSuccess {
                            status.value = status.value!!.copy(
                                isFavorite = !status.value!!.isFavorite
                            )
                            _heartEnable.value = true
                        }.onFailure {
                            status.value = status.value!!.copy(
                                isError = true,
                                errorMessage = it.message
                            )
                            _heartEnable.value = true
                        }
                    }
            } else {
                favoriteMoviesRepository.addFavorites(movieId)
                    .collect { result ->
                        result.onSuccess {
                            status.value = status.value!!.copy(
                                isFavorite = !status.value!!.isFavorite
                            )
                            _heartEnable.value = true
                        }.onFailure {
                            status.value = status.value!!.copy(
                                isError = true,
                                errorMessage = it.message
                            )
                            _heartEnable.value = true
                        }
                    }
            }
        }
    }

    private fun userIdFromProfile() {
        viewModelScope.launch {
            userRepository.getProfile()
                .collect { result ->
                    result.onSuccess {
                        _userProf.value = UserShortModel(it.id, it.nickName, it.avatarLink)
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun addReview() {
        viewModelScope.launch {
            var rating = 1
            for (i in 0..9) {
                if (_stars.value!![i]) rating = i + 1
            }
            val reviewBody = ReviewModifyModel(_textDialogReview.value!!, rating, _isAnonymous.value!!)
            reviewRepository.addReview(movieId, reviewBody)
                .collect { result ->
                    result.onSuccess {
                        val temp = status.value!!.movieDetail!!.reviews!!.toMutableList()
                        val newReview = ReviewModel(_userProf.value!!.userId, rating, _textDialogReview.value, _isAnonymous.value!!, "", _userProf.value!!)
                        temp.add(0, newReview)
                        status.value!!.movieDetail!!.reviews = temp
                        status.value = status.value!!.copy(
                            userHaveReview = true
                        )
                        setMyReviewInfo(newReview)
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun changeReview() {
        viewModelScope.launch {
            var rating = 1
            for (i in 0..9) {
                if (_stars.value!![i]) rating = i + 1
            }
            val reviewBody = ReviewModifyModel(_textDialogReview.value!!, rating, _isAnonymous.value!!)
            reviewRepository.putReview(movieId, _myReviewInfo.value!!.id, reviewBody)
                .collect { result ->
                    result.onSuccess {

                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            reviewRepository.deleteReview(movieId, reviewId)
                .collect { result ->
                    result.onSuccess {
                        val temp = status.value!!.movieDetail!!.reviews!!.toMutableList()
                        temp.removeIf { it.id == reviewId }
                        status.value!!.movieDetail!!.reviews = temp
                        status.value = status.value!!.copy(
                            userHaveReview = false,
                        )
                        _textDialogReview.value = ""
                        setActiveStars(-1)
                        _isAnonymous.value = false
                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }
}