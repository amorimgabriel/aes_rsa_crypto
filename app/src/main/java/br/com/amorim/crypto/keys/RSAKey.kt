@file:Suppress("DEPRECATION")

package br.com.amorim.crypto.keys

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.security.KeyPairGeneratorSpec.Builder
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStore.PrivateKeyEntry
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Calendar
import javax.security.auth.x500.X500Principal

class RSAKey(
    private val context: Context
) {
    private val keyStore: KeyStore? by lazy {
        KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null)
        }
    }

    fun initAndGenerateKeyPair() {
        val spec =
            Builder(context).run {
                setKeySize(RSA_KEY_SIZE)
                setSerialNumber(BigInteger.TEN)
                setSubject(X500Principal("CN=$ALIAS"))
                setAlias(ALIAS)
                setStartDate(Calendar.getInstance().time)
                setEndDate(Calendar.getInstance().apply { add(Calendar.YEAR, 2) }.time)
                build()
            }

        KeyPairGenerator.getInstance(
            RSA_ENCRYPT_MODE,
            ANDROID_KEY_STORE
        ).apply {
            initialize(spec)
            generateKeyPair()
        }
    }

    fun getPrivateKey(): PrivateKey? {
        return (keyStore?.getEntry(ALIAS, null) as? PrivateKeyEntry)?.privateKey
    }

    fun getPublicKey(): PublicKey? {
        val privateKeyEntry = (keyStore?.getEntry(ALIAS, null) as? PrivateKeyEntry)
        return privateKeyEntry?.certificate?.publicKey
    }

    companion object {
        private const val RSA_ENCRYPT_MODE = "RSA"
        private const val RSA_KEY_SIZE = 2048
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val ALIAS = "alias"
    }
}