package com.example.myapplication3.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class ExifUtil {
    private static final String SCHEME_FILE = "file";
    private static final String SCHEME_CONTENT = "content";

    ExifUtil() {
    }

    public static void closeSilently(@Nullable Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable var2) {
            }
        }
    }

    public static int getExifRotation(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return 0;
        } else {
            try {
                ExifInterface e = new ExifInterface(imagePath);
                switch (e.getAttributeInt("Orientation", 0)) {
                    case 3:
                        return 180;
                    case 6:
                        return 90;
                    case 8:
                        return 270;
                    default:
                        return 0;
                }
            } catch (IOException var2) {
                return 0;
            }
        }
    }

    public static boolean copyExifRotation(File sourceFile, File destFile) {
        if (sourceFile != null && destFile != null) {
            try {
                ExifInterface e = new ExifInterface(sourceFile.getAbsolutePath());
                ExifInterface exifDest = new ExifInterface(destFile.getAbsolutePath());
                exifDest.setAttribute("Orientation", e.getAttribute("Orientation"));
                exifDest.saveAttributes();
                return true;
            } catch (IOException var4) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Nullable
    public static File getFromMediaUri(Context context, Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        if (uri == null) {
            return null;
        } else if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath());
        } else if ("content".equals(uri.getScheme())) {
            String[] filePathColumn = new String[]{"_data", "_display_name"};
            Cursor cursor = null;

            File filePath;
            try {
                cursor = resolver.query(uri, filePathColumn, (String) null, (String[]) null, (String) null);
                if (cursor == null || !cursor.moveToFirst()) {
                    return null;
                }

                int ignored = uri.toString().startsWith("content://com.google.android.gallery3d") ? cursor.getColumnIndex("_display_name") : cursor.getColumnIndex("_data");
                if (ignored == -1) {
                    return null;
                }

                String filePath1 = cursor.getString(ignored);
                if (TextUtils.isEmpty(filePath1)) {
                    return null;
                }

                File var7 = new File(filePath1);
                return var7;
            } catch (IllegalArgumentException var12) {
                filePath = getFromMediaUriPfd(context, resolver, uri);
            } catch (SecurityException var13) {
                return null;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }

            return filePath;
        } else {
            return null;
        }
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }

    @Nullable
    private static File getFromMediaUriPfd(Context context, ContentResolver resolver, Uri uri) {
        if (uri == null) {
            return null;
        } else {
            FileInputStream input = null;
            FileOutputStream output = null;

            try {
                ParcelFileDescriptor ignored = resolver.openFileDescriptor(uri, "r");
                FileDescriptor fd = ignored.getFileDescriptor();
                input = new FileInputStream(fd);
                String tempFilename = getTempFilename(context);
                output = new FileOutputStream(tempFilename);
                byte[] bytes = new byte[4096];

                int read;
                while ((read = input.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }

                File var10 = new File(tempFilename);
                return var10;
            } catch (IOException var14) {
                ;
            } finally {
                closeSilently(input);
                closeSilently(output);
            }

            return null;
        }
    }
}
