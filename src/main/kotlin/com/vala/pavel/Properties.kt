package com.vala.pavel

import com.natpryce.konfig.*

object server : PropertyGroup() {
    val port by intType
    val static_html by stringType
}

object db : PropertyGroup() {
    val url by stringType
}
