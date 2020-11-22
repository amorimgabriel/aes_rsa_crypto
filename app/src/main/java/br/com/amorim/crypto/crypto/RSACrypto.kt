package br.com.amorim.crypto.crypto

import android.content.Context
import android.util.Base64
import br.com.amorim.crypto.keys.RSAKey
import javax.crypto.Cipher

class RSACrypto(
    private val context: Context
) {
    private val rsaKey = RSAKey(context).apply {
        generateRSAKeys()
    }

    fun encrypt(value: String): String {
        val cipher: Cipher = Cipher.getInstance(CIPHER_RSA_ENCRYPT_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, rsaKey.getPrivateKey())

        val originalMessage = value.toByteArray() // Converte a mensagem original para byteArray
        val encryptedMessage = cipher.doFinal(originalMessage) //Mensagem em byteArray criptografada
        val encryptMessageString = Base64.encodeToString(encryptedMessage, Base64.DEFAULT)

        return encryptMessageString
    }

    fun decrypt(value: String): String {
        val cipher: Cipher = Cipher.getInstance(CIPHER_RSA_ENCRYPT_MODE)
        cipher.init(Cipher.DECRYPT_MODE, rsaKey.getPublicKey())

        //Encoda em base64 a mensagem criptograda
        val encrypted = Base64.decode(value, Base64.DEFAULT)
        //Remove a criptografia
        val decrypted = cipher.doFinal(encrypted)
        //Converte para string a mensagem em byteArray
        return String(decrypted)
    }

    companion object {
        private const val CIPHER_RSA_ENCRYPT_MODE = "RSA/ECB/PKCS1Padding"
    }
}
