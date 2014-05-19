## 0.0.5
* Fixed repository name type from `android-process-buton` to `android-process-button` 

## 0.0.4
**Attributes**

Renamed attributes:

 * `progressText` -> `textProgress`
 * `completeText` -> `textComplete`

Added attribute to control button corner roundness:

* `cornerRadius`


**ProcessButton** 

Now button state depends on progress:

* normal state [0]
* progress state [1-99]
* success state [100]
* error state [-1] *new

Added abstract `drawProgress` method to simplify creation different progress buttons.

General code clean-up.

Thanks to **@avrahamshuk** for contribution.

## 0.0.3

* Fixed bug when all buttons where sharing the same drawable state
* Uploaded proejct to Maven Central
