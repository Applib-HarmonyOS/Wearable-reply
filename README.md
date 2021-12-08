[![Build](https://github.com/applibgroup/wearable-reply/actions/workflows/main.yml/badge.svg)](https://github.com/applibgroup/wearable-reply/actions/workflows/main.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=applibgroup_Wearable-reply&metric=alert_status)](https://sonarcloud.io/dashboard?id=applibgroup_Wearable-reply)

# Wearable Reply

## Introduction:
This library aims to simplify allowing text input in your app, via voice, keyboard, or canned responses.

# Source
This library has been inspired by [klinker24\\wearable-reply](https://github.com/klinker24/wearable-reply).

## Usage Instructions:

Usage is really simple. Start the reply abilityslice with:

```java
WearableReplyAbilitySlice.start(abilityslice);
WearableReplyAbilitySlice.start(abilityslice, new String[] {"test 1", "test 2" });
```

If you omit the `String[]` or the array resource, the `WearableReplyAbilitySlice` will display the default canned responses: "Yes", "No", "Maybe", "Ok", and "Thanks".

When the user has done the text input, the results will be delivered to your `onResult` method, and can be pulled with:

```java
  @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        super.onResult(requestCode, resultIntent);
        String result = WearableReplyAbilitySlice.getResultText(resultIntent);
        if (result != null) {
            ToastDialog toastDialog = new ToastDialog(this);
            toastDialog.setText(result).setDuration(1000).show();
        }
    }
```


## Installation instructions:

```
Method 1:
    Generate the .har package through the library and add the .har package to the libs folder.
    Add the following code to the entry gradle:
        implementation fileTree  (dir: 'libs', include: ['*.jar', '*.har'])

Method 2:
    allprojects{
        repositories{
            mavenCentral()
        }
    }
implementation 'dev.applibgroup:wearablereply:1.0.0'
```

## License

    Copyright 2017 Luke Klinker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
