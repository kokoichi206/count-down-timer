package jp.mydns.kokoichi0206.countdowntimer.datamanager

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import jp.mydns.kokoichi0206.countdowntimer.R

/**
 * SoundPoolのmanager。
 */
class SoundPoolManager(context: Context) {
    private var soundPool: SoundPool

    /**
     * 終了時の音源id。
     */
    private var finishSoundId = 0

    /**
     * 音量。
     */
    private var soundVolume = 5.0f

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(2)
            .build()
        // 音源を事前に読み込んでおく。
        finishSoundId = soundPool.load(context, R.raw.finish_sound, 1)
    }

    /**
     * タイマー終了時の音源を再生する。
     */
    fun playFinishSound() {
        soundPool.play(finishSoundId, soundVolume, soundVolume, 0, 0, 1.0f)
    }
}
