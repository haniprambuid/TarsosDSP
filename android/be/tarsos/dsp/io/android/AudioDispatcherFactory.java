/*
*      _______                       _____   _____ _____  
*     |__   __|                     |  __ \ / ____|  __ \ 
*        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
*        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
*        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
*        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
*                                                         
* -------------------------------------------------------------
*
* TarsosDSP is developed by Joren Six at IPEM, University Ghent
*  
* -------------------------------------------------------------
*
*  Info: http://0110.be/tag/TarsosDSP
*  Github: https://github.com/JorenSix/TarsosDSP
*  Releases: http://0110.be/releases/TarsosDSP/
*  
*  TarsosDSP includes modified source code by various authors,
*  for credits and info, see README.
* 
*/

package be.tarsos.dsp.io.android;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;

public class AudioDispatcherFactory {

	public static AudioDispatcher fromDefaultMicrophone(final int sampleRate,
			final int audioBufferSize, final int bufferOverlap) {
		int minAudioBufferSize = AudioRecord.getMinBufferSize(sampleRate,
				android.media.AudioFormat.CHANNEL_IN_MONO,
				android.media.AudioFormat.ENCODING_PCM_16BIT);
		int minAudioBufferSizeInSamples =  minAudioBufferSize/2;
		if(minAudioBufferSizeInSamples <= audioBufferSize ){
		AudioRecord audioInputStream = new AudioRecord(
				MediaRecorder.AudioSource.MIC, sampleRate,
				android.media.AudioFormat.CHANNEL_IN_MONO,
				android.media.AudioFormat.ENCODING_PCM_16BIT,
				audioBufferSize * 2);

		TarsosDSPAudioFormat format = new TarsosDSPAudioFormat(sampleRate, 16,1, true, false);
		
		TarsosDSPAudioInputStream audioStream = new AndroidAudioInputStream(audioInputStream, format);
		//start recording ! Opens the stream.
		audioInputStream.startRecording();
		return new AudioDispatcher(audioStream,audioBufferSize,bufferOverlap);
		}else{
			new IllegalArgumentException("Buffer size too small should be at least " + (minAudioBufferSize *2));
			return null;
		}
		
		
	}
}
