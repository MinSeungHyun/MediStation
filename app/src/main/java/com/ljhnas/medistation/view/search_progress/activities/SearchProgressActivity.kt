package com.ljhnas.medistation.view.search_progress.activities

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcA
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ljhnas.medistation.databinding.ActivitySearchProgressBinding
import com.ljhnas.medistation.view.search_progress.models.SearchProgressViewModel
import kotlinx.android.synthetic.main.activity_search_progress.*


class SearchProgressActivity : AppCompatActivity() {
    private val viewModel by lazy { SearchProgressViewModel(this) }
    private lateinit var binding: ActivitySearchProgressBinding

    private val nfcAdapter by lazy { NfcAdapter.getDefaultAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.ljhnas.medistation.R.layout.activity_search_progress)
        binding.apply {
            vm = viewModel
            activity = this@SearchProgressActivity
        }

        val span = mainText.text as Spannable
        span.setSpan(ForegroundColorSpan(getColor(com.ljhnas.medistation.R.color.colorPrimary)), 15, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mainText.text = span
        val span2 = mainText2.text as Spannable
        span2.setSpan(ForegroundColorSpan(getColor(com.ljhnas.medistation.R.color.colorPrimary)), 13, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mainText2.text = span2

        tryReadNFC(intent)
    }

    override fun onResume() {
        super.onResume()
        val adapter = NfcAdapter.getDefaultAdapter(this)
        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val intentFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        val techList = arrayOf(arrayOf(NfcA::class.java.name))
        adapter.enableForegroundDispatch(this, pendingIntent, arrayOf(intentFilter), techList)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        tryReadNFC(intent)
    }

    private fun tryReadNFC(intent: Intent) {
        val action = intent.action
        if (!(action == NfcAdapter.ACTION_TAG_DISCOVERED ||
                action == NfcAdapter.ACTION_TECH_DISCOVERED ||
                action == NfcAdapter.ACTION_NDEF_DISCOVERED)) return

        Log.d("testing", "reading")

        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return
        val uid = byteArrayToHexString(tag.id)
        Log.d("testing", uid)
        viewModel.requestNfcUid(uid)
    }

    private fun byteArrayToHexString(inArray: ByteArray): String {
        var i: Int
        var j = 0
        var `in`: Int
        val hex = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        var out = ""
        while (j < inArray.size) {
            `in` = inArray[j].toInt() and 0xff
            i = `in` shr 4 and 0x0f
            out += hex[i]
            i = `in` and 0x0f
            out += hex[i]
            ++j
        }
        return out
    }
}