package xyz.klinker.wear_reply.slice;

import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.window.dialog.ToastDialog;
import xyz.klinker.wear.reply.WearableReplyAbilitySlice;
import xyz.klinker.wear_reply.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button button = (Button) findComponentById(ResourceTable.Id_reply);
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                 WearableReplyAbilitySlice.start(MainAbilitySlice.this, new String[] {"test 1", "test 2" });
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        super.onResult(requestCode, resultIntent);
        String result = WearableReplyAbilitySlice.getResultText(resultIntent);
        if (result != null) {
            ToastDialog toastDialog = new ToastDialog(this);
            toastDialog.setText(result).setDuration(1000).show();
        }
    }
}
