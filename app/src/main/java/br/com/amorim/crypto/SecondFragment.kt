package br.com.amorim.crypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import br.com.amorim.crypto.crypto.AESCrypto
import br.com.amorim.crypto.crypto.RSACrypto

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aesCrypto = AESCrypto()

        val textToEncrypt = view.findViewById<TextView>(R.id.text_to_encrypt)
        val textToDecrypt = view.findViewById<TextView>(R.id.text_to_decrypt)
        val decryptResult = view.findViewById<TextView>(R.id.result)

        view.findViewById<Button>(R.id.encrypt_button).setOnClickListener {
            aesCrypto.encrypt(textToEncrypt.text.toString()).let {
                textToDecrypt.text = it
            }
        }

        view.findViewById<Button>(R.id.decrypt_button).setOnClickListener {
            aesCrypto.decrypt(textToDecrypt.text.toString()).let {
                decryptResult.text = it
            }
        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}