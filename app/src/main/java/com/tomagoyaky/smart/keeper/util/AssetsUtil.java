package com.tomagoyaky.smart.keeper.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author tomagoyaky
 * @date 2018/3/5
 */

public class AssetsUtil {

    /**
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getAssetsJson(@NonNull Context context, @NonNull String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                assetManager.open(fileName),"utf-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
