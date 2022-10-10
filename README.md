[![](https://jitpack.io/v/a365344743s/MediaCompressSignal.svg)](https://jitpack.io/#a365344743s/MediaCompressSignal)

# MediaCompressSignal
 Android 视频、图片(暂未实现)、音频(暂未实现)压缩库 from [Signal](https://github.com/signalapp/Signal-Android)

# Signal Commit
提交： 96539d70dffdff0386b80afa1c8022059405dee4 [96539d7]
父级： 07570bbfec
作者： Alex Hart <alex@signal.org>
日期： 2022年7月13日 15:59:23
提交者： Alex Hart
提交时间： 2022年7月13日 15:59:28
Bump version to 5.43.3

# USAGE
## Signal(最低支持AndroidApi26)

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
        implementation 'com.github.a365344743s:MediaCompressSignal:1.0.0'
	}

### 初始化

    org.thoughtcrime.securesms.util.VideoConvertUtil.init(context, scheduler);

### 开始转换

    Integer convertId = org.thoughtcrime.securesms.util.VideoConvertUtil.startVideoConvert(srcPath, dstPath, upperSizeLimit, true/false, listener)

### 取消转换

    org.thoughtcrime.securesms.util.VideoConvertUtil.stopVideoConvert(int convertId);

# 输出视频控制

## 说明
视频码率决定了视频清晰度，码率越大视频越清晰，但是文件会变大。

视频分辨率决定了视频宽高，分辨率越大视频宽高越大。

相同视频码率下，视频分辨率越大，视频越模糊。

音频码率决定了音频清晰度，码率越大音频越清晰，但是文件会变大。

可以控制 输出视频码率、分辨率 和 输出音频码率，但是音频码率通常在视频码率的 1/3至1/10，所以音频码率修改对最终文件大小影响不大。

## 计算方法
1.根据 upperSizeLimit 和 源视频时长，计算出目标视频的码率

    VideoBitRateCalculator.getTargetQuality(long duration, int inputTotalBitRate)。

2.根据目标视频码率，计算出目标视频分辨率

    VideoBitRateCalculator.Quality.getOutputResolution()。

## 视频码率控制
视频码率主要由输出尺寸和视频时长控制，但是提供了一个码率范围 VideoBitRateCalculator.MINIMUM_TARGET_VIDEO_BITRATE(默认500_000) 和 VideoBitRateCalculator.MAXIMUM_TARGET_VIDEO_BITRATE(默认2_000_000)，可修改这个范围控制最终码率。

但是要注意如果VideoBitRateCalculator.MINIMUM_TARGET_VIDEO_BITRATE设置太大，会造成最终输出的文件超过upperSizeLimit，造成转换失败。

## 视频分辨率控制
视频分辨率根据视频码率是否小于VideoBitRateCalculator.LOW_RES_TARGET_VIDEO_BITRATE(默认1_750_000)控制，如果小于返回VideoBitRateCalculator.LOW_RES_OUTPUT_FORMAT(默认480p)，否则返回VideoBitRateCalculator.OUTPUT_FORMAT(默认720p)，可以修改这三个值来控制输出视频分辨率。

## 音频码率控制
VideoBitRateCalculator.AUDIO_BITRATE(默认192_000)