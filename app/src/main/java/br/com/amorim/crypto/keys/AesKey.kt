package br.com.amorim.crypto.keys

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class AesKey {

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null) //Parametro para ajudar a proteger ainda mais o storage como por exemplo: PasswordProtection
        }
    }

    fun getAesKey(): SecretKey? {
        return if (keyStore.containsAlias(ALIAS_KEY)) {
            keyStore.getKey(ALIAS_KEY, null) as SecretKey?
        } else {
            createAesKey()
        }
    }

    private fun createAesKey(): SecretKey {
        val startCalendar = Calendar.getInstance()
        startCalendar.add(Calendar.DAY_OF_MONTH, -1)
        val keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM, ANDROID_KEY_STORE)
        val builder = KeyGenParameterSpec.Builder(
            ALIAS_KEY,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeyValidityStart(startCalendar.time)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
        keyGenerator.init(builder.build())
        return keyGenerator.generateKey()
    }

    companion object {
        private const val AES_ALGORITHM = "AES"
        private const val ALIAS_KEY = "aliasKey"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}