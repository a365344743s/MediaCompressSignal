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