package org.example.project.data.repository

import com.benasher44.uuid.uuid4
import com.example.project.AnandMartDb
import com.example.project.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.config.AppConfig
import org.example.project.domain.model.UsersModel
import org.example.project.domain.repository.SessionRepository
import org.example.project.utils.AppConstants

class SessionRepoImpl(
    private val appConfig: AppConfig,
    private val db: AnandMartDb,
    ) : SessionRepository {

    override suspend fun startSession() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                db.transaction {
                    db.sessionQueries.insertSession(
                        id = uuid4().toString(),
                        created_at = Clock.System.now().toEpochMilliseconds(),
                        is_active = true
                    )
                }
                println("session created")
            } catch (e: Exception) {
                println("Error in inserting Session: ${e.message}")
            }
        }
    }

    override suspend fun endSession() {
        appConfig.clearAllData()
        db.sessionQueries.clearAllSessions()
        db.userQueries.deleteAllUsers()
    }

    override suspend fun currentActiveSession(): Session {
        return db.sessionQueries.selectActive().executeAsOne()
    }

    override fun getUserDetails(): UsersModel {
        return appConfig.getObject(AppConstants.ARG_USER_DETAILS, UsersModel.serializer())
            ?: UsersModel.empty()
    }

    override fun setUserDetails(user: UsersModel): Boolean {
        appConfig.putObject(AppConstants.ARG_USER_DETAILS, user , UsersModel.serializer())
        return true
    }

    override fun getIsUserLoggedIn(): Boolean {
       return appConfig.getBoolean(AppConstants.ARG_IS_LOGGED_IN, defaultValue = false)
    }

    override fun setUserLoggedIn(): Boolean {
        appConfig.putBoolean(AppConstants.ARG_IS_LOGGED_IN, true)
        return true
    }

}