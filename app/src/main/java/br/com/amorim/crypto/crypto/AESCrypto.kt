package br.com.amorim.crypto.crypto

import android.util.Base64
import br.com.amorim.crypto.keys.AesKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class AESCrypto {

    private val cipher = Cipher.getInstance(TRANSFORMATION_SYMMETRIC)
    private val aesKey = AesKey()
    private var iv: ByteArray? = null

    fun encrypt(value: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, aesKey.getAesKey())
        val textToEncrypt = value.toByteArray()
        val encryptedByteArray = cipher.doFinal(textToEncrypt)
        iv = cipher.iv
        return Base64.encodeToString(encryptedByteArray, Base64.DEFAULT)
    }

    fun decrypt(value: String): String {
        cipher.init(Cipher.DECRYPT_MODE, aesKey.getAesKey(), IvParameterSpec(iv))
        val encryptedData = Base64.decode(value, Base64.DEFAULT)
        val decoded = cipher.doFinal(encryptedData)
        return String(decoded)
    }

    companion object {
        private const val TRANSFORMATION_SYMMETRIC = "AES/CBC/PKCS7Padding"
    }
}