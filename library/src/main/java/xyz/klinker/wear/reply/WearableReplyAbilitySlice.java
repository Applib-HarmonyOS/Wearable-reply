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
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.ScrollView;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;

import java.io.IOException;

public class WearableReplyAbilitySlice extends AbilitySlice implements ItemClickListener {

    private static final String ARG_CANNED_RESPONSES = "canned_responses";
    private static final String ARG_RESULT_TEXT = "result_text";

    public static final int ACTIVITY_REQUEST_CODE = 0;
    private static final int VOICE_REQUEST_CODE = 1;


    public static void start(AbilitySlice context) {
        start(context, null);
    }

    public static void start(AbilitySlice context, int cannedResponseList) {
        try {
            start(context, context.getResourceManager().getElement(cannedResponseList).getStringArray());
        } catch (IOException e) {
            //IOException
        } catch (NotExistException e) {
            //NotExistException
        } catch (WrongTypeException e) {
            //WrongTypeException
        }
    }

    public static void start(AbilitySlice context, String[] cannedResponses) {

        WearableReplyAbilitySlice wearableReplyAbility = new WearableReplyAbilitySlice();
        Intent intent = new Intent();
        intent.setParam(ARG_CANNED_RESPONSES,cannedResponses);
        context.presentForResult(wearableReplyAbility,intent,ACTIVITY_REQUEST_CODE);

    }

    public static String getResultText(Intent data) {
        if (data != null) {
            return data.getStringParam(ARG_RESULT_TEXT);
        } else {
            return null;
        }
    }

    private Component voiceReply;
    private Component textReply;
    private ScrollView scrollView;
    private ListContainer recyclerView;
    private final CharSequence[] responses = {"Yes","No","May be","Ok","Thanks"};

    private TextAdapter adapter;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_wearreply_abilityslice_reply);

        String[] cannedResponseList = intent.getStringArrayParam(ARG_CANNED_RESPONSES);
        voiceReply = findComponentById(ResourceTable.Id_voice);
        textReply = findComponentById(ResourceTable.Id_text);
        recyclerView = (ListContainer) findComponentById(ResourceTable.Id_recycler_view);

        voiceReply.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                captureSpeech();
            }
        });

        textReply.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                TextReplyAbilitySlice.start(WearableReplyAbilitySlice.this);
            }
        });

        CharSequence[] cannedResponses = intent.getStringArrayParam(ARG_CANNED_RESPONSES);
        if (cannedResponses == null) {
            adapter = new TextAdapter(responses, WearableReplyAbilitySlice.this, this);
        } else {
            adapter = new TextAdapter(cannedResponses, this, this);
        }

        adapter = new TextAdapter(cannedResponseList,this,this);
        recyclerView.setItemProvider(adapter);
    }

    private void captureSpeech() {
        finishWithResult("");
    }

    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        super.onResult(requestCode, resultIntent);
        String text = null;

        if (requestCode == VOICE_REQUEST_CODE && requestCode == 0) {
            //VOICE REQUEST CODE INTENT
        } else {
            text = TextReplyAbilitySlice.getResultText(resultIntent);
        }

        if (text != null) {
            finishWithResult(text);
        }
    }

    private void finishWithResult(CharSequence text) {
        Intent result = new Intent();
        result.setParam(ARG_RESULT_TEXT, text);
        setResult(result);
        terminate();
    }

    @Override
    public void onItemClick(int position) {
        finishWithResult(adapter.getItem(position).toString());
    }
}
