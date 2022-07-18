package org.thoughtcrime.securesms.util;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class FileUtils {

  static {
    System.loadLibrary("native-utils");
  }

  public static native int getFileDescriptorOwner(FileDescriptor fileDescriptor);

  static native int createMemoryFileDescriptor(String name);

  public static byte[] getFileDigest(FileInputStream fin) throws IOException {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA256");

      byte[] buffer = new byte[4096];
      int read = 0;

      while ((read = fin.read(buffer, 0, buffer.length)) != -1) {
        digest.update(buffer, 0, read);
      }

      return digest.digest();
    } catch (NoSuchAlgorithmException e) {
      throw new AssertionError(e);
    }
  }

  public static void deleteDirectoryContents(@Nullable File directory) {
    if (directory == null || !directory.exists() || !directory.isDirectory()) return;

    File[] files = directory.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) deleteDirectory(file);
        else                    file.delete();
      }
    }
  }

  public static boolean deleteDirectory(@Nullable File directory) {
    if (directory == null || !directory.exists() || !directory.isDirectory()) {
      return false;
    }

    deleteDirectoryContents(directory);

    return directory.delete();
  }
}
