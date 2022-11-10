package com.example.moviecatalog.screens.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.MessageController
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

    private val _myReviewInfo = MutableLiveData<ReviewModel>()
    val myReviewInfo: LiveData<ReviewModel> = _myReviewInfo
    private fun setMyReviewInfo(value: ReviewModel) {
        _myReviewInfo.value = value
        _textDialogReview.value = value.reviewText
        setActiveStars(value.rating - 1)
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
        _save.value = _stars.value!![0] != false && _textDialogReview.value!!.isNotEmpty()
    }

    fun initialScreen(id: String) {
        status.value = status.value!!.copy(isStart = false)
        movieId = id
        getFavorites()
        getDetailsRequest()
    }

    fun beforeInit() {
        status.value = status.value!!.copy(makingRequest = true)
        getProfile()
    }

    private fun getDetailsRequest() {
        viewModelScope.launch {
            movieRepository.details(movieId)
                .collect { result ->
                    result.onSuccess {
                        status.value = status.value!!.copy(
                            isLoading = false,
                            movieDetail = it
                        )
                        for (index in 0 until (it.reviews?.size ?: -1)) {
                            if (it.reviews!![index].author != null) {
                                if (it.reviews!![index].author!!.userId == _userProf.value!!.userId) {
                                    setMyReviewInfo(it.reviews!![index])

                                    val temp = it.reviews!!.toMutableList()
                                    val myReview = temp.removeAt(index)
                                    temp.add(0, myReview)
                                    status.value!!.movieDetail!!.reviews = temp

                                    break
                                }
                            }
                        }

                    }.onFailure {
                        status.value = status.value!!.copy(
                            isError = true,
                            errorMessage = it.message
                        )
                    }
                }
        }
    }

    private fun getFavorites() {
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
                                isFavorite = !status.value!!.isFavorite,
                                showMessage = true,
                                textMessage = MessageController.getTextMessage(MessageController.FAVORITE_DELETE)
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
                                isFavorite = !status.value!!.isFavorite,
                                showMessage = true,
                                textMessage = MessageController.getTextMessage(MessageController.FAVORITE_ADD)
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

    private fun getProfile() {
        viewModelScope.launch {
            userRepository.getProfile()
                .collect { result ->
                    result.onSuccess {
                        _userProf.value = UserShortModel(it.id, it.nickName, it.avatarLink)
                        status.value = status.value!!.copy(
                            isGetProfile = true,
                            makingRequest = false
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

    fun addReview() {
        viewModelScope.launch {
            var rating = 1
            for (i in 0..9) {
                if (_stars.value!![i]) rating = i + 1
            }
            val reviewBody =
                ReviewModifyModel(_textDialogReview.value!!, rating, _isAnonymous.value!!)
            reviewRepository.addReview(movieId, reviewBody)
                .collect { result ->
                    result.onSuccess {
                        status.value = status.value!!.copy(
                            userHaveReview = true,
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.REVIEW_ADD)
                        )
                        getDetailsRequest()
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
            val reviewBody =
                ReviewModifyModel(_textDialogReview.value!!, rating, _isAnonymous.value!!)
            reviewRepository.putReview(movieId, _myReviewInfo.value!!.id, reviewBody)
                .collect { result ->
                    result.onSuccess {
                        status.value!!.movieDetail!!.reviews!![0].rating = rating
                        status.value!!.movieDetail!!.reviews!![0].reviewText =
                            _textDialogReview.value
                        status.value!!.movieDetail!!.reviews!![0].isAnonymous = _isAnonymous.value!!

                        status.value = status.value!!.copy(
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.REVIEW_CHANGE)
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

    fun deleteReview() {
        viewModelScope.launch {
            reviewRepository.deleteReview(movieId, _myReviewInfo.value!!.id)
                .collect { result ->
                    result.onSuccess {
                        val temp = status.value!!.movieDetail!!.reviews!!.toMutableList()
                        temp.removeIf { it.id == _myReviewInfo.value!!.id }
                        status.value!!.movieDetail!!.reviews = temp

                        status.value = status.value!!.copy(
                            userHaveReview = false,
                            showMessage = true,
                            textMessage = MessageController.getTextMessage(MessageController.REVIEW_DELETE)
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

    fun setDefaultStatus() {
        status.value = status.value!!.copy(
            isError = false,
            showMessage = false
        )
    }
}