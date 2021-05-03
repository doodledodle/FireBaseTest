package com.dodlebee.firebasetest.KaKao

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.AuthCodeClient
import com.kakao.sdk.auth.model.OAuthToken


private val authCodeClient: AuthCodeClient = AuthCodeClient.instance
private val authApiClient: AuthApiClient = AuthApiClient.instance

val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

}

fun loginWithKakaoTalk(
        context: Context,
        requestCode: Int = AuthCodeClient.DEFAULT_REQUEST_CODE,
        channelPublicIds: List<String>? = null,
        serviceTerms: List<String>? = null,
        callback: (token: OAuthToken?, error: Throwable?) -> Unit
) {
    authCodeClient.authorizeWithKakaoTalk(
            context,
            requestCode,
            channelPublicIds,
            serviceTerms
    ) { code, codeError ->
        if (codeError != null) {
            callback(null, codeError)
        } else {
            authApiClient.issueAccessToken(code!!) { token, tokenError ->
                callback(token, tokenError)
            }
        }
    }
}