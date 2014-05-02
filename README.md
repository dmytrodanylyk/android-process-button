## Sample

### Indicate sign in progress

<img src="screenshots/sample1.gif" width="480" />

===
### Indicate send message progress

<img src="screenshots/sample2.gif" width="480" />

===
### Indicate image upload progress

<img src="screenshots/sample3.gif" width="480" />
## Usage

Declare button inside your layout

```xml
 <com.dd.processbutton.ProcessButton
    android:id="@+id/btnSignIn"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/btn_blue_selector"
    android:text="Sign in"
    custom:completeDrawable="@drawable/rect_green"
    custom:completeText="Success"
    custom:progressDrawable="@drawable/rect_purple"
    custom:progressText="Loading"
    custom:progressStyle="action"/>
            
<!--drawable which will be displayed when loading is complete-->
custom:completeDrawable="@drawable/rect_green"
<!--text which will be displayed when loading is complete-->
custom:completeText="Success"
<!--drawable which will be displayed when loading is in progress-->
custom:progressDrawable="@drawable/rect_purple"
<!--text which will be displayed when loading is in progress-->
custom:progressText="Loading"
<!--progress indicator - action, submit, generate-->
custom:progressStyle="action"
```

Control via Java code

```java
ProcessButton btnSignIn = (ProcessButton) findViewById(R.id.btnSignIn);

// no progress
button.setProgress(0);
// progressDrawable cover 50% of button width, progressText is shown
button.setProgress(50);
// progressDrawable cover 75% of button width, progressText is shown
button.setProgress(75);
// completeDrawable & completeText is shown
button.setProgress(100);
```

## License

```
The MIT License (MIT)

Copyright (c) 2014 Danylyk Dmytro

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
