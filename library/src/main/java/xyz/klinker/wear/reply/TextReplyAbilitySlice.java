/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.wear.reply;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ElementScatter;
import ohos.agp.components.element.ShapeElement;

public class TextReplyAbilitySlice extends AbilitySlice {

    private static final int ACTIVITY_REQUEST_CODE = 5;
    private static final String RESULT_TEXT = "result_text";

    protected static void start(AbilitySlice context) {
        TextReplyAbilitySlice textReplyAbilitySlice = new TextReplyAbilitySlice();
        Intent intent = new Intent();
        context.presentForResult(textReplyAbilitySlice,intent,ACTIVITY_REQUEST_CODE);
    }

    public static String getResultText(Intent data) {
        if (data != null) {
            return data.getStringParam(RESULT_TEXT);
        } else {
            return null;
        }
    }

    private TextField text;
    private Component confirm;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_wearreply_abilityslice_text);

        text = (TextField) findComponentById(ResourceTable.Id_text);
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setShape(ShapeElement.RECTANGLE);
        shapeElement.setRgbColor(new RgbColor(0,255,255));
        text.setBasement(ElementScatter.getInstance(getContext()).parse(ResourceTable.Graphic_textfield_shape));
        confirm = findComponentById(ResourceTable.Id_confirm);

        confirm.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                finishWithResult(text.getText());
            }
        });
    }

    private void finishWithResult(CharSequence text) {
        Intent result = new Intent();
        result.setParam(RESULT_TEXT, text);
        setResult(result);
        terminate();
    }
}
