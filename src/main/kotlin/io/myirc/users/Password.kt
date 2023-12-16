package io.myirc.users
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.digest.SHA3_256

class Password(var password: String) {
    private val provider = CryptographyProvider.Default
    init {
        require(password.length >= 8) { "Password must be at least 8 characters long" }
        encryptPassword()
    }

    fun encryptPassword() {
        val digest = provider.get(SHA3_256)
        password = digest.hasher().hashBlocking(password.encodeToByteArray()).toString()
    }

    fun checkPassword(password: String): Boolean {
        val digest = provider.get(SHA3_256)
        return this.password == digest.hasher().hashBlocking(password.encodeToByteArray()).toString()
    }

}