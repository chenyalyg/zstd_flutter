package com.chenya.zstd_flutter;

import com.github.luben.zstd.Zstd;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ZstdFlutterPlugin */
public class ZstdFlutterPlugin implements FlutterPlugin, MethodCallHandler {
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor().getBinaryMessenger(), "zstd_flutter");
    channel.setMethodCallHandler(new ZstdFlutterPlugin());
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "zstd_flutter");
    channel.setMethodCallHandler(new ZstdFlutterPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if(call.method.equals("compress")){
      int level=call.argument("level");
      byte[] src=call.argument("src");
      byte[] out=Zstd.compress(src,level);
      result.success(out);

    }else if(call.method.equals("decompress")){
      byte[] src=call.argument("src");
      int size=call.argument("original_size");
      byte[] original=new byte[size];
      Zstd.decompress(original,src);
      result.success(original);
    } else if(call.method.equals("decompressed_size")){
      byte[] src=call.argument("src");
      long original_size=Zstd.decompressedSize(src);
      result.success(original_size);
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  }


}
