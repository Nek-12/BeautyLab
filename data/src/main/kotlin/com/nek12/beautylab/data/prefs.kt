package com.nek12.beautylab.data

import android.content.Context
import com.nek12.androidutils.extensions.preferences.stringPreference


var Context.accessToken by stringPreference()
var Context.refreshToken by stringPreference()
