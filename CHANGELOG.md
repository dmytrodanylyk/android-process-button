## 1.0.2
* Added library description file for About AboutLibraries

## 1.0.1
* Fixed issue: buggy behavior when an endless button gets re-sized #14

## 1.0.0
* Fixed issue: Button padding are ignored #11
* Added prefix 'pb_' for all custom attributes to avoid conflict #12

## 0.0.7
* Added api 10 compatibility
* Fixed bug in ProgressGenerator class

## 0.0.6
Bug fixes

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
